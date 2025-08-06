package markup;

import java.util.List;

public interface MarkupFilling {
    static void fillingMarkup(StringBuilder string, String mark, List<Markup> list) {
        string.append(mark);
        for (Markup str : list) {
            str.toMarkdown(string);
        }
        string.append(mark);
    }

    static void fillingBBCode(StringBuilder string, String markA, String markB, List<Markup> list) {
        string.append(markA);
        for (Markup str : list) {
            str.toBBCode(string);
        }
        string.append(markB);
    }
}
