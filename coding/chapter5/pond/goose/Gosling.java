package pond.goose;

import pond.shore.Bird;

public class Gosling extends Bird {
    public void swim() {
        floatInWater();             // Accessing the protected method from the superclass
        System.out.println(text);   // Accessing the protected field from the superclass
    }

    public static void main(String[] args) {
        Gosling gosling = new Gosling();
        gosling.swim();
    }
}
