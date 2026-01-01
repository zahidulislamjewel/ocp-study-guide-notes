package canine;

public abstract class Canine {
    public abstract String getSound();

    public void bark() {
        System.out.println(getSound());
    }
}

class Wolf extends Canine {

    @Override
    public String getSound() {
        return "Wooooooof!";
    }
}

class Fox extends Canine {

    @Override
    public String getSound() {
        return "Squeak!";
    }
}

class Coyote extends Canine {

    @Override
    public String getSound() {
        return "Roar!";
    }
}

class CanineTest {
    public static void main(String[] args) {
        Canine fox = new Fox();
        fox.bark();  // Called bark() calls the getSound() concreted method implemented in class
    }
}
