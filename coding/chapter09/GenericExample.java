import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GenericExample {
    public static void main(String[] args) {
        List numbers = new ArrayList(List.of(1, 2, 3));
        System.out.println(numbers);
        Integer element = (Integer) numbers.get(0);
        System.out.println(element);

        numbers.add("Welcome to the zoo!");
        System.out.println(numbers);
        System.out.println("=".repeat(100));

        var list = new ArrayList<String>();
        list.add("Magician");
        list.add("Assistant");
        System.out.println(list);

        list.removeIf(s -> s.startsWith("A"));
        System.out.println(list);

        list.add("Manager");
        list.add("");
        System.out.println(list);
        // list.removeIf(s -> s.isEmpty());
        list.removeIf(String::isEmpty);
        System.out.println(list);
        System.out.println("=".repeat(100));

        var names = new ArrayList<String>();
        names.add("Alex");
        names.add("Lee");
        names.addFirst("Cooper");
        names.addLast("John");
        names.add(2, "Jane");
        System.out.println(names);
        // names.replaceAll(name -> name.toUpperCase());
        names.replaceAll(String::toUpperCase);
        System.out.println(names);

        System.out.println("=".repeat(100));

        var nums = Arrays.asList(4, 9, 2, 3, 5);
        // var nums = List.of(4, 9, 2, 3, 5);  // Replaces each element in list with the result of operator.
        System.out.println(nums);
        nums.replaceAll(x -> x * x);
        System.out.println(nums);
        System.out.println("=".repeat(100));

        var numberList = new LinkedList<Integer>();
        numberList.add(3);
        numberList.add(2);
        numberList.add(1);
        numberList.remove(2);
        numberList.remove(Integer.valueOf(2));
        System.out.println(numberList);
        System.out.println(numberList.remove(Integer.valueOf(100)));    // false
        // System.out.println(numberList.remove(100));                  // java.lang.IndexOutOfBoundsException
    }
}