import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Supplier;

public class FunctionalInterfaceExample {

    public static void main(String[] args) {
        testSupplier();
        System.out.println("=".repeat(100));
    }

    private static void testSupplier() {
        Supplier<LocalDate> s1 = () -> LocalDate.now();
        Supplier<LocalDate> s2 = LocalDate::now;

        LocalDate d1 = s1.get();
        LocalDate d2 = s2.get();

        System.out.println(d1);
        System.out.println(d2);

        Supplier<StringBuilder> sb1 = () -> new StringBuilder();
        Supplier<StringBuilder> sb2 = StringBuilder::new;

        System.out.println(sb1.get());
        System.out.println(sb2.get());

        Supplier<ArrayList<String>> l1 = () -> new ArrayList<>();
        Supplier<ArrayList<String>> l2 = ArrayList::new;
        ArrayList<String> a1 = l1.get();
        ArrayList<String> a2 = l2.get();
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(l1);
        System.out.println(l2);
    }
}