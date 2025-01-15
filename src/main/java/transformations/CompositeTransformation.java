package transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompositeTransformation implements TextTransformation {
    private final List<TextTransformation> transformations = new ArrayList<>();

    public CompositeTransformation(List<TextTransformation> transformations) {
        if (transformations != null) {
            this.transformations.addAll(transformations);
        }
    }

    public void addTransformation(TextTransformation transformation) {
        transformations.add(transformation);
    }

    @Override
    public String transform(String text) {
        for (TextTransformation transformation : transformations) {
            text = transformation.transform(text);
        }
        return text;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeTransformation that = (CompositeTransformation) o;
        return Objects.equals(transformations, that.transformations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transformations);
    }
}
