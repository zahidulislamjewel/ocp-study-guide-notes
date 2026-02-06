import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class OverloadingTest {

    public static void main(String[] args) {
        var dove = new Dove();
        dove.fly(1); // calls the fly(int) method
        dove.fly((short) 1); // calls the fly(short) method
        System.out.println("=".repeat(100));

        var pelican = new Pelican();
        pelican.fly("test");
        pelican.fly(56);
        System.out.println("=".repeat(100));

        Parrot.print("abc");
        Parrot.print(Arrays.asList(3, 4, 5));
        Parrot.print(LocalDate.of(2019, Month.JULY, 4));
        System.out.println("=".repeat(100));

        var Ostrich = new Ostrich();
        Ostrich.fly(123);
        Ostrich.fly(123L);
        System.out.println("=".repeat(100));

        var kiwi = new Kiwi();
        kiwi.fly(100);
        kiwi.fly(Integer.valueOf(100));
        System.out.println("=".repeat(100));

        var emu = new Emu();
        emu.walk(new int[] { 4, 9, 2 }); // this code does not result in autoboxing:
        emu.walk(new Integer[] { 4, 9, 2 }); // this code does not result in autoboxing:
        System.out.println("=".repeat(100));

        var toucan = new Toucan();
        toucan.fly();
        toucan.fly(1, 2, 3);
    }
}

class Falcon {
    public void fly(int numMiles) {
    }

    public void fly(short numFeet) {
    }

    public boolean fly() {
        return false;
    }

    void fly(int numMiles, short numFeet) {
    }

    public void fly(short numFeet, int numMiles) throws Exception {
    }
}

class Eagle {
    public void fly(int numMiles) {
    }
    // public int fly(int numMiles) { return 1; }// DOES NOT COMPILE
}

class Dove {
    public void fly(int numMiles) {
        System.out.println("int");
    }

    public void fly(short numMiles) {
        System.out.println("short");
    }
}

class Pelican {
    public void fly(String s) {
        System.out.println("string " + s);
    }

    public void fly(Object o) {
        System.out.println("object " + o);
    }
}

class Parrot {
    public static void print(List<Integer> i) {
        System.out.println("I " + i);
    }

    public static void print(CharSequence c) {
        System.out.println("C " + c);
    }

    public static void print(Object o) {
        System.out.println("O" + o);
    }
}

class Ostrich {
    public void fly(int i) {
        System.out.println("int " + i);
    }

    public void fly(long l) {
        System.out.println("long " + l);
    }
}

class Kiwi {
    public void fly(int numMiles) {
        System.out.println("int " + numMiles);
    }

    public void fly(Integer numMiles) {
        System.out.println("Integer " + numMiles);
    }
}

class Emu {
    public void walk(int[] ints) {
        System.out.println("int[] " + Arrays.toString(ints));
    }

    public void walk(Integer[] integers) {
        System.out.println("Integer[] " + Arrays.toString(integers));
    }
}

class Toucan {
    // public void fly(int[] lengths) {
    // System.out.println("int[] " + Arrays.toString(lengths));
    // }

    // DOES NOT COMPILE, because, they are of the same signatures, either one can be
    // present, not both
    public void fly(int... lengths) {
        System.out.println("int[] " + Arrays.toString(lengths));
    }
}