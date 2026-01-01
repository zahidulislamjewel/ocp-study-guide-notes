package pond.swan;

import pond.duck.MotherDuck;

public class BadCygnet {
    public void makeNoise() {
        var motherDuck = new MotherDuck();
        // Cannot access package-private members of MotherDuck from a different package
        // motherDuck.quack(); // Compilation error
        // System.out.println(motherDuck.noise); // Compilation error
    }
}
