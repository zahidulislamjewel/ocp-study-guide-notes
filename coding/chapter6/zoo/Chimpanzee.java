package zoo;

class Primate {
    public Primate() {
        // An implicit call to the super() of parent java.lang.Object happens
        System.out.print("Primate-");
    }
}

class Ape extends Primate {
    public Ape(int fur) {
        // An implicit call to the super() of parent Primate happens
        System.out.print("Ape1-");
    }

    public Ape() {
        // An implicit call to the super() of parent Primate happens
        System.out.print("Ape2-");
    }
}

public class Chimpanzee extends Ape {
    public Chimpanzee() {
        // explicit call to the super(int) of parent, so no implicit call to the super() of parent
        super(2); 
        System.out.println("Chimpanzee-");
    }

    public static void main(String[] args) {
        new Chimpanzee();
        // Output: Primate-Ape1-Chimpanzee-
    }
}
