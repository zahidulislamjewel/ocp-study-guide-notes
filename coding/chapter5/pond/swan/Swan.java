package pond.swan;

import pond.shore.Bird;

public class Swan extends Bird {
    public void swim() {
        floatInWater();             // Accessing the protected method from the superclass
        System.out.println(text);   // Accessing the protected field from the superclass
    }

    public void helpOtherSwanSwim() {
        Swan otherSwan = new Swan();
        otherSwan.floatInWater();           // subclass access to superclass protected method through subclass instance
        System.out.println(otherSwan.text); // subclass access to superclass protected field through subclass instance
    }

    public void helpOtherBirdSwim() {
        Bird otherBird = new Bird();
        // otherBird.floatInWater();            // Not accessible: subclass cannot access superclass protected method through superclass instance
        // System.out.println(otherBird.text);  // Not accessible: subclass cannot access superclass protected field through superclass instance
    }
}
