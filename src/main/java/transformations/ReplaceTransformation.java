package transformations;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplaceTransformation that = (ReplaceTransformation) o;
        return Objects.equals(A, that.A) && Objects.equals(B, that.B);
    }

    @Override
    public int hashCode() {
        return Objects.hash(A, B);
    }
}
