package pond.shore;

public class BirdWatcher {
    public void watchBird() {
        Bird bird = new Bird();
        bird.floatInWater();            // Accessing the protected method from the same package
        System.out.println(bird.text);  // Accessing the protected field from the same package
    }
}
