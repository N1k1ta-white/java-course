package bg.sofia.uni.fmi.mjt.order.server;

import bg.sofia.uni.fmi.mjt.order.server.repository.MJTOrderRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ClientRequestHandler implements Runnable {
    private Socket socket;

    private MJTOrderRepository rep;

    private Response doRequest(List<String> args) {
        final int size = 0;
        final int color = 1;
        final int destination = 2;
        return rep.request(args.get(size), args.get(color), args.get(destination));
    }

    private Response getResponse(String inputLine) {
        List<String> input = Arrays.stream(inputLine.split(" ")).toList();
        Iterator<String> iter = input.iterator();
        String command = iter.next();
        if (command.equals("request")) {
            return doRequest(input.subList(1, input.size())
                    .stream()
                    .map(inp -> inp.substring(inp.lastIndexOf("=") + 1))
                    .toList());
        } else if (command.equals("get")) {
            command = iter.next();
            if (command.equals("all")) {
                return rep.getAllOrders();
            } else if (command.equals("all-successful")) {
                return rep.getAllSuccessfulOrders();
            } else if (command.equals("my-order")) {
                command = iter.next();
                return rep.getOrderById(Integer.parseInt(command.substring(command.lastIndexOf("=") + 1)));
            }
        }
        throw new IllegalArgumentException("Unknown command");
    }

    public ClientRequestHandler(Socket socket, MJTOrderRepository rep) {
        this.socket = socket;
        this.rep = rep;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Client Request Handler for " + socket.getRemoteSocketAddress());

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) { // read the message from the client
                System.out.println("Message received from client: " + inputLine);
                try {
                    out.println(getResponse(inputLine)); // send response back to the client
                } catch (Exception e) {
                    out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
