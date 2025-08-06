import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class WsppPosition {
    public static void main(String[] args) {
        try {
            try (MyScanner reader = new MyScanner(args[0], StandardCharsets.UTF_8)) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                    int cnt = 0;
                    Map<String, List<String>> m = new LinkedHashMap<>();
                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        cnt++;
                        List<String> words = new ArrayList<>();
                        StringBuilder word = new StringBuilder();
                        for (int i = 0; i < line.length(); i++) {
                            char c = line.charAt(i);
                            if (Character.isLetter(c) || c == '\'' || Character.getType(c) == Character.DASH_PUNCTUATION) {
                                word.append(c);
                            } else {
                                if (!word.isEmpty()) {
                                    words.add(word.toString());
                                    word.setLength(0);
                                }
                            }
                        }
                        if (!word.isEmpty()) {
                            words.add(word.toString());
                        }
                        for (int p = 0; p < words.size(); p++) {
                            String token = words.get(p);
                            List<String> vls = m.getOrDefault(token.toLowerCase(), new ArrayList<>());
                            vls.add(cnt + ":" + (words.size() - p));
                            m.put(token.toLowerCase(), vls);
                        }
                    }
                    for (Map.Entry<String, List<String>> entry : m.entrySet()) {
                        List<String> values = new ArrayList<>(entry.getValue());
                        writer.write(entry.getKey() + " " + values.size());
                        for (String value : values) {
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
            System.err.println(e.getMessage());
        }
    }
}
