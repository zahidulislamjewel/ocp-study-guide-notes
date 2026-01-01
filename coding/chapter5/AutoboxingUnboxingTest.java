import java.util.Arrays;

public class AutoboxingUnboxingTest {
    public static void main(String[] args) {
        int quack = 5;
        Integer autoBoxedQuack = quack; // autoboxing
        int unboxedQuack = autoBoxedQuack;

        System.out.println("quack = " + quack);
        System.out.println("autoBoxedQuack = " + autoBoxedQuack);
        System.out.println("unboxedQuack = " + unboxedQuack);
        System.out.println("=".repeat(100));

        int meow = 4;
        Integer autoBoxedMeow = Integer.valueOf(meow);
        int unboxedMeow = autoBoxedMeow.intValue();

        System.out.println("meow = " + meow);
        System.out.println("autoBoxedMeow = " + autoBoxedMeow);
        System.out.println("unboxedMeow = " + unboxedMeow);
        System.out.println("=".repeat(100));

        Integer a = 100;
        Integer b = 100;
        System.out.println("a == b: " + (a == b));
        Integer c = 192;
        Integer d = 192;
        System.out.println("c == d: " + (c == d));
        System.out.println("=".repeat(100));

        var obj = new AutoboxingUnboxingTest();
        obj.test(10); // calls the exact match
        obj.test(10L); // call the widening (numeric promotion)
        obj.test(Integer.valueOf(10)); // call the widening (numeric promotion)
        System.out.println("=".repeat(100));

        // Long badGorilla1 = 8; // gives error: cannot convert from int to Long (as
        // this requires numeric promotion + autoboxing)
        Long badGrorilla2 = 8L; // works, long can be numerically promoted to Long
        Long badGrorilla3 = Long.valueOf(8); // works, exact match

        System.out.println(badGrorilla2);
        System.out.println(badGrorilla3);
        System.out.println("=".repeat(100));

        Boolean status = true; // Autoboxing
        boolean flag = Boolean.FALSE; // Unboxing
        System.out.println(status);
        System.out.println(flag);
        System.out.println("=".repeat(100));

        int[] openingHours = {9, 12};
        Integer[] closingHours = {9, 12};
        double[] humidityAtZoo = {78, 55};
        // Double[] temperatureAtZoo = {78, 55}; // Doesn't compile because, numeric promotion + autoboxing required
        System.out.println(Arrays.toString(openingHours));
        System.out.println(Arrays.toString(closingHours));
        System.out.println(Arrays.toString(humidityAtZoo));
    }

    public void test(int x) {
        System.out.println("int x = " + x);
    }

    public void test(long x) {
        System.out.println("long x = " + x);
    }

    public void test(Integer x) {
        System.out.println("Integer x = " + x);
    }
}
