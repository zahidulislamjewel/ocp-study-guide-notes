import java.util.stream.IntStream;

public class ParallelStreamTest {
    public static final int MAX = 10_000_000;

    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }
    
    public static void testSequentialStream() {
        var start = System.currentTimeMillis();
        var count = IntStream.range(1, MAX)
                .filter(ParallelStreamTest::isPrime)
                .count();
        System.out.println("Sequential Stream - Primes found: " + count);
        var end = System.currentTimeMillis();
        System.out.println("Time taken (sequential): " + (end - start) + " ms");
    }

    public static void testParallelStream() {
        var start = System.currentTimeMillis();
        var count = IntStream.range(1, MAX)
                .parallel()
                .filter(ParallelStreamTest::isPrime)
                .count();
        System.out.println("Parallel Stream - Primes found: " + count);
        var end = System.currentTimeMillis();
        System.out.println("Time taken (parallel): " + (end - start) + " ms");
    }

    public static void main(String[] args) {
        testSequentialStream();
        testParallelStream();   
    }
}
