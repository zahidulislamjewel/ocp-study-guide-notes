import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CollectingResultTest {
    public static void main(String[] args) {
        List<String> words = List.of("java", "stream", "api", "code", "java");
        List<Integer> numbers = List.of(4, 9, 2, 3, 5, 7, 8, 1, 6, 0);

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

        System.out.println("=".repeat(100));

        var sublist1 = words.stream().filter(str -> str.startsWith("j")).collect(Collectors.toSet());
        TreeSet<Integer> sublist2 = numbers.stream()
                .filter(num -> num % 2 == 1)
                .collect(Collectors.toCollection(TreeSet::new)); // Modifiable list
        List<Integer> sublist3 = numbers.stream()
                .filter(num -> num % 2 == 0)
                .toList(); // Immutable, Unmodifiable list

        System.out.println(sublist1);
        System.out.println(sublist2);
        System.out.println(sublist3);

        sublist2.add(11); // Permitted
        sublist3.add(13); // java.lang.UnsupportedOperationException
    }
}
