package coding.chapter7.record;

public record Crane(int numberEggs, String name) {
}

class CraneTest {
    public static void main(String[] args) {
        var mommy = new Crane(4, "Cammy");
        System.out.println(mommy.numberEggs());// 4
        System.out.println(mommy.name());
        System.out.println(mommy);
        System.out.println("=".repeat(100));

        var father = new Crane(0, "Craig");
        System.out.println(father);

        var copy = new Crane(0, "Craig");
        System.out.println(copy);
        System.out.println(father.equals(copy));
        System.out.println(father.hashCode() + ", " + copy.hashCode());
    }
}
