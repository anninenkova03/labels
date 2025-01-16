import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class ProxyLabel implements Label {
    private Label realLabel;
    private String cachedText = null;
    private final int timeout;
    private AtomicInteger accessCount = new AtomicInteger(0);

    public ProxyLabel() {
        this(6);
    }

    public ProxyLabel(int timeout) {
        this.timeout = timeout;
    }

    public int getAccessCount() {
        return accessCount.get();
    }

    @Override
    public String getText() {
        Scanner input = new Scanner(System.in);

        if (cachedText == null) {
            System.out.print("Enter label text: ");
            cachedText = input.nextLine();
            realLabel = new SimpleLabel(cachedText);
        }

        if (accessCount.incrementAndGet() > timeout) {
            System.out.println("Timeout reached. Do you want to update the text? (yes/no)");
            String response = input.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                System.out.println("Enter new label text:");
                cachedText = input.nextLine();
                realLabel = new SimpleLabel(cachedText);
            }
            accessCount.set(1);
        }

        return cachedText;

    }
}
