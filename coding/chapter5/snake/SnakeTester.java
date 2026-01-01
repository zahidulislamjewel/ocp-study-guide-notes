package snake;

public class SnakeTester {
    public static void main(String[] args) {
        System.out.println("Snake hiss value (static access): " + Snake.hiss);

        Snake s = new Snake();
        // Warning: The static field Snake.hiss should be accessed in a static way
        System.out.println("Snake hiss value (instance / reference access): " + s.hiss); // Accessing static field via instance reference

        s = null; // Nullifying the reference
        
        System.out.println("Snake hiss value after nullifying reference (instance / reference access): " + s.hiss);
    }
}
