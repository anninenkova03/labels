import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TextTransformationTests {

    @Test
    public void testCapitalizeTransformation() {
        TextTransformation capitalize = new CapitalizeTransformation();
        assertEquals("Hello", capitalize.transform("hello"));
        assertEquals("Hello world", capitalize.transform("hello world"));
        assertEquals("4 digits", capitalize.transform("4 digits"));
        assertEquals("", capitalize.transform(""));
    }

    @Test
    public void testLeftTrimTransformation() {
        TextTransformation leftTrim = new LeftTrimTransformation();
        assertEquals("Hello", leftTrim.transform("   Hello"));
        assertEquals("Hello world", leftTrim.transform("\t\n Hello world"));
        assertEquals("", leftTrim.transform("   "));
    }

    @Test
    public void testRightTrimTransformation() {
        TextTransformation rightTrim = new RightTrimTransformation();
        assertEquals("Hello", rightTrim.transform("Hello   "));
        assertEquals("Hello world", rightTrim.transform("Hello world \t\n"));
        assertEquals("", rightTrim.transform("   "));
    }

    @Test
    public void testNormalizeSpaceTransformation() {
        TextTransformation normalizeSpace = new NormalizeSpaceTransformation();
        assertEquals("Hello world", normalizeSpace.transform("Hello    world"));
    }

    @Test
    public void testDecorateTransformation() {
        TextTransformation decorate = new DecorateTransformation();
        assertEquals("-={ hello }=-", decorate.transform("hello"));
        assertEquals("", decorate.transform(""));
    }

    @Test
    public void testCensorTransformation() {
        TextTransformation censor = new CensorTransformation("hello");
        assertEquals("***** world", censor.transform("hello world"));
        assertEquals("***** world, *****!", censor.transform("Hello world, hello!"));
        assertEquals("*****", censor.transform("hello"));
    }

    @Test
    public void testReplaceTransformation() {
        TextTransformation replace = new ReplaceTransformation("Hello", "Goodbye");
        assertEquals("Goodbye world", replace.transform("Hello world"));
        assertEquals("Goodbye world! Goodbye!", replace.transform("Hello world! Hello!"));
        assertEquals("hello world", replace.transform("hello world"));
    }
}

