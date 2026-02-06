import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StatefulLambdaTest {
    public static List<Integer> addValues(IntStream source) {
        var data = Collections.synchronizedList(new ArrayList<Integer>());
        source.filter(s -> s % 2 == 0)
                .forEach(n -> data.add(n));
        return data;
    }

    public static List<Integer> addValuesSafefly(IntStream source) {
        return source.filter(s -> s % 2 == 0)
            .boxed()
            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        var list = addValues(IntStream.range(0, 20));
        System.out.println("Added values: " + list);
        System.out.println("=".repeat(100));

        var unpredictableList = addValues(IntStream.range(0, 20).parallel());
        System.out.println("Added values Unsafe (Parallel): " + unpredictableList);    

        System.out.println("=".repeat(100));

        var safeList = addValuesSafefly(IntStream.range(0, 20).parallel());
        System.out.println("Added values Safely (Parallel): " + safeList);  
    }
}
