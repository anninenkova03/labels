abstract class LabelDecoratorBase implements Label {
    protected Label label;

    public LabelDecoratorBase(Label label) {
        this.label = label;
    }

    @Override
    public String getText() {
        return label.getText();
    }
}
