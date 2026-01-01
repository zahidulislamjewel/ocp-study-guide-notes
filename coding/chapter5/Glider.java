import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Glider {
    public static String glide(int i, int j) {
        System.out.print("glide(int, int) ");
        return i + " " + j;
    }

    public static String glide(Integer i, Integer j) {
        System.out.print("glide(Integer, Integer) ");
        return i + " " + j;
    }

    public static String glide(long i, long j) {
        System.out.print("glide(long, long) ");
        return i + " " + j;
    }

    public static String glide(int... nums) {
        System.out.print("glide(int... nums) ");
        return Arrays.toString(nums);
    }

    public static String glide(String s) {
        System.out.print("glide(String) ");
        return s.toUpperCase();
    }

    public static String glide(String s, String t) {
        System.out.print("glide(String, String) ");
        return s + " " + t;
    }

    public static String glide(String... s) {
        System.out.print("glide(String...) ");
        return Arrays.toString(s);
    }

    public static String glide(Object s) {
        System.out.print("glide(Object) ");
        return s.toString();
    }

    public static String glide(LocalDate s) {
        System.out.print("glide(LocalDate) ");
        return s.toString();
    }

    public static void main(String[] args) {
        System.out.println(glide(4, 9));
        System.out.println(glide(4, 9L));
        System.out.println(glide(4L, 9));
        System.out.println(glide(4L, 9L));
        System.out.println(glide(4, 9, 2));
        System.out.println(glide("Falcon"));
        System.out.println(glide("Falcon", "Eagle", "Owl"));
        System.out.println(glide("Falcon", "Eagle"));
        System.out.println(glide(LocalDate.now()));
        System.out.println(glide(LocalDateTime.now()));
        System.out.println(glide(BigDecimal.valueOf(1_024L)));
    }
}
