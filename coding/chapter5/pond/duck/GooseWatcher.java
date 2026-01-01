package pond.duck;

import pond.goose.Goose;

public class GooseWatcher {
    public void watch() {
        Goose goose = new Goose();
        goose.helpGooseSwim();                  // Accessible: Goose's public method
        // goose.floatInWater();                // Not accessible: protected method not accessible through Goose reference
        // System.out.println(goose.text);      // Not accessible: protected field not accessible through Goose
    }
}
