import java.util.ArrayList;
import java.util.List;

public class BoundingGenericTest {

    public static void printList(List<?> list) {
        for (Object x : list) {
            System.out.println(x);
        }
    }

    static class Bird {
    }

    static class Sparrow extends Bird {
    }

    class A {}
    class B extends A {}
    class C extends B {}

    public static void main(String[] args) {
        List<String> keywords = new ArrayList<>();
        keywords.add("Java");
        keywords.add("Python");

        printList(keywords);
        System.out.println("=".repeat(100));
        List<? extends Bird> birds = new ArrayList<Bird>();
        
        // birds.add(new Sparrow());   // DOES NOT COMPILE, can’t add a Sparrow to List<? extends Bird>
        // birds.add(new Bird());      // DOES NOT COMPILE, can’t add a Bird to List<Sparrow>
       
        System.out.println("=".repeat(100));

        List<?> list1 = new ArrayList<A>();             // OK
        List<? extends A> list2 = new ArrayList<A>();   // OK
        List<? super A> list3 = new ArrayList<A>();     // OK
    }
}
