import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SheepManagerVolatile {
    private volatile int sheepCount = 0;
    public void incrementAndReport() {
        // Critical section, two distinct operations
        // two threads could read the same value before either writes back
        System.out.print((++sheepCount) + " ");
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        try {
            SheepManagerVolatile manager = new SheepManagerVolatile();
            for(int i=0; i<20; i++) {
                executorService.submit(() -> manager.incrementAndReport());
            }
        } catch (Exception e) {
            e.printStackTrace();    
        } finally {
            executorService.shutdown();
        }
    }
}
