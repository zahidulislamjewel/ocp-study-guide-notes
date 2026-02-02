import java.util.List;
import java.util.stream.Collectors;

public class TeeingTest {
    record Separations(String spaceSeparated, String commaSeparated) {}

    public static void main(String[] args) {
        var list = List.of("x", "y", "z");

        Separations result = list.stream().collect(
            Collectors.teeing(
                Collectors.joining(" "), 
                Collectors.joining(","), 
                Separations::new
            )
        );
        System.out.println(result);

        var numbers = List.of(4, 9, 2, 3, 5, 7, 8, 1, 6, 0);
        var average = numbers.stream().collect(
            Collectors.teeing(
                Collectors.summingInt(Integer::valueOf), 
                Collectors.counting(), 
                (sum, count) -> sum / (double) count
            )
        );
        System.out.println(average);
    }
}
