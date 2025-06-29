package info.kgeorgiy.ja.aksenova.hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class GeneralCode {
    /**
     * Default charset used for encoding and decoding messages.
     */
    public static final Charset CHARSET = StandardCharsets.UTF_8;
    /**
     * Default timeout in milliseconds for socket operations.
     */
    public static final int TIMEOUT = 100;

    /**
     * Retrieves the buffer size of the given socket.
     *
     * @param socket the {@link DatagramSocket} to retrieve the buffer size from.
     * @return the buffer size, or 0 if an error occurs.
     */
    public static int getSocketBufferSize(DatagramSocket socket) {
        try {
            return socket.getReceiveBufferSize();
        } catch (SocketException e) {
            System.err.println("Failed to get buffer size! " + e.getMessage());
            return 0;
        }
    }

    /**
     * Creates a new {@link DatagramPacket} with the specified size.
     *
     * @param size the size of the packet.
     * @return a new {@link DatagramPacket}.
     */
    public static DatagramPacket createPacket(int size) {
        return new DatagramPacket(new byte[size], size);
    }

    /**
     * Extracts a message from the given {@link DatagramPacket} using the specified charset.
     *
     * @param packet  the {@link DatagramPacket} containing the message.
     * @param charset the {@link Charset} to decode the message.
     * @return the extracted message as a {@link String}.
     */
    public static String extractMessage(DatagramPacket packet, Charset charset) {
        return new String(packet.getData(), packet.getOffset(), packet.getLength(), charset);
    }

    /**
     * Sends a response using the given socket and request packet.
     *
     * @param socket        the {@link DatagramSocket} to send the response.
     * @param response      the response message to send.
     * @param charset       the {@link Charset} to encode the response.
     * @param requestPacket the original request {@link DatagramPacket}.
     */
    public static void sendResponse(DatagramSocket socket, String response, Charset charset, DatagramPacket requestPacket) {
        byte[] data = response.getBytes(charset);
        DatagramPacket responsePacket = new DatagramPacket(data, data.length, requestPacket.getSocketAddress());
        try {
            socket.send(responsePacket);
        } catch (IOException e) {
            System.err.println("Failed to send response! " + e.getMessage());
        }
    }

    /**
     * Shuts down the given {@link ExecutorService} gracefully.
     *
     * @param executor the {@link ExecutorService} to shut down.
     */
    public static void shutdownExecutor(ExecutorService executor) {
        if (executor != null) {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted while shutting down executor! " + e.getMessage());
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
