package cat;

public class AccessTest {
    public static void main(String[] args) {
        BigCat bigCat = new Jaguar();
        System.out.println(bigCat.size);
        bigCat.size = 11.2;
        System.out.println(bigCat.size);
        System.out.println("=".repeat(100));

        Jaguar jaguar = new Jaguar();
        jaguar.printDetails();
        jaguar.size = 12.2;
        jaguar.printDetails();
    }
}
