import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SortingSearchingTest {
    static record Rabbit(int id) {}

    public static void main(String[] args) {
        List<Rabbit> rabbits = new ArrayList<>();
        rabbits.add(new Rabbit(4));
        rabbits.add(new Rabbit(3));
        rabbits.add(new Rabbit(1));
        rabbits.add(new Rabbit(5));
        rabbits.add(new Rabbit(2));

        Comparator<Rabbit> compareByIdAsc = Comparator.comparing(Rabbit::id);
        Comparator<Rabbit> compareByIdDesc = Comparator.comparing(Rabbit::id).reversed();

        System.out.println(rabbits);
        
        // Collections.sort(rabbits);  // DOES NOT COMPILE, if Rabbit doesn't implement Comparable
        // System.out.println(rabbits);
        
        Collections.sort(rabbits, compareByIdAsc);
        System.out.println(rabbits);

        Collections.sort(rabbits, compareByIdDesc);
        System.out.println(rabbits);
        
        Collections.reverse(rabbits);
        System.out.println(rabbits);
        System.out.println("=".repeat(100));

        Set<Rabbit> rabbitList = new TreeSet<>();
        // rabbitList.add(new Rabbit(10));     // throw ClassCastException, Rabbit is not Comparable

        Set<Rabbit> anotherRabbitList = new TreeSet<>((r1, r2) -> Integer.compare(r1.id, r2.id));
        anotherRabbitList.add(new Rabbit(10)); // Compiles fine, Comprator passed to the constructor
        System.out.println(anotherRabbitList);

        List<String> bunnies = new ArrayList<>();
        bunnies.add("long ear");
        bunnies.add("floppy");
        bunnies.add("hoppy");
        System.out.println(bunnies);

        // bunnies.sort((b1, b2) -> b1.compareTo(b2));
        bunnies.sort(String::compareTo);

        System.out.println(bunnies);
    }
}
