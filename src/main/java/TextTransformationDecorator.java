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
}
