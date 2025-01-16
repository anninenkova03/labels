import decorators.*;
import labels.*;
import transformations.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DecoratorRemovalTests {

    @Test
    public void testRemoveDecoratorWhenNoDecoratorsPresent() {
        Label label = new SimpleLabel("Just Text");
        TextTransformationDecorator decoratorToRemove =
                new TextTransformationDecorator(null, new CapitalizeTransformation());
        label = LabelDecoratorBase.removeDecoratorFrom(label, decoratorToRemove);
        assertEquals("Just Text", label.getText());
    }

    @Test
    public void testRemoveSingleDecorator() {
        Label label = new SimpleLabel("Hello World");
        TextTransformation transformation = new CensorTransformation("world");
        label = new TextTransformationDecorator(label, transformation);

        assertEquals("Hello *****", label.getText());

        TextTransformationDecorator decoratorToRemove =
                new TextTransformationDecorator(null, new CensorTransformation("world"));
        label = LabelDecoratorBase.removeDecoratorFrom(label, decoratorToRemove);

        assertEquals("Hello World", label.getText());
    }

    @Test
    public void testRemoveSpecificDecoratorWithProperties() {
        labels.Label label = new labels.SimpleLabel("Hello abc def");
        TextTransformation censorTransformation1 = new CensorTransformation("abc");
        TextTransformation censorTransformation2 = new CensorTransformation("def");

        label = new TextTransformationDecorator(label, censorTransformation1);
        label = new TextTransformationDecorator(label, censorTransformation2);

        assertEquals("Hello *** ***", label.getText());

        LabelDecoratorBase decoratorToRemove =
                new TextTransformationDecorator(null, new CensorTransformation("abc"));
        label = LabelDecoratorBase.removeDecoratorFrom(label, decoratorToRemove);

        assertEquals("Hello abc ***", label.getText());
    }

    @Test
    public void testRemoveNonExistentDecorator() {
        Label label = new SimpleLabel("Hello World");
        TextTransformation transformation = new ReplaceTransformation("Hello", "Hi");
        label = new TextTransformationDecorator(label, transformation);

        TextTransformationDecorator decoratorToRemove =
                new TextTransformationDecorator(null, new CensorTransformation("Hi"));
        label = decorators.LabelDecoratorBase.removeDecoratorFrom(label, decoratorToRemove);

        assertEquals("Hi World", label.getText());
    }

    @Test
    public void testRemoveDecoratorFromMultipleChainedDecorators() {
        Label label = new labels.SimpleLabel("sample Text");
        label = new TextTransformationDecorator(label, new CapitalizeTransformation());
        label = new TextTransformationDecorator(label, new ReplaceTransformation("Text", "Word"));
        label = new TextTransformationDecorator(label, new CensorTransformation("Word"));

        assertEquals("Sample ****", label.getText());

        TextTransformationDecorator decoratorToRemove =
                new TextTransformationDecorator(null, new ReplaceTransformation("Text", "Word"));
        label = decorators.LabelDecoratorBase.removeDecoratorFrom(label, decoratorToRemove);

        assertEquals("Sample Text", label.getText());
    }

}
