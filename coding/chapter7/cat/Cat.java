interface Walk {
    public default int getSpeed() {
        return 5;
    }
}

interface Run {
    public default int getSpeed() {
        return 10;
    }
}

public class Cat implements Walk, Run {
    public int getSpeed() {
        return 15;
    }

    public int getRunSpeed() {
        return Run.super.getSpeed();
    }

    public int getWalkSpeed() {
        return Walk.super.getSpeed();
    }
}

class CatTest {
    public static void main(String[] args) {
        Cat cat = new Cat();
        System.out.println(cat.getSpeed());
        System.out.println(cat.getWalkSpeed());
        System.out.println(cat.getRunSpeed());
    }
}