import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalTest {

    public static Optional<Double> average(int... scores) {
        if (scores.length == 0) {
            return Optional.empty();
        }
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }

        return Optional.of((double) sum / scores.length);
    }

    public static void main(String[] args) {
        System.out.println(average(90, 100));
        System.out.println(average());
        System.out.println("=".repeat(100));

        // Optional<Double> opt = average(90, 100);
        Optional<Double> opt = average();
        System.out.println(opt.orElse(Double.NaN));
        System.out.println(opt.orElseGet(() -> Math.random() * 100));
        System.out.println(opt.orElseThrow());
        System.out.println(opt.orElseThrow(() -> new NoSuchElementException("The element is not found!")));
    }
}
