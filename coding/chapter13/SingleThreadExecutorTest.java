import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            System.out.println("begin");
            // Submitting multiple tasks to the single-threaded executor
            // So, the tasks will execute sequentially in the order they were submitted
            executorService.execute(() -> System.out.println("Running in executor"));
            executorService.execute(() -> {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Count: " + (i+1));
                }
            });
            executorService.execute(() -> System.out.println("Done"));
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            System.out.println("finally shutting down executor");
        }
    }
}
