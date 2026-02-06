import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CallableTest {
    public static void main(String[] args) throws InterruptedException {
        var excutorService = Executors.newSingleThreadExecutor();
        try {
            Future<Integer> futureResult = excutorService.submit(() -> new Random().nextInt(100));
            System.out.println("Future result: " + futureResult.get());
            System.out.println("Is cancelled: " + futureResult.isCancelled());
            System.out.println("Is done: " + futureResult.isDone());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            excutorService.shutdown();
            System.out.println("finally shutting down executor");
        }

        excutorService.awaitTermination(1, TimeUnit.MINUTES);
        
        if (excutorService.isTerminated()) {
            System.out.println("All tasks are finished");
        } else {
            System.out.println("Some tasks are still running");
        }
    }
}
