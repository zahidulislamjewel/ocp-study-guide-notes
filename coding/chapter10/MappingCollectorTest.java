import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.minBy;

public class MappingCollectorTest {
    public static void main(String[] args) {
        var animals = List.of("lions", "tigers", "bears", "elephants", "birds", "ant");
        Map<Integer, Optional<Character>> animalMap1 = animals.stream().collect(
                Collectors.groupingBy(
                        String::length,
                        Collectors.mapping(
                                str -> str.charAt(0),
                                Collectors.minBy((a, b) -> a - b))));
        System.out.println(animalMap1);

        var animalMap2 = animals.stream().collect(
                groupingBy(
                        String::length,
                        mapping(
                                str -> str.charAt(0),
                                minBy((a, b) -> a - b))));
        System.out.println(animalMap2);
    }
}
