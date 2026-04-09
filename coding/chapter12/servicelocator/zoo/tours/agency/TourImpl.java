// TourImpl.java
package zoo.tours.agency;

import zoo.tours.api.Souvenir;
import zoo.tours.api.Tour;

public class TourImpl implements Tour {

    public String name() {
        return "Behind the Scenes";
    }

    public int length() {
        return 120;
    }

    public Souvenir getSouvenir() {
        return new Souvenir("stuffed animal");
    }
}