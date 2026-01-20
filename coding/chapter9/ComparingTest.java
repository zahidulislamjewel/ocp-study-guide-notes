import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ComparingTest {
    record Duck(int id, String name, int weight) implements Comparable<Duck> {

        @Override
        public int compareTo(Duck other) {
            if (other == null) {
                throw new IllegalArgumentException("Duck instance cannot be null.");
            }
            return Integer.compare(this.id, other.id);
        }

    }

    record Rabbit (int id) {}

    public static void main(String[] args) {
        var ducks = new ArrayList<Duck>();
        ducks.add(new Duck(3, "Quack", 10));
        ducks.add(new Duck(1, "Puddles", 20));
        ducks.add(new Duck(4, "Pebble", 15));
        ducks.add(new Duck(2, "Alex", 25));
        ducks.add(new Duck(5, "Lee", 25));
        System.out.println(ducks);
        Collections.sort(ducks);
        System.out.println(ducks);
        System.out.println("=".repeat(100));

        Comparator<Duck> compareByName = new Comparator<Duck>() {

            @Override
            public int compare(Duck d1, Duck d2) {
                return d1.name.compareTo(d2.name);
            }
        };

        Comparator<Duck> compareByWeightAsc = Comparator.comparing(Duck::weight);
        Comparator<Duck> compareByWeightDesc = (d1, d2) -> Integer.compare(d2.weight(), d1.weight());
        Comparator<Duck> compareByWeightThenComparingNameDesc = Comparator.comparing(Duck::weight)
                .thenComparing(Duck::name)
                .thenComparingInt(Duck::id)
                .reversed();

        Collections.sort(ducks, compareByName);
        System.out.println(ducks);

        Collections.sort(ducks, compareByWeightAsc);
        System.out.println(ducks);

        Collections.sort(ducks, compareByWeightDesc);
        System.out.println(ducks);

        Collections.sort(ducks, compareByWeightThenComparingNameDesc);
        System.out.println(ducks);
        System.out.println("=".repeat(100));

        Comparator<Duck> compareNaturalOrderOfObject = Comparator.naturalOrder();
        Comparator<Duck> compareReverseOrderOfObject = Comparator.reverseOrder();

        Collections.sort(ducks, compareNaturalOrderOfObject);
        System.out.println(ducks);

        Collections.sort(ducks, compareReverseOrderOfObject);
        System.out.println(ducks);
        System.out.println("=".repeat(100));
    }
}
