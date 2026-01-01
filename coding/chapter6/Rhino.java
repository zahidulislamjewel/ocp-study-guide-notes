class Animal {
    static {
        System.out.print("A");
    }
}

public class Rhino extends Animal {
    static {
        System.out.print("B");
    }
}

class RhinoTest {
    public static void main(String[] args) {
        System.out.print("C");
        new Rhino();
        new Rhino();
        new Rhino();
        System.out.println();
        // Output: CAB (A before B, because parent class initialization first)
        // And, and C is the first statement of main, initialization of Main class always first
    }
}
