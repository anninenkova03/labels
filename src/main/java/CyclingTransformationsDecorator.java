import java.util.List;

public class CyclingTransformationsDecorator extends LabelDecoratorBase{
    private final List<TextTransformation> transformations;
    private int currentIndex = 0;

    public CyclingTransformationsDecorator(Label label, List<TextTransformation> transformations) {
        super(label);
        this.transformations = transformations;
    }

    @Override
    public String getText() {
        TextTransformation transformation = transformations.get(currentIndex);
        currentIndex = (currentIndex + 1) % transformations.size();
        return transformation.transform(label.getText());
    }
}