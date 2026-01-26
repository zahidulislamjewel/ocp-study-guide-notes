import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class ReductionTest {
    public static void main(String[] args) {
        Stream<String> charStream = Stream.of("w", "o", "l", "f");
        // String word = charStream.reduce("", (s, t) -> s + t);
        String word = charStream.reduce("", String::concat);
        System.out.println(word);

        Stream<Integer> numberStream = Stream.of(3, 5, 6);
        Integer multiplication = numberStream.reduce(1, (a, b) -> a * b);
        System.out.println(multiplication);
        System.out.println("=".repeat(100));

        BinaryOperator<Integer> binaryOperator = (a, b) -> a * b;
        Stream<Integer> empty = Stream.empty();
        Stream<Integer> oneElement = Stream.of(3);
        Stream<Integer> threeElement = Stream.of(3, 5, 6);

        empty.reduce(binaryOperator).ifPresent(System.out::println);
        oneElement.reduce(binaryOperator).ifPresent(System.out::println);
        threeElement.reduce(binaryOperator).ifPresent(System.out::println);

        int sum = List.of(1, 2, 3, 4).stream().reduce(0, Integer::sum);
        System.out.println(sum);

        Optional<Integer> max = List.of(3, 7, 2, 9)
                .stream()
                .reduce(Integer::max);
        max.ifPresent(System.out::println);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int parallelSum = numbers.parallelStream().reduce(0, Integer::sum, Integer::sum); // Result: 55
        System.out.println(parallelSum);
    }
}
