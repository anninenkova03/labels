import labels.Label;
import labels.LabelPrinter;
import transformations.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Label Creator!");

        Scanner scanner = new Scanner(System.in);
        List<TextTransformation> allTransformations = List.of(
                new CapitalizeTransformation(),
                new LeftTrimTransformation(),
                new RightTrimTransformation(),
                new NormalizeSpaceTransformation(),
                new DecorateTransformation(),
                new CensorTransformation("bad"),
                new ReplaceTransformation("bad", "good")
        );

        LabelCreationService labelCreationService = new LabelCreationService(scanner, allTransformations);
        Label label = labelCreationService.createLabel();
        LabelPrinter.print(label);
    }
}
