import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SheepManagerAtomic {
    private AtomicInteger sheepCount = new AtomicInteger(0);
    public void incrementAndReport() {
        System.out.print((sheepCount.incrementAndGet()) + " "); // equivalent to ++sheepCount
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        try {
            SheepManagerAtomic manager = new SheepManagerAtomic();
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
