import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public class SpliteratorTest {
    public static void main(String[] args) {
        var list = List.of("bird-", "bunny-", "cat-", "dog-", "fish-", "lamb-", "mouse-");

        Spliterator<String> original = list.spliterator();
        Spliterator<String> bag1 = original.trySplit();
        Spliterator<String> bag2 = original.trySplit();

        bag1.forEachRemaining(System.out::print); // bird-bunny-cat-
        System.out.println();
        bag2.forEachRemaining(System.out::print); // dog-fish-
        System.out.println();
        original.forEachRemaining(System.out::print); // lamb-mouse-
        System.out.println();

        System.out.println("=".repeat(100));

        Spliterator<Integer> sp = Stream.iterate(1, n -> n + 1).spliterator();

        Spliterator<Integer> split = sp.trySplit();

        split.tryAdvance(System.out::println); // 1
        split.tryAdvance(System.out::println); // 2
        split.tryAdvance(System.out::println); // 3
    }
}
