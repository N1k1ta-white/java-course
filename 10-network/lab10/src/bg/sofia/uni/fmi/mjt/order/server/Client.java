package bg.sofia.uni.fmi.mjt.order.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            Thread.currentThread().setName("Client thread " + socket.getLocalPort());

            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("=> ");
                String command = scanner.nextLine(); // read a line from the console

                if ("disconnect".equals(command)) {
                    System.out.println("Disconnected from the server");
                    break;
                }

                writer.println(command); // send the message to the server

                String resp = reader.readLine();
                System.out.println("=> " + resp);
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }
}
