import java.util.List;
import java.util.Random;

public class RandomTransformationDecorator extends LabelDecoratorBase {
    private final List<TextTransformation> transformations;
    private final Random random;

    public RandomTransformationDecorator(Label label, List<TextTransformation> transformations) {
        super(label);
        this.transformations = transformations;
        this.random = new Random();
    }

    @Override
    public String getText() {
        TextTransformation transformation = transformations.get(random.nextInt(transformations.size()));
        return transformation.transform(label.getText());
    }
}
