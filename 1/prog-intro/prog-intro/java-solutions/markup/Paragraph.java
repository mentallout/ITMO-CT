package markup;

import java.util.List;

public class Paragraph implements Markup {
    protected final List<Markup> list;

    public Paragraph(List<Markup> data) {
        this.list = data;
    }

    public void toMarkdown(StringBuilder string) {
        MarkupFilling.fillingMarkup(string, "", list);
    }

    public void toBBCode(StringBuilder string) {
        MarkupFilling.fillingBBCode(string, "", "", list);
    }
}
