import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

public class LionPenManager {
    public void removeLions() {
        System.out.println("Removing lions from the pen.");
    }

    private void cleanPen() {
        System.out.println("Cleaning the lion pen.");
    }

    public void addLions() {
        System.out.println("Adding lions to the pen.");
    }

    public void performTask(CyclicBarrier c1, CyclicBarrier c2) {
        try {
            removeLions();
            c1.await(); // Wait for all 4 threads to reach this point
            cleanPen();
            c2.await(); // Wait for all 4 threads to reach this point
            addLions();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        var executorService = Executors.newFixedThreadPool(4);
        try {
            var manager = new LionPenManager();
            var c1 = new CyclicBarrier(4);
            var c2 = new CyclicBarrier(4, () -> System.out.println("*** Pen Cleaned!"));
            for (int i = 0; i < 4; i++) {
                executorService.submit(() -> manager.performTask(c1, c2));
            }
        } finally {
            executorService.shutdown();
        }
    }
}
