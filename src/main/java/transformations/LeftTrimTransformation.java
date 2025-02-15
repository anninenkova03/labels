package transformations;

public class LeftTrimTransformation implements TextTransformation {
    @Override
    public String transform(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("^\\s+", "");
    }
}
