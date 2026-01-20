public class HappyPenguin {
    public static int getHeight() {
        return 3;
    }

    public void printInfo() {
        System.out.println("Height: " + getHeight());
    }
}

class CrestedPenguin extends HappyPenguin {
    public static int getHeight() {
        return 8;
    }
}

class CrestedPenguinTest {
    public static void main(String[] args) {
        HappyPenguin happyPenguin = new CrestedPenguin();
        happyPenguin.printInfo();   // prints 3, not 8
        System.out.println("=".repeat(100));

        CrestedPenguin crestedPenguin = new CrestedPenguin();
        crestedPenguin.printInfo();
    }
}