package bg.sofia.uni.fmi.mjt.order.server.repository;

import bg.sofia.uni.fmi.mjt.order.server.destination.Destination;
import bg.sofia.uni.fmi.mjt.order.server.order.Order;
import bg.sofia.uni.fmi.mjt.order.server.Response;
import bg.sofia.uni.fmi.mjt.order.server.tshirt.Color;
import bg.sofia.uni.fmi.mjt.order.server.tshirt.Size;
import bg.sofia.uni.fmi.mjt.order.server.tshirt.TShirt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class MJTOrderRepository implements OrderRepository {

    private Map<Integer, Order> orders;
    private Collection<Order> deniedOrders;

    private AtomicInteger id;

    public MJTOrderRepository() {
        id = new AtomicInteger(1);
        orders = new HashMap<>();
        deniedOrders = new ArrayList<>();
    }

    /**
     * Creates a new T-Shirt order
     *
     * @param size        - size of the requested T-Shirt
     * @param color       - color of the requested T-Shirt
     * @param destination - destination of the requested T-Shirt
     * @return response which contains status and additional info (orderId or invalid parameters if there are such)
     * @throws IllegalArgumentException if either size, color or destination is null
     */
    @Override
    public Response request(String size, String color, String destination) {
        Collection<String> eventualError = new ArrayList<>();
        Size orderSize = Size.getValue(size);
        Color orderColor = Color.getValue(color);
        Destination orderDestination = Destination.getValue(destination);
        if (orderSize.equals(Size.UNKNOWN)) {
            eventualError.add("size");
        }
        if (orderColor.equals(Color.UNKNOWN)) {
            eventualError.add("color");
        }
        if (orderDestination.equals(Destination.UNKNOWN)) {
            eventualError.add("destination");
        }
        if (eventualError.isEmpty()) {
            int idOfOrder = id.getAndIncrement();
            orders.put(idOfOrder, new Order(idOfOrder, new TShirt(orderSize, orderColor), orderDestination));
            return Response.create(idOfOrder);
        }
        deniedOrders.add(new Order(-1, new TShirt(orderSize, orderColor), orderDestination));
        return Response.decline(String.join(",", eventualError));
    }

    /**
     * Retrieves a T-Shirt order with the given id
     *
     * @param id order id
     * @return response which contains status and an order with the given id
     * @throws IllegalArgumentException if id is a non-positive number
     */
    @Override
    public Response getOrderById(int id) {
        if (!orders.containsKey(id)) {
            return Response.notFound(id);
        }
        return Response.ok(List.of(orders.get(id)));
    }

    /**
     * Retrieves all T-Shirt orders
     *
     * @return response which contains status and  all T-Shirt orders from the repository, in undefined order.
     * If there are no orders in the repository, returns an empty collection.
     */
    @Override
    public Response getAllOrders() {
        return Response.ok(Stream.concat(orders.values().stream(), deniedOrders.stream()).toList());
    }

    /**
     * Retrieves all successful orders for T-Shirts
     *
     * @return response which contains status and all successful orders for T-Shirts from the repository,
     * in undefined order.
     * If there are no such orders in the repository, returns an empty collection.
     */
    @Override
    public Response getAllSuccessfulOrders() {
        return Response.ok(orders.values()
                .stream()
                .toList());
    }
}
