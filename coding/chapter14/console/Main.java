import java.io.Console;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();

        if (console == null) {
            System.out.println("No console available");
        } else {
            String name = console.readLine("Enter your name: ");
            System.out.println("Hello, " + name);
        }
    }
}