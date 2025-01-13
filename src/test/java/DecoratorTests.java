import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class DecoratorTests {

    @Test
    public void testTextTransformationDecorator() {
        Label label = new SimpleLabel("hello world");
        Label capitalizeLabel = new TextTransformationDecorator(label, new CapitalizeTransformation());
        assertEquals("Hello world", capitalizeLabel.getText());
    }

    @Test
    public void testRandomTransformationDecorator() {
        Label label = new SimpleLabel("   hello world   ");
        List<TextTransformation> transformations = List.of(new RightTrimTransformation(), new LeftTrimTransformation());
        Label randomDecorator = new RandomTransformationDecorator(label, transformations);
        String result = randomDecorator.getText();
        assertTrue(result.equals("hello world   ") || result.equals("   hello world"));
    }

    @Test
    public void testCyclingTransformationsDecorator() {
        Label label = new SimpleLabel("   hello world   ");
        List<TextTransformation> transformations = List.of(new LeftTrimTransformation(), new RightTrimTransformation());
        Label cyclingDecorator = new CyclingTransformationsDecorator(label, transformations);

        assertEquals("hello world   ", cyclingDecorator.getText());
        assertEquals("   hello world", cyclingDecorator.getText());
        assertEquals("hello world   ", cyclingDecorator.getText());
    }

    @Test
    public void testChainingMultipleDecorators() {
        Label label = new SimpleLabel("  hello world  ");

        Label decoratedLabel = new TextTransformationDecorator(
                new TextTransformationDecorator(
                        new TextTransformationDecorator(label, new LeftTrimTransformation()),
                        new CapitalizeTransformation()
                ),
                new RightTrimTransformation()
        );

        assertEquals("Hello world", decoratedLabel.getText());
    }

    @Test
    public void testChainingRandomAndCyclingDecorators() {
        Label label = new SimpleLabel("  hello world  ");
        List<TextTransformation> transformations = List.of(
                new LeftTrimTransformation(),
                new CapitalizeTransformation(),
                new RightTrimTransformation()
        );

        Label randomAndCyclingLabel = new CyclingTransformationsDecorator(
                new RandomTransformationDecorator(label, transformations),
                transformations
        );

        String result = randomAndCyclingLabel.getText();
        assertTrue(result.equals("Hello world  ") || result.equals("  hello world") || result.equals("hello world"));
    }

}