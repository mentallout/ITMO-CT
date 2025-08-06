import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Wspp {
    public static void main(String[] args) {
        try {
            try (MyScanner reader = new MyScanner(args[0], StandardCharsets.UTF_8)) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                    int cnt = 0;
                    Map<String, ArrayList<Integer>> m = new LinkedHashMap<>();
                    while (reader.hasNext()) {
                        ArrayList<String> words = new ArrayList<>(reader.getWords());
                        for (String word : words) {
                            cnt++;
                            ArrayList<Integer> vls = m.getOrDefault(word.toLowerCase(), new ArrayList<>());
                            vls.add(cnt);
                            m.put(word.toLowerCase(), vls);
                        }
                    }
                    for (Map.Entry<String, ArrayList<Integer>> entry : m.entrySet()) {
                        ArrayList<Integer> values = new ArrayList<>(entry.getValue());
                        writer.write(entry.getKey() + " " + values.size());
                        for (Integer value : values) {
                            writer.write(" " + value);
                        }
                        writer.write(System.lineSeparator());
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("Output file error: " + e.getMessage());
                }
            } catch (FileNotFoundException e) {
                System.err.println("Input file error: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
