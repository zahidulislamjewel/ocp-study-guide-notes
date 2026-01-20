package pond.duck;

public class GoodDuckling {
    public void makeNoise() {
        var motherDuck = new MotherDuck();
        // Can access package-private members of MotherDuck
        motherDuck.quack(); // Works fine
        System.out.println(motherDuck.noise); // Works fine
    }
}
