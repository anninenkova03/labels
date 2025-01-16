import labels.RichLabel;
import labels.SimpleLabel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LabelTests {

    @Test
    public void testSimpleLabelProperties() {
        SimpleLabel simpleLabel = new SimpleLabel("Test Simple labels.Label");
        assertEquals("Test Simple labels.Label", simpleLabel.getText());
    }

    @Test
    public void testRichLabelProperties() {
        RichLabel richLabel = new RichLabel("Rich Text", "Blue", 12, "Verdana");
        assertEquals("Rich Text", richLabel.getText());
        assertEquals("Blue", richLabel.getColor());
        assertEquals(12, richLabel.getFontSize());
        assertEquals("Verdana", richLabel.getFontName());
    }
}
