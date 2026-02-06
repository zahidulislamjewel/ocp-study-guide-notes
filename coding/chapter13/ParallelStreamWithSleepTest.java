import java.util.List;

public class ParallelStreamWithSleepTest {

    public static int doWork(int n) {
        try {
            Thread.sleep(5_000);    
        } catch (InterruptedException e) {
        }
        return n;
    }
    
    public static void testSequentialStream() {
        var start = System.currentTimeMillis();
        List.of(4, 9, 2, 3, 5).stream()
                .map(w -> doWork(w))
                .forEach(n -> System.out.print(n + " "));
        System.out.println();
        var end = System.currentTimeMillis();
        System.out.println("Time taken (sequential): " + (end - start) + " ms");
    }

    public static void testParallelStream() {
        var start = System.currentTimeMillis();
        List.of(4, 9, 2, 3, 5).parallelStream()
                .map(w -> doWork(w))
                .forEach(n -> System.out.print(n + " "));
        System.out.println();
        var end = System.currentTimeMillis();
        System.out.println("Time taken (parallel): " + (end - start) + " ms");
    }

    public static void testParallelStreamOrdered() {
        var start = System.currentTimeMillis();
        List.of(4, 9, 2, 3, 5).parallelStream()
                .map(w -> doWork(w))
                .forEachOrdered(n -> System.out.print(n + " "));
        System.out.println();
        var end = System.currentTimeMillis();
        System.out.println("Time taken (parallel ordered): " + (end - start) + " ms");
    }

    public static void main(String[] args) {
        testSequentialStream();
        System.out.println("=".repeat(100));
        testParallelStream();   
        System.out.println("=".repeat(100));
        testParallelStreamOrdered();
    }
}
