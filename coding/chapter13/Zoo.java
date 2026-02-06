public class Zoo {

    public static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
        System.out.println("Thread finished");
    }

    public static void main(String[] args) {
        var job = new Thread(() -> pause(10_000));
        job.setDaemon(true);
        job.start();

        System.out.println("Main method finished");
    }
}