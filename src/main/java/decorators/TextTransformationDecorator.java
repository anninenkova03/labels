package decorators;

import labels.Label;
import transformations.TextTransformation;
import java.util.Objects;

public class TextTransformationDecorator extends LabelDecoratorBase {
    private final TextTransformation transformation;

    public TextTransformationDecorator(Label label, TextTransformation transformation) {
        super(label);
        this.transformation = transformation;
    }

    @Override
    public String getText() {
        return transformation.transform(label.getText());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextTransformationDecorator that = (TextTransformationDecorator) o;
        return Objects.equals(transformation, that.transformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transformation);
    }
}
