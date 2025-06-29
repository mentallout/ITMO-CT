package info.kgeorgiy.ja.aksenova.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;
import info.kgeorgiy.java.advanced.hello.NewHelloClient;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static info.kgeorgiy.ja.aksenova.hello.GeneralCode.*;

/**
 * Implementation of {@link HelloClient} and {@link NewHelloClient} using UDP protocol.
 */
public class HelloUDPClient implements HelloClient, NewHelloClient {
    /**
     * Runs Hello client.
     * This method should return when all requests are completed.
     *
     * @param requests requests to perform in each thread.
     * @param threads  number of request threads.
     */
    @Override
    public void newRun(List<Request> requests, int threads) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        IntStream.range(1, threads + 1).forEach(thread -> executor.submit(() -> {
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(TIMEOUT);
                int size = getSocketBufferSize(socket);
                DatagramPacket responsePacket = createPacket(size);
                for (Request request : requests) {
                    String message = request.template().replace("$", String.valueOf(thread));
                    InetSocketAddress address = new InetSocketAddress(request.host(), request.port());
                    byte[] requestData = message.getBytes(CHARSET);
                    DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, address);
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            socket.send(requestPacket);
                            socket.receive(responsePacket);
                            String response = extractMessage(responsePacket, CHARSET);
                            if (response.contains(message)) {
                                break;
                            }
                        } catch (IOException e) {
                            System.err.println("Failed to send or receive packet: " + e.getMessage());
                        }
                    }
                }
            } catch (SocketException e) {
                System.err.println("Socket error: " + e.getMessage());
            }
        }));
        shutdownExecutor(executor);
    }

    /**
     * Entry point for the HelloUDPClient.
     * Runs the client with the specified host, port, prefix, number of threads, and requests.
     *
     * @param args command-line arguments: <host> <port> <prefix> <threads> <requests>.
     */
    public static void main(String[] args) {
        if (args == null || args.length != 5) {
            System.err.println("Usage: HelloUDPClient <host> <port> <prefix> <threads> <requests>");
            return;
        }
        try {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            String prefix = args[2];
            int threads = Integer.parseInt(args[3]);
            int requests = Integer.parseInt(args[4]);
            new HelloUDPClient().run(host, port, prefix, requests, threads);
        } catch (NumberFormatException e) {
            System.err.println("Port, threads, and requests must be integers! " + e.getMessage());
        }
    }
}
