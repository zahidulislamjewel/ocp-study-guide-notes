package pond.goose;

import pond.shore.Bird;

public class Goose extends Bird {
    public void helpGooseSwim() {
        floatInWater();             // Accessing the protected method from the superclass, from another package
        System.out.println(text);   // Accessing the protected field from the superclass, from another package
    }

    public void helpOtherGooseSwim() {
        Bird otherBird = new Goose();
        // otherBird.floatInWater();           // Not accessible: subclass cannot access superclass protected method through superclass reference 
        // System.out.println(otherBird.text); // Not accessible: subclass cannot access superclass protected field through superclass reference
    }
}
