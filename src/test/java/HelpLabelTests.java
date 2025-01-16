import labels.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class HelpLabelTests {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @Before
    public void setUp() {
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void simulateUserInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    public void testSimpleLabelWithHelpText() {
        Label simpleLabel = new SimpleLabel("Simple labels.Label");
        HelpLabel helpLabel = new HelpLabel(simpleLabel, "Help text for Simple labels.Label");

        assertEquals("Simple labels.Label", helpLabel.getText());
        assertEquals("Help text for Simple labels.Label", helpLabel.getHelpText());
    }

    @Test
    public void testRichLabelWithHelpText() {
        Label richLabel = new RichLabel("Rich labels.Label", "Blue", 12, "Verdana");
        HelpLabel helpLabel = new HelpLabel(richLabel, "Help text for Rich labels.Label");

        assertEquals("Rich labels.Label", helpLabel.getText());
        assertEquals("Help text for Rich labels.Label", helpLabel.getHelpText());
    }

    @Test
    public void testProxyLabelWithHelpText() {
        ProxyLabel proxyLabel = new ProxyLabel(3);

        simulateUserInput("Proxy labels.Label\n");
        HelpLabel helpLabel = new HelpLabel(proxyLabel, "Help text for Proxy labels.Label");

        assertEquals("Proxy labels.Label", helpLabel.getText());
        assertEquals("Help text for Proxy labels.Label", helpLabel.getHelpText());
    }
}

