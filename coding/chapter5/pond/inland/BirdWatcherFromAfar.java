package pond.inland;

import pond.shore.Bird;

public class BirdWatcherFromAfar {
    public void watchBird() {
        // Cannot access protected members from a different package without inheritance
        Bird bird = new Bird();
        // bird.floatInWater();            // Not accessible
        // System.out.println(bird.text);  // Not accessible
    }
}
