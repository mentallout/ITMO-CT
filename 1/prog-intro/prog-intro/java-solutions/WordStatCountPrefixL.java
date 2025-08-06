import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class WordStatCountPrefixL {
    public static boolean isPartOfWord(Character character) {
        return (Character.isLetter(character)) || (character == '\'') || (Character.getType(character) == Character.DASH_PUNCTUATION);
    }

    public static void main(String[] args) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8))) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                    Map<String, Integer> m = new LinkedHashMap<>();
                    int start;
                    String line;
                    int sub = 3;
                    while ((line = reader.readLine()) != null) {
                        line = line.toLowerCase();
                        start = -1;
                        String word;
                        for (int i = 0; i < line.length(); i++) {
                            Character ch = line.charAt(i);
                            if (isPartOfWord(ch)) {
                                if (start == -1) {
                                    start = i;
                                } else {
                                    if ((i == line.length() - 1) && (i - start >= sub + 1)) {
                                        word = line.substring(start, start + sub);
                                        m.put(word, m.getOrDefault(word, 0) + 1);
                                    }
                                }
                            } else {
                                if ((start != -1) && (i - start >= sub)) {
                                    word = line.substring(start, start + sub);
                                    m.put(word, m.getOrDefault(word, 0) + 1);
                                }
                                start = -1;
                            }
                        }
                    }
                    List<Map.Entry<String, Integer>> list = new ArrayList<>(m.entrySet());
                    list.sort(Map.Entry.comparingByValue());
                    for (Map.Entry<String, Integer> entry : list) {
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