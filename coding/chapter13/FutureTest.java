import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTest {
    private static final int UPPER_LIMIT = 1_000_000_000;
    private static int counter = 0;
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            System.out.println("begin");
            Future<?> futureResult = executorService.submit(() -> {
                for (int i = 0; i < UPPER_LIMIT; i++) {
                    counter++;
                }
            });
            System.out.println("Is cancelled: " + futureResult.isCancelled());
            System.out.println("Is done: " + futureResult.isDone());
            System.out.println("Future result: " + futureResult.get());

            // futureResult.get(); // Waits indefinitely for the task to complete
            // Alternatively, we can specify a timeout
            futureResult.get(10, TimeUnit.SECONDS);
            System.out.println("Final counter value: " + counter);
            System.out.println("end");

        } catch (TimeoutException e) {
            System.out.println("Task timed out before completion");
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            System.out.println("finally shutting down executor");
        }
    }
}
