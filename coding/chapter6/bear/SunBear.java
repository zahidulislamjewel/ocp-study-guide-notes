package bear;

class Bear {
    public static void sneeze() { System.out.println("Bear is sneezing"); }

    public void hibernate() { System.out.println("Bear is hibernating"); }

    public static void laugh() { System.out.println("Bear is laughing"); }

    public static void eat() { System.out.println("Bear is eating"); }
}

public class SunBear extends Bear {
    // Doesn't compile. This instance method cannot override the static method from Bear
    // public void sneeze() { System.out.println("Sun Bear is sneezing"); }

    // Doesn't compile. This static method cannot hide the instance method from Bear
    // public static void hibernate() { System.out.println("Sun Bear is hibernating"); }

    // Doesn't compile. Cannot reduce the visibility of the inherited method from Bear
    // protected static void laugh() { System.out.println("Sun Bear is laughing"); }

    public static void eat() { System.out.println("Sun Bear is eating"); }

    public static void main(String[] args) {
        // SunBear class static method eat() hides Bear class static method eat()
        eat();  
    }
}
