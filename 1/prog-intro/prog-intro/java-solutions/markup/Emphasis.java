package markup;

import java.util.List;

public class Emphasis implements Markup {
    private final List<Markup> list;

    public Emphasis(List<Markup> data) {
        this.list = data;
    }

    @Override
    public void toMarkdown(StringBuilder string) {
        MarkupFilling.fillingMarkup(string, "*", list);
    }

    @Override
    public void toBBCode(StringBuilder string) {
        MarkupFilling.fillingBBCode(string, "[i]", "[/i]", list);
    }
}
