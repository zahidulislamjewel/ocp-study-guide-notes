// Superclass: Hopper can throw a checked exception
class Hopper {
    public void hop() throws Exception {
        System.out.println("Hopper hops");
        // Imagine this could fail
        if (Math.random() > 0.5) {
            throw new Exception("Hopper stumble!");
        }
    }
}

// Subclass: Bunny overrides hop(), throws no checked exception
class Bunny extends Hopper {
    @Override
    public void hop() {
        System.out.println("Bunny hops safely");
        // never throws a checked exception
    }
}

// Subclass: Kangaroo overrides hop(), throws narrower checked exception
class Kangaroo extends Hopper {
    @Override
    public void hop() throws IllegalStateException {
        System.out.println("Kangaroo hops strongly");
        // unchecked exception, allowed
        if (Math.random() > 0.7) {
            throw new IllegalStateException("Kangaroo slip!");
        }
    }
}

// Subclass overrides method with a narrower exception
class SpecialBunny extends Hopper {
    @Override
    public void hop() throws IllegalArgumentException {
        System.out.println("Special Bunny hops");
        // legal: IllegalArgumentException is unchecked 
        // (also a narrower type if it were checked)
    }
}

public class OverridingExceptionTest {
    public static void main(String[] args) {
        // Calling Hopper
        try {
            Hopper h = new Hopper();
            h.hop(); // must catch Exception
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Calling Bunny (no checked exception)
        try {
            Hopper b = new Bunny();
            b.hop(); // fine, no checked exception to handle
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }


        Hopper k1 = new Kangaroo();
        try {
            k1.hop(); // must catch Exception because Hopper.hop() declares it
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Calling Kangaroo (throws unchecked only)
        Kangaroo k2 = new Kangaroo();
        k2.hop(); // no try/catch required
    }
}
