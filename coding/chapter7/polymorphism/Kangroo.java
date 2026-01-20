class Marsupial {
    protected int age = 2;

    public static boolean isBiped() {
        return false;
    }
}

public class Kangroo extends Marsupial {
    protected int age = 6;

    public static boolean isBiped() {
        return true;
    }
}

class KangrooTest {
    public static void main(String[] args) {
        Kangroo joey = new Kangroo();
        Marsupial moey = joey;

        System.out.println(joey.isBiped());
        System.out.println(joey.age);
        System.out.println("=".repeat(100));

        System.out.println(moey.isBiped());
        System.out.println(moey.age);
    }
}
