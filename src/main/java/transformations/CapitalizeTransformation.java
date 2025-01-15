package transformations;

public class CapitalizeTransformation implements TextTransformation {
    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty() || !Character.isLetter(text.charAt(0))) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
