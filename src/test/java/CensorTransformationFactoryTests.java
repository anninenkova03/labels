import org.junit.Test;
import static org.junit.Assert.*;
import transformations.*;

public class CensorTransformationFactoryTests {

    @Test
    public void testFlyweightReuseForShortWords() {
        CensorTransformationFactory factory = new CensorTransformationFactory();

        TextTransformation censor1 = factory.getTextTransformation("test");
        TextTransformation censor2 = factory.getTextTransformation("test");

        assertSame(censor1, censor2);
    }

    @Test
    public void testNewObjectForLongWords() {
        CensorTransformationFactory factory = new CensorTransformationFactory();

        TextTransformation censor1 = factory.getTextTransformation("example");
        TextTransformation censor2 = factory.getTextTransformation("example");

        assertNotSame(censor1, censor2);
    }

    @Test
    public void testTransformationApplication() {
        CensorTransformationFactory factory = new CensorTransformationFactory();
        TextTransformation censor = factory.getTextTransformation("bad");

        String text = "This is a bad example.";
        String censored = censor.transform(text);

        assertEquals("This is a *** example.", censored);
    }

    @Test
    public void testFlyweightCount() {
        CensorTransformationFactory factory = new CensorTransformationFactory();

        factory.getTextTransformation("test");
        factory.getTextTransformation("test");
        factory.getTextTransformation("good");

        assertEquals(2, factory.getFlyweightCount());
    }

}