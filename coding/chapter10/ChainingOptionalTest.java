import java.util.ArrayList;

public class ChainingOptionalTest {
    public static void main(String[] args) {
        var cats = new ArrayList<>();
        cats.add("Annie");
        cats.add("Ripley");

        var stream = cats.stream();

        cats.add("KC");

        System.out.println(stream.count());
    }
}
