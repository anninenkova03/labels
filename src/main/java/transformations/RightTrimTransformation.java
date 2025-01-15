package transformations;

public class RightTrimTransformation implements TextTransformation {
    @Override
    public String transform(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("\\s+$", "");
    }
}
