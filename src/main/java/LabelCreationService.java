import labels.*;
import decorators.*;
import transformations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class LabelCreationService {
    private final Scanner input;
    private final List<TextTransformation> allTransformations;

    public LabelCreationService(Scanner input, List<TextTransformation> allTransformations) {
        this.input = input;
        this.allTransformations = allTransformations;
    }

    public Label createLabel() {
        Label label;

        System.out.println("""
                Choose label type:
                1) Simple Label
                2) Rich Text
                3) Custom Label""");
        int type = input.nextInt();
        input.nextLine();

        if (type == 1) {
            System.out.println("Enter label text:");
            String text = input.nextLine();
            label = new SimpleLabel(text);
        } else if (type == 2) {
            System.out.println("Enter label text:");
            String text = input.nextLine();
            System.out.println("Enter text color:");
            String color = input.nextLine();
            System.out.println("Enter font name:");
            String fontName = input.nextLine();
            System.out.println("Enter font size:");
            int fontSize = input.nextInt();
            input.nextLine();
            label = new RichLabel(text, color, fontSize, fontName);
        } else if (type == 3) {
            label = new ProxyLabel();
            label.getText();
        } else {
            System.out.println("Invalid choice. Please select 1, 2, or 3.");
            return null;
        }

        System.out.println("Would you like to add help text? (yes/no)");
        String helpResponse = input.nextLine();
        if (helpResponse.equalsIgnoreCase("yes")) {
            System.out.println("Enter help text:");
            String helpText = input.nextLine();
            label = new HelpLabel(label, helpText);
        }

        System.out.println("""
                Write a space-separated list of the transformations you'd like to apply. Press enter to finish.
                1) Capitalize
                2) Trim left
                3) Trim right
                4) Normalize space
                5) Decorate
                6) Censor
                7) Replace
                8) Random
                9) None""");

        String userChoice = input.nextLine();
        String[] userChoiceSplit = userChoice.split(" ");
        List<Integer> transformations = new ArrayList<>();
        for (String choice : userChoiceSplit) {
            try {
                transformations.add(Integer.parseInt(choice));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Transformations should be selected by their numbers.");
            }
        }

        List<List<Integer>> transformationsInChunks = splitTransformations(transformations);

        for (List<Integer> chunk : transformationsInChunks) {
            if (chunk.size() == 1) {
                int transformationType = chunk.getFirst();
                if (transformationType == 8) {
                    label = new RandomTransformationDecorator(label, allTransformations);
                } else {
                    label = new TextTransformationDecorator(label, getTransformation(chunk));
                }
            } else {
                label = new TextTransformationDecorator(label, getTransformation(chunk));
            }
        }

        return label;
    }

    private TextTransformation getTransformation(List<Integer> chunk) {
        CompositeTransformation composite = new CompositeTransformation(new ArrayList<>());
        for (int transform : chunk) {
            switch (transform) {
                case 1 -> composite.addTransformation(new CapitalizeTransformation());
                case 2 -> composite.addTransformation(new LeftTrimTransformation());
                case 3 -> composite.addTransformation(new RightTrimTransformation());
                case 4 -> composite.addTransformation(new NormalizeSpaceTransformation());
                case 5 -> composite.addTransformation(new DecorateTransformation());
                case 6 -> {
                    System.out.print("Enter word to censor: ");
                    String censorWord = input.nextLine();
                    composite.addTransformation(new CensorTransformation(censorWord));
                }
                case 7 -> {
                    System.out.print("Enter word to replace: ");
                    String replace = input.nextLine();
                    System.out.print("Enter replacement: ");
                    String replacement = input.nextLine();
                    composite.addTransformation(new ReplaceTransformation(replace, replacement));
                }
                case 9 -> {}
                default -> System.out.println("Invalid transformation number. Skipping: " + transform);
            }
        }
        return composite;
    }

    private List<List<Integer>> splitTransformations(List<Integer> transformations) {
        List<List<Integer>> chunks = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        for (int num : transformations) {
            if (num == 8 || num == 9) {
                if (!current.isEmpty()) {
                    chunks.add(new ArrayList<>(current));
                    current.clear();
                }
                chunks.add(List.of(num));
            } else {
                current.add(num);
            }
        }
        if (!current.isEmpty()) {
            chunks.add(current);
        }
        return chunks;
    }
}