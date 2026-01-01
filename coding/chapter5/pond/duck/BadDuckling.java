package pond.duck;

public class BadDuckling {
    public void makeNoise() {
        var fatherDuck = new FatherDuck();
        // Cannot access private members of FatherDuck
        // fatherDuck.quack(); // Compilation error
        // System.out.println(fatherDuck.noise); // Compilation error
    }
}
