import java.util.stream.Stream;

public class InfiniteStreamTest {
    public static void main(String[] args) {
        Stream<Integer> oddNumberUnder100 = Stream.iterate(1, n -> n < 100, n -> n + 2);
        System.out.println(oddNumberUnder100.toList());
    }
}
