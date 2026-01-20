import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.TreeMap;

public class MapMethodTest {
    public static void main(String[] args) {
        addElementsAndPrint(new HashMap<>());
        System.out.println("=".repeat(100));

        addElementsAndPrint(new LinkedHashMap<>());
        System.out.println("=".repeat(100));

        addElementsAndPrint(new TreeMap<>());
        System.out.println("=".repeat(100));

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        frequencyMap.put(1, 2);
        frequencyMap.put(2, 4);
        frequencyMap.put(3, 6);
        System.out.println(frequencyMap);
        frequencyMap.replace(3, 9);
        System.out.println(frequencyMap);
        frequencyMap.replaceAll((k, v) -> k + v);
        System.out.println(frequencyMap);
        System.out.println("=".repeat(100));

        Map<String, String> favorites = new HashMap<>();
        favorites.put("Jenny", "Bus Tour");
        favorites.put("Tom", null);
        System.out.println(favorites);
        favorites.putIfAbsent("Jenny", "Tram");
        favorites.putIfAbsent("Sam", "Tram");
        favorites.putIfAbsent("Tom", "Tram");
        System.out.println(favorites);
        System.out.println("=".repeat(100));

        BiFunction<String, String, String> mapper = (v1, v2) -> v1.length() > v2.length() ? v1 : v2;
        String jenny = favorites.merge("Jenny", "Skyride", mapper);
        String tom = favorites.merge("Tom", "Skyride", mapper);
        System.out.println(jenny);
        System.out.println(tom);
        System.out.println(favorites);
        String sam = favorites.merge("Ram", "Skyride", mapper);
        System.out.println(sam);
        System.out.println(favorites);

        BiFunction<String, String, String> nullMapper = (v1, v2) -> null;
        favorites.merge("Jenny", "Swimming", nullMapper);
        favorites.merge("Jane", "Hiking", nullMapper);
        System.out.println(favorites);

    }

    public static void addElementsAndPrint(Map<String, String> animalMap) {
        // Map<String, String> animalMap = new HashMap<>();

        animalMap.put("koala", "bamboo");
        animalMap.put("lion", "meat");
        animalMap.put("giraffe", "leaf");
        animalMap.put("panda", "bamboo");
        System.out.println(animalMap);

        // for (String key : animalMap.keySet()) {
        // System.out.println("K: " + key + ", V: " + animalMap.get(key));
        // }
        // System.out.println();

        for (Entry<String, String> entry : animalMap.entrySet()) {
            System.out.println("K: " + entry.getKey() + ", V: " + entry.getValue());
        }
        System.out.println();

        // for (var entry : animalMap.entrySet()) {
        // System.out.println("K: " + entry.getKey() + ", V: " + entry.getValue());
        // }
        // System.out.println();

        // animalMap.entrySet().forEach(entry -> System.out.println("K: " +
        // entry.getKey() + ", V: " + entry.getValue()));
        // System.out.println();

        animalMap.forEach((key, value) -> System.out.println("K: " + key + ", V: " + value));
        System.out.println();
        animalMap.keySet().forEach(System.out::println);
        System.out.println();
        animalMap.values().forEach(System.out::println);

        System.out.println(animalMap.containsKey("lion"));
        System.out.println(animalMap.containsKey("elephant"));
        System.out.println(animalMap.containsValue("bamboo"));
        System.out.println(animalMap.containsValue("grass"));
        System.out.println(animalMap.size());
        System.out.println(animalMap.isEmpty());
        animalMap.clear();
        System.out.println(animalMap);
        System.out.println(animalMap.size());
        System.out.println(animalMap.isEmpty());
    }
}
