public class DecorateTransformation implements TextTransformation {
    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return "-={ " + text + " }=-";
    }
}
