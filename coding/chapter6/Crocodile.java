
class Reptile {
    protected int speed = 10;
}

public class Crocodile extends Reptile {
    protected int speed = 20;

    public int getSpeed() {
        int speed = 30;         // local variable
        System.out.println(speed);       // prints 30 (local)
        System.out.println(this.speed);  // prints 20 (Crocodile)
        System.out.println(super.speed); // prints 10 (Reptile)
        return this.speed;
    }
}

class ReptileTest {
    public static void main(String[] args) {
        var croc = new Crocodile();
        System.out.println(croc.getSpeed());
    }
}
