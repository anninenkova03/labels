import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.Assert.*;

public class HelpLabelTests {

    private void simulateUserInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    public void testSimpleLabelWithHelpText() {
        Label simpleLabel = new SimpleLabel("Simple Label");
        HelpLabel helpLabel = new HelpLabel(simpleLabel, "Help text for Simple Label");

        assertEquals("Simple Label", helpLabel.getText());
        assertEquals("Help text for Simple Label", helpLabel.getHelpText());
    }

    @Test
    public void testRichLabelWithHelpText() {
        Label richLabel = new RichLabel("Rich Label", "Blue", 12, "Verdana");
        HelpLabel helpLabel = new HelpLabel(richLabel, "Help text for Rich Label");

        assertEquals("Rich Label", helpLabel.getText());
        assertEquals("Help text for Rich Label", helpLabel.getHelpText());
    }

    @Test
    public void testProxyLabelWithHelpText() {
        ProxyLabel proxyLabel = new ProxyLabel(3);

        simulateUserInput("Proxy Label\n");
        HelpLabel helpLabel = new HelpLabel(proxyLabel, "Help text for Proxy Label");

        assertEquals("Proxy Label", helpLabel.getText());
        assertEquals("Help text for Proxy Label", helpLabel.getHelpText());
    }
}

