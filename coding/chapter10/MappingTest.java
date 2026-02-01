import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappingTest {
    public static void main(String[] args) {
        var animals = List.of("lions", "tigers", "bears", "elephants", "birds", "ant");

        Map<String, Integer> animalMap1 = animals.stream().collect(
                Collectors.toMap(str -> str, str -> str.length()));

        Map<String, Integer> animalMap2 = animals.stream().collect(
                Collectors.toMap(str -> str, String::length));

        Map<String, Integer> animalMap3 = animals.stream().collect(
                Collectors.toMap(Function.identity(), String::length));

        Map<Integer, String> animalMap4 = animals.stream().collect(
                Collectors.toMap(String::length, Function.identity(), (s1, s2) -> s1 + ", " + s2));

        Map<Integer, String> animalMap5 = animals.stream().collect(
                Collectors.toMap(String::length, Function.identity(), (s1, s2) -> s1 + ", " + s2, TreeMap::new));

        System.out.println(animalMap1);
        System.out.println(animalMap1.getClass());

        System.out.println(animalMap2);
        System.out.println(animalMap2.getClass());

        System.out.println(animalMap3);
        System.out.println(animalMap3.getClass());

        System.out.println(animalMap4);
        System.out.println(animalMap4.getClass());
        
        System.out.println(animalMap5);
        System.out.println(animalMap5.getClass());
    }
}
