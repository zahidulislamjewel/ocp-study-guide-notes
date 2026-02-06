import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ParallelReductionTest {
    public static void main(String[] args) {
        var numbers = List.of(4, 9, 2, 3, 5, 7, 8, 1, 6, 0);
        var chars = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");

        numbers.stream().findAny()
                .ifPresent(n -> System.out.println("Sequential findAny: " + n));

        numbers.parallelStream().findAny()
                .ifPresent(n -> System.out.println("Parallel findAny: " + n));

        numbers.stream().unordered().parallel().skip(5).findAny()
                .ifPresent(n -> System.out.println("Unordered Parallel findAny: " + n));

        System.out.println("=".repeat(100));

        numbers.parallelStream().reduce((a, b) -> a + b)
                .ifPresent(result -> System.out.print("Associative Reduction (Safer): " + result + "\n"));

        numbers.parallelStream().reduce((a, b) -> a - b)
                .ifPresent(result -> System.out.print("Non-Associative Reduction (Unsafe): " + result + "\n"));

        System.out.println("=".repeat(100));

        var safeReduction = chars.stream().reduce("X", String::concat);
        System.out.print("Associative Reduction (Safer): " + safeReduction + "\n");

        var unsafeReduction = chars.parallelStream().reduce("X", (s, t) -> s + t);
        System.out.print("Non-Associative Reduction (Unsafe): " + unsafeReduction + "\n");

        var safeReductionWithCombiner = chars.parallelStream()
                .reduce("X", (s, t) -> s + t, (u, v) -> u + "|" + v);
        System.out.print("Associative Reduction with Combiner (Safer): " + safeReductionWithCombiner + "\n");

        System.out.println("=".repeat(100));

        var letters = List.of("t", "a", "s", "b", "u", "c", "d", "e", "v", "p");

        var collectedLetters = letters.stream().parallel().collect(
                StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append).toString();
        System.out.println(collectedLetters);

        var collectedLetterSet = letters.stream().parallel().collect(
                ConcurrentSkipListSet::new,
                Set::add,
                Set::addAll).toString();
        System.out.println(collectedLetterSet);
        System.out.println("=".repeat(100));

        var animals = List.of("lions", "tigers", "bears", "wolves", "foxes", "eagles", "sharks", "dolphins");
        ConcurrentMap<Integer, String> animalMap = animals.stream().collect(
                Collectors.toConcurrentMap(
                        String::length, 
                        k -> k, 
                        (s1, s2) -> s1 + "," + s2
                )
        );
        System.out.println(animalMap);
        System.out.println(animalMap.getClass());

        ConcurrentMap<Integer, List<String>> groupedAnimals = animals.stream().collect(Collectors.groupingByConcurrent(String::length));
        System.out.println(groupedAnimals);
    }
}
