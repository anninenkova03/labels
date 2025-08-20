package transformations;

import java.util.HashMap;
import java.util.Map;

public class CensorTransformationFactory {
    private final Map<String, TextTransformation> flyweightCache = new HashMap<>();

    public TextTransformation getTextTransformation(String W) {
        if (W.length() <= 4) {
            return flyweightCache.computeIfAbsent(W, CensorTransformation::new);
        } else {
            return new CensorTransformation(W);
        }
    }

    public int getFlyweightCount() {
        return flyweightCache.size();
    }
}
