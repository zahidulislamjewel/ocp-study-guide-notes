import java.util.function.Consumer;

interface LearnToSpeak {
    void speak(String sound);
}

class DuckHelper {
    public static void teacher(String name, LearnToSpeak learner) {
        learner.speak(name);
    }
}

class Ducking {
    public static void makesSound(String soud) {
        // LearnToSpeak learner = s -> System.out.println(s);
        LearnToSpeak learner = System.out::println;
        DuckHelper.teacher(soud, learner);
        System.out.println("=".repeat(100));

        Consumer<String> stringPrinter = s -> System.out.println(s);
        stringPrinter.accept("Quack! Quack!");

        Consumer<Integer> integerPrinter = System.out::println;
        integerPrinter.accept(Integer.valueOf(443));
    }
}

class DucklingTest {
    public static void main(String[] args) {
        Ducking.makesSound("quack! quack!");
    }
}