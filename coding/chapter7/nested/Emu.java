class Emu1 {
    String name = "Emmy";

    static Feathers createFeathers(){
        return new Feathers("grey");  // Compiles, static method can access static member record (implict)
    }

    record Feathers(String color) {
        void fly() {
            // System.out.println(name + " is flying"); // DOES NOT COMPILE
        }
    }
}

class Emu2 {
    String name = "Emmy";

    static Feathers createFeathers(){
        // No enclosing instance of type Emu2 is accessible. 
        // Must qualify the allocation with an enclosing instance of type Emu2 (e.g. x.new A() where x is an instance of Emu2).
        // return new Feathers();  // DOES NOT COMPILE, static method cannot access no-static inner class directly

        return new Emu2().new Feathers();  // Compiles
    }

    class Feathers {
        void fly() {
            System.out.println(name + " is flying"); // Compiles, can access instance variable
        }
    }
}

public class Emu {
    public static void main(String[] args) {
        Emu1.Feathers emu1Feathers = new Emu1.Feathers("Black");
        emu1Feathers.fly();
        System.out.println("=".repeat(100));

        Emu2.Feathers emu2Feathers = new Emu2().new Feathers();
        emu2Feathers.fly();
    }
}
