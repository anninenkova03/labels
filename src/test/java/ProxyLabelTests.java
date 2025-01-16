import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;

public class ProxyLabelTests {

    private void simulateUserInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    public void testInitialTextReadFromInput() {
        simulateUserInput("Initial text\n");

        ProxyLabel proxyLabel = new ProxyLabel(3);
        String text = proxyLabel.getText();

        assertEquals("Initial text", text);
    }

    @Test
    public void testCachedTextReturned() {
        simulateUserInput("Cached text\n");

        ProxyLabel proxyLabel = new ProxyLabel(3);
        proxyLabel.getText();
        String text = proxyLabel.getText();

        assertEquals("Cached text", text);
    }

    @Test
    public void testPromptForTextUpdateAfterTimeout() {
        ProxyLabel proxyLabel = new ProxyLabel(2);

        simulateUserInput("Old text\n");
        assertEquals("Old text", proxyLabel.getText());
        assertEquals("Old text", proxyLabel.getText());

        simulateUserInput("yes\nNew text\n");
        assertEquals("New text", proxyLabel.getText());
    }

    @Test
    public void testNoTextUpdateAfterTimeoutWhenUserDeclines() {
        ProxyLabel proxyLabel = new ProxyLabel(2);

        simulateUserInput("Original text\n");
        assertEquals("Original text", proxyLabel.getText());
        assertEquals("Original text", proxyLabel.getText());

        simulateUserInput("no\n");
        assertEquals("Original text", proxyLabel.getText());
    }

    @Test
    public void testTimeoutResetsAfterUpdate() {
        ProxyLabel proxyLabel = new ProxyLabel(2);

        simulateUserInput("First text\n");
        assertEquals("First text", proxyLabel.getText());
        assertEquals("First text", proxyLabel.getText());

        simulateUserInput("yes\nUpdated text\n");
        assertEquals("Updated text", proxyLabel.getText());
        assertEquals("Updated text", proxyLabel.getText());

        simulateUserInput("no\n");
        assertEquals("Updated text", proxyLabel.getText());
        assertEquals("Updated text", proxyLabel.getText());
    }
}
