package markup;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Paragraph paragraph = new Paragraph(List.of(
                new Strong(List.of(
                        new Text("1"),
                        new Strikeout(List.of(
                                new Text("2"),
                                new Emphasis(List.of(
                                        new Text("3"),
                                        new Text("4")
                                )),
                                new Text("5")
                        )),
                        new Text("6")
                ))
        ));
        StringBuilder string1 = new StringBuilder();
        paragraph.toMarkdown(string1);
        System.out.println(string1);
        StringBuilder string2 = new StringBuilder();
        paragraph.toBBCode(string2);
        System.out.println(string2);
    }
}
/*
__1~2*34*5~6__
[b]1[s]2[i]34[/i]5[/s]6[/b]
 */