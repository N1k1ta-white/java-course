package bg.sofia.uni.fmi.mjt.order.server;

import bg.sofia.uni.fmi.mjt.order.server.repository.MJTOrderRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShopServer {
    private static final int SERVER_PORT = 8080;
    private static Map<Socket, MJTOrderRepository> rep;

    static {
        rep = new HashMap<>();
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        Thread.currentThread().setName("Server");

        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started and ready for getting requests");
            Socket client;
            while (true) {
                client = server.accept();
                rep.put(client, new MJTOrderRepository());
                ClientRequestHandler handler = new ClientRequestHandler(client, rep.get(client));
                executor.submit(handler);
                System.out.println("Accepted connection request from client: " + client.getInetAddress());
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }
}
