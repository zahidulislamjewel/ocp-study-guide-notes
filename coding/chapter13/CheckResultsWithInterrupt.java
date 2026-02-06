public class CheckResultsWithInterrupt {
    public static int counter = 0;
    public static final int UPPER_LIMIT = 10_000_000;
    public static void main(String[] args) {
        final var mainThread = Thread.currentThread();

        var job = new Thread(() -> {
            for (int i = 0; i < UPPER_LIMIT; i++) {
                counter++;
            }
            // Release the main thread from sleep when done
            // by interrupting it
            // INTERRUPT - Worker thread signals main thread when done
            // Wakes up main thread from sleep
            mainThread.interrupt();
        });
        job.start();

        // POLLING - Main thread keeps checking counter value
        // Accessing shared variable 'counter' from the main thread
        // Polling until the worker thread completes its task
        while(counter < UPPER_LIMIT) {
            System.out.println("Not done yet: " + counter);
            try {
                // Freeing up CPU for the worker thread, polling every second
                Thread.sleep(1_000);    
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted");
                // break;
            }
        }
        System.out.println("Done: " + counter);
    }
}
