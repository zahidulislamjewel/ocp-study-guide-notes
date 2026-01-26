import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TerminalOperationTest {
    public static void main(String[] args) {
        Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
        System.out.println(s.count());

        Stream<String> t = Stream.of("monkey", "gorilla", "bonobo", "elephant", "ape");
        Optional<String> min = t.min((s1, s2) -> s1.length() - s2.length());
        min.ifPresent(System.out::println);

        Optional<?> minEmpty = Stream.empty().min((s1, s2) -> 0);
        System.out.println(minEmpty.isPresent());

        System.out.println("=".repeat(100));

        Stream<String> finite = Stream.of("monkey", "gorilla", "bonobo", "tiger", "lion");
        Stream<String> infinite = Stream.generate(() -> "chimp");

        finite.findAny().ifPresent(System.out::println);
        infinite.findAny().ifPresent(System.out::println);

        System.out.println("=".repeat(100));

        var list = List.of("monkey", "2", "chimp");
        Stream<String> infiniStream = Stream.generate(() -> "chimp");
        Predicate<String> pred = x -> Character.isLetter(x.charAt(0));

        System.out.println(list.stream().anyMatch(pred));
        System.out.println(list.stream().allMatch(pred));
        System.out.println(list.stream().noneMatch(pred));
        System.out.println(infiniStream.anyMatch(pred));

        Stream<String> anotherInfiniStream = Stream.generate(() -> "chipmunk");
        System.out.println(anotherInfiniStream.allMatch(pred)); // Never terminates
    }
}
