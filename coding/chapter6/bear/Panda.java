package bear;

class Bear {
    public static void eat() {
        System.out.println("Bear is eating");
    }
}

public class Panda extends Bear {
    public static void eat() {
        System.out.println("Panda is chewing");
    }

    public static void main(String[] args) {
        eat(); // This calls the eat() method of Bear due to method hiding
    }
}

class PandaTest {
    public static void main(String[] args) {
        Bear.eat(); // from separate class, explict call required
        Panda.eat();  // from separate class, explict call required
    }
}