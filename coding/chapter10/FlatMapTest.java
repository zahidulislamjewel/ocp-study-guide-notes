import java.util.List;
import java.util.stream.IntStream;

public class FlatMapTest {
    public static void main(String[] args) {
        List<int[]> data = List.of(
                new int[] { 1, 2 },
                new int[] { 3, 4, 5 });

        // IntStream(1,2) + IntStream(3,4,5), then IntStream(1,2,3,4,5)
        IntStream stream = data.stream().flatMapToInt(IntStream::of);
        // stream.forEach(System.out::println);
        stream.mapToObj(x -> String.valueOf(x)).reduce((x, y) -> x + " " + y).ifPresent(System.out::println);
    }
}
