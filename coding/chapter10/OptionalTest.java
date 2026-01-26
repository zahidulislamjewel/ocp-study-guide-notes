import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

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

        System.out.println("=".repeat(100));

        LongStream longs = LongStream.of(5, 15);
        long sum = longs.sum();
        System.out.println(sum);

        DoubleStream doubles = DoubleStream.generate(() -> Math.PI);
        OptionalDouble min = doubles.min(); // runs infinitely
        System.out.println(min.getAsDouble());
    }
}
