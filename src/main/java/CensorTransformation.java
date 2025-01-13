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
}
