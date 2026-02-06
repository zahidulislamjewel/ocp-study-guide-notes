import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCollectionExample {
    public static void main(String[] args) {
        // var foodData = new HashMap<String, Integer>();   // This would throw ConcurrentModificationException
        var foodData = new ConcurrentHashMap<String, Integer>();
        foodData.put("lion", 5);
        foodData.put("tiger", 3);
        foodData.put("penguin", 1);
        foodData.put("flamingo", 2);

        for (var entry : foodData.entrySet()) {
            System.out.println(entry.getKey() + " has " + entry.getValue() + " pieces of food.");
            foodData.remove(entry.getKey());    // this is safe with ConcurrentHashMap but not with HashMap
        }
    }
}
