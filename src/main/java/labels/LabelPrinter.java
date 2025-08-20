package labels;

public class LabelPrinter {
    public static void print(Label label) {
        System.out.println("Here is a label: " + label.getText());
    }

    public static void printWithHelpText(HelpLabel label) {
        print(label);
        System.out.println("Some help information about this label: " + label.getHelpText());
    }
}
