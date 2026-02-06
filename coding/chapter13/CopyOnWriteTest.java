import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteTest {
    public static void main(String[] args) {
        // List<Integer> favoriteNumbers = new ArrayList<>(List.of(4, 3, 32));  // java.util.ConcurrentModificationException
        List<Integer> favoriteNumbers = new CopyOnWriteArrayList<>(List.of(4, 3, 32));
        for (var num : favoriteNumbers) {
            System.out.print(num + " ");
            favoriteNumbers.add(num + 1);
        }
        System.out.println();
        System.out.println(favoriteNumbers);
        System.out.println(favoriteNumbers.size());
    }
}
