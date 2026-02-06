public class CheckResults {
    public static int counter = 0;
    public static final int UPPER_LIMIT = 10_000_000;
    public static void main(String[] args) {
        var job = new Thread(() -> {
            for (int i = 0; i < UPPER_LIMIT; i++) {
                counter++;
            }
        });
        job.start();

        // Accessing shared variable 'counter' from the main thread
        // Polling until the worker thread completes its task
        while(counter < UPPER_LIMIT) {
            System.out.println("Not done yet: " + counter);
            try {
                // Freeing up CPU for the worker thread, polling every second
                Thread.sleep(1_000);    
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted");
            }
        }
        System.out.println("Done: " + counter);
    }
}
