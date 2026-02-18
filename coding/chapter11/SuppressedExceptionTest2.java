
class CloseableResource implements AutoCloseable {

    @Override
    public void close() {
        throw new IllegalStateException("Problem while another closing");
    }
}

public class SuppressedExceptionTest2 {
    public static void main(String[] args) {
        try (CloseableResource r = new CloseableResource()) {
            System.out.println("Using closeable resource");
            throw new RuntimeException("Prblem while executing try-with-resources");
        } catch (IllegalStateException e) {
            System.out.println("Caught");
        } catch (Exception e) {
            System.out.println("Main exception: " + e.getMessage());

            for (Throwable t : e.getSuppressed()) {
                System.out.println("Suppressed: " + t.getMessage());
            }
        }
    }
}
