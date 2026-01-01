class Insect {
    protected int numberOfLegs = 4;
    String label = "buggy";
}

public class Beetle extends Insect {
    protected int numberOfLegs = 6;
    short age = 3;

    public void printData() {
        System.out.println("this.label: " + this.label);          // inherited label, "buggy" (implicit call, this class doesn't have label)
        System.out.println("super.label:  " + super.label);       // inherited label, "buggy" (explicit call)
        System.out.println("label: " + label);                   // inherited label, "buggy" (implicit call)
        System.out.println("this.age " + this.age);             // 3
        // System.out.println(super.age);                       // compile error! age not in parent
        System.out.println("numberOfLegs: " + numberOfLegs);    // 6 (implicit this.numberOfLegs)
        System.out.println("this.numberOfLegs: " + this.numberOfLegs);      // 6 (this.numberOfLegs)
        System.out.println("super.numberOfLegs: " + super.numberOfLegs);    // 4 (parent field)
    }
}

class BeetleTest {
    public static void main(String[] args) {
        var beetle = new Beetle();
        beetle.printData();
    }
}