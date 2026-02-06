import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CollectionMethodTest {
    public static void main(String[] args) {
        var birds = List.of("peacock", "chicken", "peacock", "turkey");
        System.out.println(birds.indexOf("peacock"));
        System.out.println(birds.lastIndexOf("peacock"));
        System.out.println(birds.indexOf("penguin"));
        System.out.println("=".repeat(100));

        List<String> birdList = new ArrayList<>();
        birdList.add("hawk");
        birdList.add("robin");
        System.out.println(birdList);

        Object[] objectArray = birdList.toArray();
        System.out.println(objectArray);
        System.out.println(objectArray.length);

        // String[] stringArray = (String[])birdList.toArray(); // throws
        // java.lang.IndexOutOfBoundsException
        String[] stringArray = birdList.toArray(new String[0]);
        System.out.println(stringArray);
        System.out.println(stringArray.length);
        System.out.println("=".repeat(100));

        Set<Integer> s1 = new HashSet<>();
        s1.add(66);
        s1.add(10);
        s1.add(66);
        s1.add(8);

        Set<Integer> s2 = new LinkedHashSet<>();
        s2.add(66);
        s2.add(10);
        s2.add(66);
        s2.add(8);

        Set<Integer> s3 = new TreeSet<>();
        s3.add(66);
        s3.add(10);
        s3.add(66);
        s3.add(8);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println("=".repeat(100));
    }
}
