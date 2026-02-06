public class Penguin {
    public int getHeight() {
        return 3;
    }

    public void printInfo() {
        System.out.println("Height: " + this.getHeight());
    }
}

class EmperorPenguin extends Penguin {
    public int getHeight() {
        return 8;
    }
}

class EmperorPenguinTest {
    public static void main(String[] args) {
        new EmperorPenguin().printInfo();   // prints 8, not 3
    }
}