import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PartitioningTest {
    public static void main(String[] args) {
        var animals = List.of("lions", "tigers", "bears", "elephants", "birds", "ant");

        Map<Boolean, List<String>> partitionMap1 = animals.stream().collect(
                Collectors.partitioningBy(
                        str -> str.length() <= 5));
        System.out.println(partitionMap1);

        Map<Boolean, Set<String>> partitionMap2 = animals.stream().collect(
                Collectors.partitioningBy(
                        str -> str.length() <= 5, Collectors.toSet())
                    );
        System.out.println(partitionMap2);

        Map<Boolean, Long> partitionMap3 = animals.stream().collect(
                Collectors.partitioningBy(
                        str -> str.length() <= 5, Collectors.counting())
                    );
        System.out.println(partitionMap3);
    }
}
