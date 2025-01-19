import labels.Label;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Label Creator!");
        LabelInjector labelInjector = new LabelInjector();
        Label label = labelInjector.createLabel();
        LabelPrinter.print(label);
    }
}
