class Animal {
    static {
        System.out.print("A");
    }
}

public class Hippo extends Animal {
    static {
        System.out.print("B");
    }

    public static void main(String[] args) {
        System.out.print("C");
        new Hippo();
        new Hippo();
        new Hippo();
        System.out.println();
        // Output: ABC (super class static (once) > current class static (once) > current class non-static)
    }
}
