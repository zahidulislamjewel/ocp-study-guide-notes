package fish;

public class Fish {
    public void swim() {
    }
}

class Shark extends Fish {
    public void swim(int speed) {} // Compiles, compiler sees this as overloading, no overriding

    // @Override
    // public void swim(int speed) {}; // Does no compile, compiler sees this as overriding
}
