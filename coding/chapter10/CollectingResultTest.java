import java.util.List;
import java.util.stream.Collectors;

public class CollectingResultTest {
    public static void main(String[] args) {
        List<String> words = List.of("java", "stream", "api", "code", "java");
        List<Integer> numbers = List.of(10, 20, 30, 40, 50);

        // toList, toSet
        System.out.println(words.stream().collect(Collectors.toList()));
        System.out.println(words.stream().collect(Collectors.toSet()));

        // counting
        System.out.println(words.stream().collect(Collectors.counting()));
        System.out.println(words.stream().count());

        // joining
        System.out.println(words.stream().collect(Collectors.joining(", ")));
        System.out.println(words.stream().collect(Collectors.joining(", ", "[", "]")));
        System.out.println(words.stream().collect(Collectors.joining(" | ")));
    }
}
