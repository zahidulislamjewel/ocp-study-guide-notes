import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GroupingTest {
    public static void main(String[] args) {
        var animals = List.of("lions", "tigers", "bears", "elephants", "birds", "ant");

        Map<Integer, String> animalMap1 = animals.stream().collect(
                Collectors.toMap(String::length, Function.identity(), (s1, s2) -> s1 + "-" + s2));

        System.out.println(animalMap1);

        Map<Integer, List<String>> animalMap2 = animals.stream().collect(
                Collectors.groupingBy(String::length));

        System.out.println(animalMap2);

        System.out.println("=".repeat(100));

        Map<Integer, Set<String>> animalMap3 = animals.stream().collect(
                Collectors.groupingBy(String::length, Collectors.toSet()));

        System.out.println(animalMap3);

        TreeMap<Integer, Set<String>> animalMap4 = animals.stream().collect(
                Collectors.groupingBy(String::length, TreeMap::new, Collectors.toSet()));

        System.out.println(animalMap4);

        System.out.println(animalMap3);

        TreeMap<Integer, List<String>> animalMap5 = animals.stream().collect(
                Collectors.groupingBy(String::length, TreeMap::new, Collectors.toList()));

        System.out.println(animalMap5);

        TreeMap<Integer, Long> animalMap6 = animals.stream().collect(
                Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting()));

        System.out.println(animalMap6);
    }
}
