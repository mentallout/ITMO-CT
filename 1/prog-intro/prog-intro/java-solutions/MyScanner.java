import java.io.*;
import java.util.ArrayList;
import java.nio.charset.Charset;

public class MyScanner implements Closeable {
    private final Reader reader;
    private final char[] block = new char[1024];
    private int read;
    private int current;
    private final StringBuilder smth = new StringBuilder();
    private final String lineSeparator = System.lineSeparator();

    public MyScanner(InputStream input) throws IOException {
        this.reader = new InputStreamReader(input);
        reading();
    }

    public MyScanner(String file, Charset cd) throws IOException {
        this.reader = new InputStreamReader(new FileInputStream(file), cd);
        reading();
    }

    public MyScanner(String string) throws IOException {
        this.reader = new StringReader(string);
        reading();
    }

    public void close() throws IOException {
        this.reader.close();
    }

    private void reading() throws IOException {
        current = 0;
        read = reader.read(block);
    }

    public boolean hasNextLine() {
        return read != -1;
    }

    public String nextLine() throws IOException {
        StringBuilder line = new StringBuilder();
        while (read != -1) {
            for (; current < read; current++) {
                line.append(block[current]);
                if ((block[current] == lineSeparator.charAt(lineSeparator.length() - 1)) && (line.toString().endsWith(lineSeparator))) {
                    current++;
                    return line.substring(0, line.length() - lineSeparator.length());
                }
            }
            reading();
        }
        return line.toString();
    }

    private boolean isIntable(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException ignored) {
            return false;
        }
        return true;
    }

    public boolean hasNext() throws IOException {
        smth.setLength(0);
        while (read != -1) {
            for (; current < read; current++) {
                if (!Character.isWhitespace(block[current])) {
                    smth.append(block[current]);
                    if ((block[current] == lineSeparator.charAt(lineSeparator.length() - 1)) && (smth.toString().endsWith(lineSeparator))) {
                        if (smth.toString().length() > lineSeparator.length()) {
                            current++;
                            String temp = smth.substring(0, smth.length() - lineSeparator.length());
                            smth.setLength(0);
                            smth.append(temp);
                            return true;
                        } else {
                            smth.setLength(0);
                        }
                    }
                } else if (!smth.isEmpty()) {
                    return true;
                }
            }
            reading();
        }
        return (!smth.isEmpty());
    }

    public ArrayList<String> getWords() throws IOException {
        ArrayList<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < smth.length(); i++) {
            Character ch = smth.charAt(i);
            if ((Character.isLetter(ch)) || (ch == '\'') || (Character.getType(ch) == Character.DASH_PUNCTUATION)) {
                word.append(ch);
            } else if (!word.isEmpty()) {
                words.add(word.toString());
                word.setLength(0);
            }
        }
        if (!word.isEmpty()) {
            words.add(word.toString());
            word.setLength(0);
        }
        return words;
    }

    public boolean hasNextInt() throws IOException {
        return hasNext() && isIntable(smth.toString());
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(smth.toString());
    }

    public String next() throws IOException {
        return smth.toString();
    }
}
