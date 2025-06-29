package info.kgeorgiy.ja.aksenova.walk;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Walk {
    static final String ALGORITHM = "SHA-256";
    static final Charset CHARSET = StandardCharsets.UTF_8;
    static final int ARRAY_SIZE = 1024;
    static final int SHA_SIZE = 16;
    static MessageDigest md;

    private static Path getPath(String filename) {
        try {
            return Path.of(filename);
        } catch (InvalidPathException e) {
            System.err.println("Error getting path! " + filename);
            return null;
        }
    }

    private static String getHash(Path path) throws NoSuchAlgorithmException {
        md.reset();
        try (BufferedInputStream br = new BufferedInputStream(new FileInputStream(path.toFile()))) {
            byte[] byteArray = new byte[ARRAY_SIZE];
            int bytesRead;
            while ((bytesRead = br.read(byteArray)) >= 0) {
                md.update(byteArray, 0, bytesRead);
            }
        } catch (IOException e) {
            System.err.println("I/O error occured! Can't get hash! " + e);
            return "0".repeat(SHA_SIZE);
        }
        StringBuilder hash = new StringBuilder();
        for (byte b : md.digest()) {
            hash.append(String.format("%02x", b));
        }
        return hash.substring(0, SHA_SIZE);
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[1] == null) {
            System.err.println("Wrong input!");
            return;
        }
        Path inputPath, outputPath;
        inputPath = getPath(args[0]);
        outputPath = getPath(args[1]);
        if (inputPath == null || outputPath == null) {
            return;
        }
        if (outputPath.getParent() != null) {
            try {
                Files.createDirectories(outputPath.getParent());
            } catch (IOException e) {
                System.err.println("Can't access output file! " + e.getMessage());
            }
        }
        try (BufferedReader br = Files.newBufferedReader(inputPath, CHARSET)) {
            try (BufferedWriter bw = Files.newBufferedWriter(outputPath, CHARSET)) {
                String filename;
                md = MessageDigest.getInstance(ALGORITHM);
                while ((filename = br.readLine()) != null) {
                    String hash;
                    Path path = getPath(filename);
                    if (path != null) {
                        try {
                            hash = getHash(path);
                        } catch (NoSuchAlgorithmException e) {
                            System.err.println("No such algorithm! " + e.getMessage());
                            return;
                        }
                    } else {
                        hash = "0".repeat(SHA_SIZE);
                    }
                    bw.write(hash + ' ' + filename + System.lineSeparator());
                }
            } catch (IOException e) {
                System.err.println("Error occured while writing! " + e);
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No such algorithm! " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file! " + e.getMessage());
        }
    }
}