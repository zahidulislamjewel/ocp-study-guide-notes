import java.lang.foreign.Linker.Option;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveStreamTest {
        public static void main(String[] args) {
                Stream<Integer> stream1 = Stream.of(1, 2, 3);
                System.out.println(stream1.reduce(0, (sum, num) -> sum + num));

                Stream<Integer> stream2 = Stream.of(1, 2, 3);
                System.out.println(stream2.mapToInt(x -> x).sum());

                IntStream intStream = IntStream.of(1, 2, 3);
                OptionalDouble average = intStream.average();
                System.out.println(average.getAsDouble());

                System.out.println("=".repeat(100));

                OptionalDouble firstAverage = IntStream.of(10, 20, 30).average();
                System.out.println(firstAverage.getAsDouble());

                OptionalDouble secondAverage = IntStream.of(30, 40, 50).average();
                secondAverage.ifPresent(System.out::println);

                var thirdAverage = IntStream.of(30, 50, 70).average();
                thirdAverage.ifPresent(System.out::println);

                System.out.println("=".repeat(100));

                Stream<Integer> boxed = IntStream.of(4, 9, 2).boxed();
                boxed.forEach(System.out::println);

                OptionalInt maxStream = IntStream.of(4, 9, 2).max();
                maxStream.ifPresent(System.out::println);

                OptionalInt minStream = IntStream.of(4, 9, 2).min();
                minStream.ifPresent(System.out::println);

                IntStream.range(1, 10).forEach(System.out::print);
                int rangeSumExclusive = IntStream.range(1, 10).sum();
                System.out.println(rangeSumExclusive);

                String firstResult = IntStream.range(1, 10).boxed().map(num -> String.valueOf(num))
                                .collect(Collectors.joining(", "));
                System.out.println(firstResult);

                String secondResult = IntStream.range(1, 10).boxed().map(String::valueOf)
                                .collect(Collectors.joining(", "));
                System.out.println(secondResult);

                IntStream.range(1, 10).boxed().map(String::valueOf)
                                .reduce((str1, str2) -> str1 + ", " + str2).ifPresent(System.out::println);

                int rangeSumInclusive = IntStream.rangeClosed(1, 10).sum();
                System.out.println(rangeSumInclusive);

                IntStream.rangeClosed(1, 10).reduce(Integer::sum).ifPresent(System.out::println);

                System.out.println("=".repeat(100));

                var numbers = IntStream.of(4, 9, 2, 4, 5, 7, 8, 1, 6, 0);
                var stats = numbers.summaryStatistics();

                System.out.println(stats.getCount());
                System.out.println(stats.getMin());
                System.out.println(stats.getMax());
                System.out.println(stats.getSum());
                System.out.println(stats.getAverage());
                System.out.println(stats);

                System.out.println("=".repeat(100));

                DoubleStream empty = DoubleStream.empty();
                empty.average().ifPresent(System.out::println);

                DoubleStream oneValue = DoubleStream.of(3.14);
                oneValue.forEach(System.out::println);

                DoubleStream varargs = DoubleStream.of(1.0, 1.1, 1.2);
                varargs.forEach(System.out::println);

                var random = DoubleStream.generate(Math::random);
                var fractions = DoubleStream.iterate(.5, d -> d / 2);
                var multiples = IntStream.iterate(2, n -> n * n);
                var secondMultiples = IntStream.iterate(2, n -> n + n);

                random.limit(3).forEach(System.out::println);
                fractions.limit(3).forEach(System.out::println);
                multiples.limit(5).forEach(System.out::println);
                secondMultiples.limit(5).mapToObj(String::valueOf).reduce((n1, n2) -> n1 + ", " + n2)
                                .ifPresent(System.out::println);

                Random r = new Random();
                r.ints().limit(5).forEach(System.out::println);
                r.ints(1, 11).limit(5).forEach(System.out::println);
                r.ints(5, 11, 21).forEach(System.out::println);
        }
}
