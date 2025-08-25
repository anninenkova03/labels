# Advanced Label Customization System: A Design Patterns Project

This document provides a detailed overview of the "Labels" Java project, created as part of a software design patterns course. It details the project's structure, core features, and the key design patterns that form its architectural foundation.

---

## 1. Project Overview

The Advanced Label Customization System is a command-line application that allows for the creation and dynamic manipulation of text labels. The application is designed to be highly flexible and extensible, allowing new label types, transformations, and behaviors to be added with minimal changes to the existing codebase, demonstrating a robust, pattern-driven architecture.

### Core Features
* **Create various label types**:
    * `SimpleLabel` with basic text.
    * `RichLabel` with properties like color and font.
    * `ProxyLabel` with lazy initialization and caching.
* **Apply a wide range of text transformations**:
  * Capitalization, censoring, trimming, replacing text, and more.
* **Dynamically decorate labels**:
    * Chain multiple transformations together.
    * Apply transformations in different modes (e.g., cycling through a list or picking one randomly).
* **Add contextual help text** to any label type without altering its core class.

---

## 2. Project Structure

The project is organized into logical packages to separate concerns:

* `labels`: Contains the `Label` interface and its concrete implementations (`SimpleLabel`, `RichLabel`, `ProxyLabel`). This package defines the "products" of the application.
* `transformations`: Contains the `TextTransformation` strategy interface and all concrete transformation algorithms (`CapitalizeTransformation`, `CensorTransformation`, etc.).
* `decorators`: Contains all classes related to the Decorator pattern, which are used to wrap `Label` objects and modify their behavior.
* `main`: The entry point of the application (`Main.java`) and the `LabelCreationService` class, which handles user interaction and object construction.
* `test/java`: Contains a comprehensive suite of JUnit tests for all major components, ensuring correctness and robustness.



---

## 3. Design Patterns in Use

The project's architecture heavily relies on several fundamental design patterns to achieve its goals of flexibility, decoupling, and maintainability.

### Decorator

The **Decorator** pattern adds new behaviors to objects dynamically by placing them inside special wrapper objects. It is a flexible alternative to subclassing for extending functionality.

* **Implementation**: `Label` is the component interface. `LabelDecoratorBase` is an abstract decorator that also implements `Label`. Concrete decorators like `TextTransformationDecorator`, `RandomTransformationDecorator`, and `CyclingTransformationsDecorator` wrap a `Label` object and augment its `getText()` method.

    ```java
    // In decorators/TextTransformationDecorator.java
    public class TextTransformationDecorator extends LabelDecoratorBase {
        private final TextTransformation transformation;

        public TextTransformationDecorator(Label label, TextTransformation transformation) {
            super(label);
            this.transformation = transformation;
        }

        @Override
        public String getText() {
            // 1. Get text from the wrapped component
            String originalText = label.getText();
            // 2. Modify (decorate) the result
            return transformation.transform(originalText);
        }
    }
    ```

* **Purpose**: This pattern allows transformations to be "stacked" on any `Label` object at runtime. This avoids creating a massive hierarchy of subclasses for every possible combination of transformations (e.g., `CapitalizedCensoredLabel`, `TrimmedCapitalizedLabel`), adhering to the **Open/Closed Principle**.

### Strategy

The **Strategy** pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable, letting the algorithm vary independently of the clients that use it.

* **Implementation**: The `TextTransformation` interface is the `Strategy` interface, defining the abstract `transform()` method. Concrete classes like `CapitalizeTransformation`, `CensorTransformation`, and `NormalizeSpaceTransformation` are `Concrete Strategies`. The `TextTransformationDecorator` acts as the `Context`, holding a reference to a `TextTransformation` strategy and using it to perform its work.

    ```java
    // The "Strategy" interface
    public interface TextTransformation {
        String transform(String text);
    }

    // A "Concrete Strategy"
    public class CapitalizeTransformation implements TextTransformation {
        @Override
        public String transform(String text) {
            // ... logic to capitalize the text ...
        }
    }
    ```

* **Purpose**: This pattern **decouples the transformation algorithms** from the decorators. It allows new transformations to be added to the system without modifying any of the existing decorator classes. The decorator only needs to know about the `TextTransformation` interface, not the specifics of each algorithm.

### Composite

The **Composite** pattern composes objects into tree structures to represent part-whole hierarchies, allowing clients to treat individual objects and compositions of objects uniformly.

* **Implementation**: The `CompositeTransformation` class implements the `TextTransformation` interface, allowing it to be treated just like any other transformation strategy. However, it also maintains a `List` of other `TextTransformation` objects. Its `transform()` method iterates over this list, applying each transformation in sequence.

    ```java
    // In transformations/CompositeTransformation.java
    public class CompositeTransformation implements TextTransformation {
        private final List<TextTransformation> transformations = new ArrayList<>();

        // ... constructor and addTransformation method ...

        @Override
        public String transform(String text) {
            for (TextTransformation transformation : transformations) {
                text = transformation.transform(text);
            }
            return text;
        }
    }
    ```

* **Purpose**: This pattern allows the client to build a complex sequence of transformations and treat it as a single unit. It simplifies the client code, as it doesn't need to distinguish between applying one transformation or a list of them.

### Proxy

The **Proxy** pattern provides a surrogate or placeholder for another object to control access to it.

* **Implementation**: The `ProxyLabel` class implements the `Label` interface, acting as a stand-in for a `SimpleLabel` that it creates and manages internally. It adds several behaviors:
    1.  **Lazy Initialization**: The real `SimpleLabel` is only created when `getText()` is called for the first time.
    2.  **Caching**: The label text is cached after the first retrieval.
    3.  **Access Control**: It counts access attempts and prompts the user to update the text after a "timeout" threshold is reached.

    ```java
    // In labels/ProxyLabel.java
    public class ProxyLabel implements Label {
        private Label realLabel;
        private String cachedText = null;
        private final int timeout;
        // ...
        @Override
        public String getText() {
            if (cachedText == null) {
                // Lazy initialization: get text from user and create realLabel
            }
            if (accessCount.incrementAndGet() > timeout) {
                // Access control: prompt user to update
            }
            return cachedText;
        }
    }
    ```

* **Purpose**: This pattern allows us to add extra layers of logic and lifecycle management to an object without the client being aware of it. The client interacts with `ProxyLabel` just as it would with any other `Label`.

### Bridge
The **Bridge** pattern decouples an abstraction from its implementation so that the two can vary independently.

* **Implementation**: The `HelpLabel` class is a perfect example. While it wraps a `Label` like a decorator, its primary role is to bridge two separate concepts:
    * **The Abstraction**: The concept of a label that has help text.
    * **The Implementation**: The core `Label` functionality (`getText()`), provided by any concrete `Label` object (`SimpleLabel`, `RichLabel`, etc.).
    ```java
    // In labels/HelpLabel.java
    public class HelpLabel implements Label, HelpText {
        private final Label label; // The "Implementation" reference
        private final String helpText;

        public HelpLabel(Label label, String helpText) {
            this.label = label;
            this.helpText = helpText;
        }
        // ...
    }
    ```
* **Purpose**: This pattern separates the "help text" feature from the core "label" feature. You can create new kinds of help labels or new kinds of core labels, and they can all work together without being tightly coupled.

### Flyweight

The **Flyweight** pattern is used to minimize memory usage by sharing as much data as possible with other similar objects.

* **Implementation**: The `CensorTransformationFactory` acts as a factory for `CensorTransformation` objects. It maintains a cache (`Map`) of shared instances. For short, common words (length <= 4), it reuses a single `CensorTransformation` object from the cache. For longer, more unique words, it creates a new, unshared object.

    ```java
    // In transformations/CensorTransformationFactory.java
    public class CensorTransformationFactory {
        private final Map<String, TextTransformation> flyweightCache = new HashMap<>();

        public TextTransformation getTextTransformation(String W) {
            if (W.length() <= 4) {
                // Return a shared instance from the cache
                return flyweightCache.computeIfAbsent(W, CensorTransformation::new);
            } else {
                // Return a new, unshared instance
                return new CensorTransformation(W);
            }
        }
    }
    ```

* **Purpose**: This pattern significantly reduces the memory footprint of the application by avoiding the creation of thousands of identical objects for censoring common words, thereby improving performance and resource efficiency.

### Dependency Injection
The **Dependency Injection Principle** is a design principle used to achieve **Inversion of Control**. Instead of a class creating its own dependencies, the dependencies are supplied ("injected") from an external source.

* **Implementation**: The `LabelCreationService` class doesn't create its dependencies (like the `Scanner`). Instead, they are passed into its constructor. The `Main` class acts as the **Composition Root**—the single place where dependencies are created and "wired" together.

    ```java
    // In Main.java - The Composition Root
    public class Main {
        public static void main(String[] args) {
            // 1. Dependencies are created here
            Scanner scanner = new Scanner(System.in);
            List<TextTransformation> allTransformations = List.of( /* ... */ );
    
            // 2. Dependencies are "injected" into the LabelCreationService
            LabelCreationService labelCreationService = new LabelCreationService(scanner, allTransformations);
            
            // 3. The LabelCreationService uses the dependencies it was given
            Label label = labelCreationService.createLabel();
            LabelPrinter.print(label);
        }
    }
    ```

* **Purpose**: This design **decouples** the `LabelCreationService` from its concrete dependencies. `LabelCreationService` is no longer responsible for *how* to create a `Scanner` or a `List` of transformations; it only knows that it needs them. This makes the class much easier to test (you can inject a mock `Scanner`) and more flexible to changes in the future.

## 4. References

[Decorator Pattern – Wikipedia](https://en.wikipedia.org/wiki/Decorator_pattern)  
[Strategy Pattern – Wikipedia](https://en.wikipedia.org/wiki/Strategy_pattern)  
[Composite Pattern – Wikipedia](https://en.wikipedia.org/wiki/Composite_pattern)  
[Proxy Pattern – Wikipedia](https://en.wikipedia.org/wiki/Proxy_pattern)  
[Bridge Pattern – Wikipedia](https://en.wikipedia.org/wiki/Bridge_pattern)  
[Flyweight Pattern – Wikipedia](https://en.wikipedia.org/wiki/Flyweight_pattern)  
[Dependency Injection – Wikipedia](https://en.wikipedia.org/wiki/Dependency_injection)  