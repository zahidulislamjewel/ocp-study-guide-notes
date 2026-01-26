import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntermediateOperationTest {
    public static void main(String[] args) {
        Stream<Integer> s = Stream.iterate(1, n -> n + 1);
        s.skip(5).limit(2).forEach(System.out::println); // Skip first 5 elements, then limit to two elements

        System.out.println("=".repeat(100));

        Stream<String> animalStream = Stream.of("monkey", "gorilla", "bonobo");
        // animalStream.map(str -> str.length()).forEach(System.out::print);
        animalStream.map(String::length).forEach(System.out::print);

        System.out.println("=".repeat(100));

        List<String> zero = List.of();
        var one = List.of("Bonobo");
        var two = List.of("Mama Gorilla", "Baby Gorilla");

        Stream<List<String>> animals = Stream.of(zero, one, two);

        animals.flatMap(m -> m.stream()).forEach(System.out::println);

        var s1 = Stream.of("Bonobo");
        var s2 = Stream.of("Mama Gorilla", "Baby Gorilla");
        Stream.concat(s1, s2).forEach(System.out::println);

        System.out.println("=".repeat(100));

        // A nested list structure
        List<List<Integer>> nestedList = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4, 5),
                Arrays.asList(6));
        System.out.println("Original nested list: " + nestedList);

        // Using flatMap to flatten the nested list into a single list
        List<Integer> flatList = nestedList.stream()
                .flatMap(Collection::stream) // A function that returns a stream for each element
                .collect(Collectors.toList());
        System.out.println("Flattened list: " + flatList);
        System.out.println("=".repeat(100));

        Stream<Integer> numberStream = Stream.of(4, 9, 2, 3, 5, 7, 8, 1, 6, 0);
        // numberStream.sorted().forEach(System.out::print);
        // numberStream.sorted(Comparator.naturalOrder()).forEach(System.out::print);
        numberStream.sorted(Comparator.reverseOrder()).forEach(System.out::print);
        System.out.println();
        System.out.println("=".repeat(100));

        var stream = Stream.of("black bear", "brown bear", "grizzly bear");
        long count = stream.filter(str -> str.startsWith("g")) 
                .peek(System.out::println)  // grizzly bear
                .count();                   // 1  
        System.out.println(count);
    }
}
