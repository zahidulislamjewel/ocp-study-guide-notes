import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamPipelineTest {
    public static void main(String[] args) {
        var list = List.of("Toby", "Anna", "Leroy", "Hannah", "Alex", "Bryn");

        // fetch frist two names from the list alphabetically sorted, and the names are
        // four characters long
        // Using traditional for-each iteration
        List<String> filtered = new ArrayList<>();
        for (String name : list) {
            if (name.length() == 4) {
                filtered.add(name);
            }
        }
        Collections.sort(filtered);
        var iter = filtered.iterator();
        if (iter.hasNext()) {
            System.out.println(iter.next());
        }
        if (iter.hasNext()) {
            System.out.println(iter.next());
        }

        System.out.println("=".repeat(100));

        // Using stream api
        list.stream()
                .filter(name -> name.length() == 4)
                .sorted()
                .limit(2)
                .forEach(System.out::println);

        System.out.println("=".repeat(100));

        // This one hangs as well until we kill the program.
        // java.lang.OutOfMemoryError
        // Stream.generate(() -> "Elsa")
        // .filter(n -> n.length() != 4)
        // .limit(2)
        // .sorted()
        // .forEach(System.out::println);

        // It hangs until you kill the program, or it throws an exception after running
        // out of memory.
        // Stream.generate(() -> "Elsa")
        // .filter(n -> n.length() == 4)
        // .sorted()
        // .limit(2)
        // .forEach(System.out::println);

        // Compiles
        Stream.generate(() -> "Elsa")
                .filter(n -> n.length() == 4)
                .limit(2)
                .sorted()
                .forEach(System.out::println);

        long count = Stream.of("Goldfish", "Finch", "Elephant")
                .filter(str -> str.length() > 5)
                .collect(Collectors.toList())
                .stream().count();
        System.out.println(count);
    }
}
