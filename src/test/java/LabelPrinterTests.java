import labels.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class LabelPrinterTests {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void simulateUserInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    private void assertSystemOutContains(String expected) {
        String output = testOut.toString();
        assertTrue(output.contains(expected));
    }

    @Test
    public void testPrintSimpleLabel() {
        Label simpleLabel = new SimpleLabel("Simple labels.Label");
        LabelPrinter.print(simpleLabel);
        assertSystemOutContains("Here is a label: Simple labels.Label");
    }

    @Test
    public void testPrintRichLabel() {
        Label richLabel = new RichLabel("Rich labels.Label", "Violet", 12, "Calibri");
        LabelPrinter.print(richLabel);
        assertSystemOutContains("Here is a label: Rich labels.Label");
    }

    @Test
    public void testPrintProxyLabel() {
        ProxyLabel proxyLabel = new ProxyLabel(3);
        simulateUserInput("Proxy labels.Label\n");
        LabelPrinter.print(proxyLabel);
        assertSystemOutContains("Here is a label: Proxy labels.Label");
    }

    @Test
    public void testPrintWithHelpText() {
        Label simpleLabel = new SimpleLabel("labels.Label with Help");
        HelpLabel helpLabel = new HelpLabel(simpleLabel, "Detailed help information");

        LabelPrinter.printWithHelpText(helpLabel);
        assertSystemOutContains("Here is a label: labels.Label with Help");
        assertSystemOutContains("Some help information about this label: Detailed help information");
    }

    @Test
    public void testPrintWithHelpTextForProxyLabel() {
        ProxyLabel proxyLabel = new ProxyLabel(2);

        simulateUserInput("Proxy labels.Label with Help\n");
        HelpLabel helpLabel = new HelpLabel(proxyLabel, "Help for proxy label");

        LabelPrinter.printWithHelpText(helpLabel);
        assertSystemOutContains("Here is a label: Proxy labels.Label with Help");
        assertSystemOutContains("Some help information about this label: Help for proxy label");
    }
}
