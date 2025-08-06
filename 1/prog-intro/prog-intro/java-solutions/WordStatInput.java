import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class WordStatInput {
    public static void main(String[] args) {
        try {
            try (MyScanner reader = new MyScanner(args[0], StandardCharsets.UTF_8)) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                    Map<String, Integer> m = new LinkedHashMap<>();
                    while (reader.hasNext()) {
                        ArrayList<String> words = new ArrayList<>(reader.getWords());
                        for (String word : words) {
                            m.put(word.toLowerCase(), m.getOrDefault(word.toLowerCase(), 0) + 1);
                        }
                    }
                    for (Map.Entry<String, Integer> entry : m.entrySet()) {
                        writer.write(entry.getKey() + " " + entry.getValue() + System.lineSeparator());
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("Output file error: " + e.getMessage());
                }
            } catch (FileNotFoundException e) {
                System.err.println("Input file error: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}