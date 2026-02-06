import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SheepManagerSynchronized {
    private int sheepCount = 0;
    private final Object lock = new Object();
    public void incrementAndReport() {
        synchronized(lock) {
            System.out.print((++sheepCount) + " "); // critical section
        }
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        try {
            SheepManagerSynchronized manager = new SheepManagerSynchronized();
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
