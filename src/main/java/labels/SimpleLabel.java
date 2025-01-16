package labels;

public class SimpleLabel implements Label {
    public String value;

    public SimpleLabel(String value) {
        this.value = value;
    }

    public String getText() {
        return value;
    }
}