public class ReplaceTransformation implements TextTransformation {
    private final String A;
    private final String B;

    public ReplaceTransformation(String A, String B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public String transform(String text) {
        if (text == null || A == null || B == null) {
            return text;
        }
        return text.replace(A, B);
    }
}
