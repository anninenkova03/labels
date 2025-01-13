import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LabelTests {

    @Test
    public void testSimpleLabelProperties() {
        SimpleLabel simpleLabel = new SimpleLabel("Test Simple Label");
        assertEquals("Test Simple Label", simpleLabel.getText());
    }

    @Test
    public void testRichLabelProperties() {
        RichLabel richLabel = new RichLabel("Rich Text", "Blue", 12, "Verdana");
        assertEquals("Rich Text", richLabel.getText());
        assertEquals("Blue", richLabel.getColor());
        assertEquals(12, richLabel.getFontSize());
        assertEquals("Verdana", richLabel.getFontName());
    }

    @Test
    public void testLabelPrinterWithSimpleLabel() {
        SimpleLabel simpleLabel = new SimpleLabel("Printer Simple Test");
        LabelPrinter.print(simpleLabel);
        assertEquals("Printer Simple Test", simpleLabel.getText());
    }

    @Test
    public void testLabelPrinterWithRichLabel() {
        RichLabel richLabel = new RichLabel("Printer Rich Test", "Green", 14, "Courier New");
        LabelPrinter.print(richLabel);
        assertEquals("Printer Rich Test", richLabel.getText());
    }
}
