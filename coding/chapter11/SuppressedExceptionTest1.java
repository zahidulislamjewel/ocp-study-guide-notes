class MyResource implements AutoCloseable {

    @Override
    public void close() {
        System.out.println("Closing my resource");
    }
}

class AnotherResource implements AutoCloseable {

    @Override
    public void close() {
        throw new IllegalStateException("Problem while another closing");
    }
}

public class SuppressedExceptionTest1 {
    public static void main(String[] args) {
        try (MyResource r = new MyResource()) {
            System.out.println("Using my resource");
        }
        System.out.println("=".repeat(100));

        try (AnotherResource r = new AnotherResource()) {
            System.out.println("Using another resource");
        }
    }
}
