package decorators;

import labels.Label;

public abstract class LabelDecoratorBase implements Label {
    protected Label label;

    public LabelDecoratorBase(Label label) {
        this.label = label;
    }

    @Override
    public String getText() {
        return label.getText();
    }

    public static Label removeDecoratorFrom(Label label, LabelDecoratorBase decorator) {
        if (label == null) {
            return null;
        } else if (LabelDecoratorBase.class.isAssignableFrom(label.getClass())) {
            LabelDecoratorBase ldb = (LabelDecoratorBase) label;
            return ldb.removeDecorator(decorator);
        } else {
            return label;
        }
    }

    public Label removeDecorator(LabelDecoratorBase decorator) {
        if (this.equals(decorator)) {
            return label;
        } else if (label instanceof LabelDecoratorBase) {
            label = ((LabelDecoratorBase) label).removeDecorator(decorator);
            return this;
        } else {
            return this;
        }
    }

}
