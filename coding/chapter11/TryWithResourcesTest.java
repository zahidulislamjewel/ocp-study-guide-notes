public class TryWithResourcesTest {

    public static void main(String[] args) {
        try (MyFileClass bookReader = new MyFileClass(1);
            MyFileClass movieReqader = new MyFileClass(2)
    ) {
            System.out.println("Try Block");
            throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("Catch Block");
        } finally {
            System.out.println("Finally Block");
        }
    }
}

class MyFileClass implements AutoCloseable {

    private final int num;

    public MyFileClass(int num) {
        this.num = num;
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing: " + num);
    }
}