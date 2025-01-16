public class HelpLabel implements Label, HelpText {
    private final Label label;
    private final String helpText;

    public HelpLabel(Label label, String helpText) {
        this.label = label;
        this.helpText = helpText;
    }

    @Override
    public String getText() {
        return label.getText();
    }

    @Override
    public String getHelpText() {
        return helpText;
    }
}
