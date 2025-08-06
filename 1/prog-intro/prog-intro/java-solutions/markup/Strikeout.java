package markup;

import java.util.List;

public class Strikeout implements Markup {
    private final List<Markup> list;

    public Strikeout(List<Markup> data) {
        this.list = data;
    }

    @Override
    public void toMarkdown(StringBuilder string) {
        MarkupFilling.fillingMarkup(string, "~", list);
    }

    @Override
    public void toBBCode(StringBuilder string) {
        MarkupFilling.fillingBBCode(string, "[s]", "[/s]", list);
    }
}