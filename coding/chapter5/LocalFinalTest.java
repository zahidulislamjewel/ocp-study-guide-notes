public class LocalFinalTest {
    public static void main(String[] args) {

        // 1. final local variable without initialization
        final int x;
        x = 10; // allowed: assigned before use
        System.out.println(x); // OK

        // x = 20; // DOES NOT COMPILE (final variable already assigned)

        // 2. final with var (type inference)
        final var y = "Java";
        System.out.println(y); // OK

        // y = "OCP"; // DOES NOT COMPILE (final)

        // 3. Local variable must be definitely assigned before use
        int z;
        // System.out.println(z); // DOES NOT COMPILE (z not initialized)

        // 4. Conditional assignment must guarantee initialization
        final int a;
        if (args.length > 0) {
            a = 5;
        } else {
            a = 10;
        }
        System.out.println(a); // OK (assigned in all paths)

        // 5. Example of compilation failure due to possible unassigned variable
        final int b;
        if (args.length > 0) {
            b = 100;
        }
        // System.out.println(b); // DOES NOT COMPILE (b might not have been assigned)

        zooAnimalCheckup();
        zooFriends();
        testEffectivelyFinal(true);
    }

    private static class Animal {
        private String name;

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void zooAnimalCheckup() {
        final int rest = 5;
        final Animal giraffe = new Animal();
        final int[] friends = new int[5];

        System.out.println(rest); // Compiles fine

        giraffe.setName("George"); // Compiles fine
        friends[2] = 2; // Compiles fine
        // giraffe = null; // DOES NOT COMPILE
    }

    public static String zooFriends() {
        String name = "Harry the Hippo";
        var size = 10;
        boolean wet;
        if (size > 100)
            size++;
        name.substring(0);
        wet = true;
        return name;
    }

    public static void testEffectivelyFinal(boolean flag) {

        // Effectively final: assigned once
        int a = 10;
        System.out.println(a);

        // Not effectively final: reassigned
        int b = 5;
        b = 8; // reassignment, thus not effectively final

        // Effectively final: assigned once in all paths
        int c;
        if (flag) {
            c = 1;
        } else {
            c = 1;
        }
        System.out.println(c);

        // Not effectively final: assigned in multiple paths
        int d;
        if (flag) {
            d = 1;
        } else {
            d = 2; // different assignment, thus not effectively final
        }

        // Effectively final usage in lambda
        int x = 100;
        Runnable r = () -> {
            System.out.println(x); // OK: x is effectively final
        };

        // x = 200; // would break effective finality → compilation error above
    }
}
