package transformations;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CensorTransformation implements TextTransformation{
    private final String W;

    public CensorTransformation(String W) {
        this.W = W;
    }

    @Override
    public String transform(String text) {
        if (text == null || W == null || W.isEmpty()) {
            return text;
        }

        String replacement = "*".repeat(W.length());

        Pattern pattern = Pattern.compile(W, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        String result = text;
        while (matcher.find()) {
            String match = text.substring(matcher.start(), matcher.end());
            result = result.replace(match, replacement);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CensorTransformation that = (CensorTransformation) o;
        return Objects.equals(W, that.W);
    }

    @Override
    public int hashCode() {
        return Objects.hash(W);
    }
}
