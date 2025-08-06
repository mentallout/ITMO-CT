package markup;

public class Text implements Markup {
    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder string) {
        string.append(text);
    }

    @Override
    public void toBBCode(StringBuilder string) {
        string.append(text);
    }
}
