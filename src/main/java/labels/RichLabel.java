package labels;

public class RichLabel implements Label {
    public String value;
    public String color;
    public int fontSize;
    public String fontName;

    public RichLabel(String value, String color, int fontSize, String fontName) {
        this.value = value;
        this.color = color;
        this.fontSize = fontSize;
        this.fontName = fontName;
    }

    public String getColor() {
        return color;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getFontName() {
        return fontName;
    }

    @Override
    public String getText() {
        return value;
    }
}
