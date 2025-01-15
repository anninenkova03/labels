import org.junit.Test;
import transformations.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TextTransformationTests {

    @Test
    public void testCapitalizeTransformation() {
        TextTransformation capitalize = new CapitalizeTransformation();
        assertEquals("Hello", capitalize.transform("hello"));
        assertEquals("Hello world", capitalize.transform("hello world"));
        assertEquals("4 digits", capitalize.transform("4 digits"));
        assertEquals("", capitalize.transform(""));
        assertNull(null, capitalize.transform(null));
    }

    @Test
    public void testLeftTrimTransformation() {
        TextTransformation leftTrim = new LeftTrimTransformation();
        assertEquals("Hello", leftTrim.transform("   Hello"));
        assertEquals("Hello world", leftTrim.transform("\t\n Hello world"));
        assertEquals("", leftTrim.transform("   "));
        assertNull(null, leftTrim.transform(null));
    }

    @Test
    public void testRightTrimTransformation() {
        TextTransformation rightTrim = new RightTrimTransformation();
        assertEquals("Hello", rightTrim.transform("Hello   "));
        assertEquals("Hello world", rightTrim.transform("Hello world \t\n"));
        assertEquals("", rightTrim.transform("   "));
        assertNull(null, rightTrim.transform(null));
    }

    @Test
    public void testNormalizeSpaceTransformation() {
        TextTransformation normalizeSpace = new NormalizeSpaceTransformation();
        assertEquals("Hello world", normalizeSpace.transform("Hello    world"));
        assertNull(null, normalizeSpace.transform(null));
    }

    @Test
    public void testDecorateTransformation() {
        TextTransformation decorate = new DecorateTransformation();
        assertEquals("-={ hello }=-", decorate.transform("hello"));
        assertEquals("", decorate.transform(""));
        assertNull(null, decorate.transform(null));
    }

    @Test
    public void testCensorTransformation() {
        TextTransformation censor = new CensorTransformation("hello");
        assertEquals("***** world", censor.transform("hello world"));
        assertEquals("***** world, *****!", censor.transform("Hello world, hello!"));
        assertEquals("*****", censor.transform("hello"));
        assertNull(null, censor.transform(null));
    }

    @Test
    public void testReplaceTransformation() {
        TextTransformation replace = new ReplaceTransformation("Hello", "Goodbye");
        assertEquals("Goodbye world", replace.transform("Hello world"));
        assertEquals("Goodbye world! Goodbye!", replace.transform("Hello world! Hello!"));
        assertEquals("hello world", replace.transform("hello world"));
        assertNull(null, replace.transform(null));
    }

    @Test
    public void testCompositeTransformation() {
        TextTransformation leftTrim = new LeftTrimTransformation();
        TextTransformation capitalize = new CapitalizeTransformation();
        TextTransformation replace = new ReplaceTransformation("Hello", "Goodbye");
        CompositeTransformation composite = new CompositeTransformation(Arrays.asList(leftTrim, capitalize));
        composite.addTransformation(replace);
        assertEquals("Goodbye world", ((TextTransformation) composite).transform("   hello world"));
        assertNull(null, composite.transform(null));
    }
}

