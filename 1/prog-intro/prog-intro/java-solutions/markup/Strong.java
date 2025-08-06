package markup;

import java.util.List;

public class Strong implements Markup {
    private final List<Markup> list;

    public Strong(List<Markup> data) {
        this.list = data;
    }

    @Override
    public void toMarkdown(StringBuilder string) {
        MarkupFilling.fillingMarkup(string, "__", list);
    }

    @Override
    public void toBBCode(StringBuilder string) {
        MarkupFilling.fillingBBCode(string, "[b]", "[/b]", list);
    }
}
