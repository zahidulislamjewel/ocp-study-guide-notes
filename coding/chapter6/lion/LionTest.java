package lion;

public class LionTest {

    public static void main(String[] args) {
        var lion = new Lion();
        lion.setProperties(3, "Kion");
        lion.roar();
        System.out.println("=".repeat(100));
        var africanLion = new AfricanLion();
        africanLion.setProperties(4, "Simba");
        africanLion.roar();
    }
}
