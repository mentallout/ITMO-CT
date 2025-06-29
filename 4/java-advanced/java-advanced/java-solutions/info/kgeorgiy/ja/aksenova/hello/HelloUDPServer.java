package info.kgeorgiy.ja.aksenova.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.*;
import java.util.stream.IntStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static info.kgeorgiy.ja.aksenova.hello.GeneralCode.*;

/**
 * Implementation of {@link HelloServer} using UDP protocol.
 * The server listens for incoming UDP requests, processes them, and sends responses.
 */
public class HelloUDPServer implements HelloServer {
    private ExecutorService executor;
    private DatagramSocket socket;

    /**
     * Starts a new Hello server.
     * This method should return immediately.
     *
     * @param port    server port.
     * @param threads number of working threads.
     */
    @Override
    public void start(int port, int threads) {
        try {
            this.socket = new DatagramSocket(port);
            this.executor = Executors.newFixedThreadPool(threads);
            IntStream.range(1, threads + 1).forEach(i -> this.executor.submit(() -> {
                int size = getSocketBufferSize(this.socket);
                DatagramPacket packet = createPacket(size);
                while (!this.socket.isClosed() && !Thread.currentThread().isInterrupted()) {
                    try {
                        this.socket.receive(packet);
                        String request = extractMessage(packet, CHARSET);
                        sendResponse(this.socket, "Hello, " + request, CHARSET, packet);
                    } catch (IOException e) {
                        if (!this.socket.isClosed()) {
                            System.err.println("Couldn't handle packet! " + e.getMessage());
                        }
                    }
                }
            }));
        } catch (SocketException e) {
            System.err.println("Failed to start a server! " + e.getMessage());
        }
    }

    /**
     * Stops server and deallocates all resources.
     */
    @Override
    public void close() {
        if (this.socket != null && !this.socket.isClosed()) {
            this.socket.close();
        }
        shutdownExecutor(this.executor);
    }

    /**
     * Entry point for the HelloUDPServer.
     * Starts the server with the specified port and number of threads.
     *
     * @param args command-line arguments: <port> <threads>.
     */
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.err.println("Usage: HelloUDPServer <port> <threads>");
            return;
        }
        try {
            int port = Integer.parseInt(args[0]);
            int threads = Integer.parseInt(args[1]);
            try (HelloUDPServer server = new HelloUDPServer()) {
                server.start(port, threads);
            }
        } catch (NumberFormatException e) {
            System.err.println("Port and threads must be integers! " + e.getMessage());
        }
    }
}
