class Primate {
    public boolean hasHair() {
        return true;
    }
}

interface HasTail {
    boolean isTailStriped();
}

public class Lemur extends Primate implements HasTail {
    public int age = 10;

    @Override
    public boolean isTailStriped() {
        return false;
    }
}

class LemurTest {
    public static void main(String[] args) {
        Lemur lemur = new Lemur();
        System.out.println("(Lemur) age: " + lemur.age);
        System.out.println("(Lemur) is tail stripped: " + lemur.isTailStriped());
        System.out.println("(Lemur) has hair: " + lemur.hasHair());
        System.out.println("=".repeat(100));

        HasTail hasTail = lemur; // Lemur can be assigned to HasTail reference
        // System.out.println("(HasTail) age: " + hasTail.age); // hasTail reference
        // cannot access lemur fields
        System.out.println("(HasTail) is tail stripped: " + hasTail.isTailStriped());
        // System.out.println("(HasTail) has hair: " + hasTail.hasHair()); // hasTail
        // reference cannot access lemur method
        System.out.println("=".repeat(100));

        Primate primate = lemur; // Lemur can be assigned to Primate reference
        // System.out.println("(Primate) age: " + primate.age); // primate reference
        // cannot access lemur fields
        // System.out.println("(Primate) is tail stripped: " + primate.isTailStriped());
        // // primate reference cannot access HasTail method
        System.out.println("(Primate) has hair: " + primate.hasHair());
        System.out.println("=".repeat(100));

        Object lemurObject = lemur; // // Lemur can be assigned to java.lang.Object reference
        System.out.println(lemurObject.hashCode()); // Only object methods are accessible
        System.out.println("=".repeat(100));

        System.out.println(lemur.age);
        System.out.println(lemur.isTailStriped());
        System.out.println(lemur.hasHair());
    }
}
