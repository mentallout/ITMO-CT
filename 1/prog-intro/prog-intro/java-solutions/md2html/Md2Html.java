package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    BufferedReader reader;
    BufferedWriter writer;

    String line;
    StringBuilder block = new StringBuilder();
    int header_count = 0;

    public Md2Html(String in, String out) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(in), StandardCharsets.UTF_8));
        this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8));
    }

    public void close() throws IOException {
        this.reader.close();
        this.writer.close();
    }

    private void toHtml() throws IOException {
        while ((line = reader.readLine()) != null) {
            block.setLength(0);
            while ((line != null) && (!line.isEmpty())) {
                block.append(line).append(System.lineSeparator());
                line = reader.readLine();
            }
            if (block.length() - System.lineSeparator().length() > 0) {
                markup();
                paragraph();
                writer.write(block.toString());
            }
        }
        close();
    }

    private void paragraph() {
        int count = 0;
        if (!block.toString().startsWith("#")) {
            block.insert(0, "<p>").insert(block.length() - System.lineSeparator().length(), "</p>");
        } else {
            for (int i = 0; i < block.length(); i++) {
                if (block.charAt(i) == '#') {
                    count++;
                    block.deleteCharAt(i);
                    i--;
                } else if (block.charAt(i) == ' ') {
                    if (count > 0) {
                        header_count = count;
                        block.deleteCharAt(i);
                        block.insert(0, "<h" + header_count + ">").insert(block.length() - System.lineSeparator().length(), "</h" + header_count + ">");
                        break;
                    }
                } else {
                    block.insert(0, "<p>" + "#".repeat(count)).insert(block.length() - System.lineSeparator().length(), "</p>");
                    break;
                }
            }
        }
    }

    private void markup() {
        for (int i = 0; i < block.length(); i++) {
            if (block.charAt(i) == '<') {
                block.deleteCharAt(i);
                block.insert(i, "&lt;");
                i = i + 3;
            } else if (block.charAt(i) == '&') {
                block.deleteCharAt(i);
                block.insert(i, "&amp;");
                i = i + 4;
            } else if (block.charAt(i) == '>') {
                block.deleteCharAt(i);
                block.insert(i, "&gt;");
                i = i + 3;
            }
        }
        for (int i = 0; i < block.length(); i++) {
            if (block.charAt(i) == '`') {
                block.deleteCharAt(i);
                block.insert(i, "<code>");
                for (int j = i + "<code>".length(); j < block.length(); j++) {
                    if (block.charAt(j) == '`') {
                        block.deleteCharAt(j);
                        block.insert(j, "</code>");
                        break;
                    }
                }
            } else if (((block.charAt(i) == '*') && (block.charAt(i + 1) == '*')) || ((block.charAt(i) == '_') && (block.charAt(i + 1) == '_'))) {
                char mark2 = block.charAt(i);
                for (int j = i + 2; j < block.length() - 1; j++) {
                    if ((block.charAt(j) == mark2) && (block.charAt(j + 1) == mark2)) {
                        block.deleteCharAt(j).deleteCharAt(j);
                        block.insert(j, "</strong>");
                        block.deleteCharAt(i).deleteCharAt(i);
                        block.insert(i, "<strong>");
                        break;
                    }
                }
            } else if ((block.charAt(i) == '-') && (block.charAt(i + 1) == '-')) {
                for (int j = i + 2; j < block.length(); j++) {
                    if ((block.charAt(j) == '-') && (block.charAt(j + 1) == '-')) {
                        block.deleteCharAt(j).deleteCharAt(j);
                        block.insert(j, "</s>");
                        block.deleteCharAt(i).deleteCharAt(i);
                        block.insert(i, "<s>");
                        break;
                    }
                }
            } else if ((block.charAt(i) == '\'') && (block.charAt(i + 1) == '\'')) {
                for (int j = i + 2; j < block.length(); j++) {
                    if ((block.charAt(j) == '\'') && (block.charAt(j + 1) == '\'')) {
                        block.deleteCharAt(j).deleteCharAt(j);
                        block.insert(j, "</q>");
                        block.deleteCharAt(i).deleteCharAt(i);
                        block.insert(i, "<q>");
                        break;
                    }
                }
            } else if ((block.charAt(i) == '*') || (block.charAt(i) == '_')) {
                char mark = block.charAt(i);
                for (int j = i + 1; j < block.length(); j++) {
                    if (block.charAt(j) == mark) {
                        if (block.charAt(j + 1) == mark) {
                            j = j + 2;
                        } else if (block.charAt(j - 1) != '\\') {
                            block.deleteCharAt(j);
                            block.insert(j, "</em>");
                            block.deleteCharAt(i);
                            block.insert(i, "<em>");
                            break;
                        }
                    }
                }
            } else if ((block.charAt(i) == '\\') && ((block.charAt(i + 1) == '*') || (block.charAt(i + 1) == '_') || (block.charAt(i + 1) == '\''))) {
                block.deleteCharAt(i);
            }
        }
    }


    public static void main(String[] args) {
        try {
            Md2Html converter = new Md2Html(args[0], args[1]);
            converter.toHtml();
            converter.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
