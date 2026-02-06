import java.util.ArrayList;
import java.util.Collections;

public class OrderingTest {

    record Duck(String name) implements Comparable<Duck> {
        @Override
        public int compareTo(Duck other) {
            return this.name.compareTo(other.name);
        }
    }

    record ZooDuck(int id, String name) implements Comparable<ZooDuck> {
        @Override
        public int compareTo(ZooDuck other) {
            // return this.id - other.id; // for sorting in ascending order
            // return other.id - this.id; // for sorting in descending order
            return Integer.compare(this.id, other.id); // for sorting in ascending order
        }
    }

    record LeagacyDuck(String name) implements Comparable {

        @Override
        public int compareTo(Object obj) {
            if (obj instanceof LeagacyDuck ld) {
                return this.name.compareTo(ld.name);
            }
            throw new UnsupportedOperationException("Not an instance of LegacyDuck");
        }

    }

    record MissingDuck(String name) implements Comparable<MissingDuck> {

        @Override
        public int compareTo(MissingDuck quack) {
            if(quack == null) {
                throw new IllegalArgumentException("Poorly formed duck!");
            }

            if(this.name == null && quack.name == null) {
                return 0;
            } else if(this.name == null) {
                return -1;
            } else if(quack.name == null) {
                return 1;
            } else {
                return this.name.compareTo(quack.name);
            }
        }

    }

    public static void main(String[] args) {
        var ducks = new ArrayList<Duck>();
        ducks.add(new Duck("Quack"));
        ducks.add(new Duck("Puddles"));
        ducks.add(new Duck("Pebble"));
        ducks.add(new Duck("Alex"));
        System.out.println(ducks);
        Collections.sort(ducks);
        System.out.println(ducks);
        System.out.println("=".repeat(100));

        var zooDucks = new ArrayList<ZooDuck>();
        zooDucks.add(new ZooDuck(2, "Donald"));
        zooDucks.add(new ZooDuck(4, "Alex"));
        zooDucks.add(new ZooDuck(3, "Daffy"));
        zooDucks.add(new ZooDuck(1, "Pug"));
        System.out.println(zooDucks);
        Collections.sort(zooDucks);
        System.out.println(zooDucks);
        System.out.println("=".repeat(100));

        var legacyDucks = new ArrayList<LeagacyDuck>();
        legacyDucks.add(new LeagacyDuck("Quack"));
        legacyDucks.add(new LeagacyDuck("Puddles"));
        legacyDucks.add(new LeagacyDuck("Pebble"));
        legacyDucks.add(new LeagacyDuck("Alex"));
        System.out.println(legacyDucks);
        Collections.sort(legacyDucks);
        System.out.println(legacyDucks);

    }
}
