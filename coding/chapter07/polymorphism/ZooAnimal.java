import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ZooAnimal {
    public static void sortAndPrintZooAnimals(List<String> animals) {
        Collections.sort(animals);
        for (String a : animals)
            System.out.println(a);
    }

    public static void main(String[] args) {
        var arrayList = new ArrayList<>(Arrays.asList("Tiger", "Lion", "Elephant"));
        sortAndPrintZooAnimals(arrayList);
        System.out.println("=".repeat(100));

        var linkedList = new LinkedList<>(List.of("Tiger", "Lion", "Elephant"));
        sortAndPrintZooAnimals(linkedList);
    }
}
