import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorTest {
    public static void main(String[] args) throws InterruptedException {
        // var excutorService = Executors.newSingleThreadScheduledExecutor();
        var excutorService = Executors.newSingleThreadScheduledExecutor();
        try {
            // Schedule a task to run after a 10-second delay
            Runnable runnableTask = () -> System.out.println("Task executed after delay");
            Callable<Integer> callableTask = () -> new Random().nextInt(100);
            Runnable runnableTaskPrintingRandom = () -> System.out.println(">> Task executed after delay in fixed rate with random: " + new Random().nextInt(100));
            
            ScheduledFuture<?> futureRunnableResult = excutorService.schedule(runnableTask, 10, TimeUnit.SECONDS);
            ScheduledFuture<Integer> futureCallableResult = excutorService.schedule(callableTask, 1, TimeUnit.MINUTES);
            ScheduledFuture<?> runnableRandomResult = excutorService.scheduleAtFixedRate(runnableTaskPrintingRandom, 1, 1, TimeUnit.SECONDS);
            // ScheduledFuture<?> runnableRandomResult = excutorService.scheduleWithFixedDelay(runnableTaskPrintingRandom, 10, 10, TimeUnit.SECONDS);

            System.out.println("Scheduled Runnable task result: " + futureRunnableResult.get());
            System.out.println("Scheduled Callable task result: " + futureCallableResult.get());
            System.out.println("Scheduled Runnable with random result: " + runnableRandomResult.get());

            System.out.println("Is done (runnable): " + futureRunnableResult.isDone());
            System.out.println("Is cancelled (runnable): " + futureRunnableResult.isCancelled());
            System.out.println("Is done (callable): " + futureCallableResult.isDone());
            System.out.println("Is cancelled (callable): " + futureCallableResult.isCancelled());
            System.out.println("Get delay (runnable): " + futureRunnableResult.getDelay(TimeUnit.SECONDS) + " seconds");
            System.out.println("Get delay (callable): " + futureCallableResult.getDelay(TimeUnit.SECONDS) + " seconds");
            System.out.println("All scheduled tasks completed");
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
