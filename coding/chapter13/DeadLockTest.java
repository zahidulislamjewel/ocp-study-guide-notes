import java.util.concurrent.Executors;

class Food {
}

class Water {
}

class Fox {
    private String name;

    public Fox(String name) {
        this.name = name;
    }

    public void eatAndDrink(Food food, Water water) {
        synchronized (food) {
            System.err.println(this.name + " Got Food!");
            move();
            synchronized (water) {
                System.err.println(this.name + " Got Water!");
            }
        }
    }

    public void drinkAndEat(Food food, Water water) {
        synchronized (water) {
            System.err.println(this.name + " Got Water!");
            move();
            synchronized (food) {
                System.err.println(this.name + " Got Food!");
            }
        }
    }

    public void move() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}

public class DeadLockTest {

    public static void main(String[] args) {
        var foxy = new Fox("Foxy");
        var tails = new Fox("Tails");
        var food = new Food();
        var water = new Water();

        var service = Executors.newScheduledThreadPool(10);
        try {
            service.submit(() -> foxy.eatAndDrink(food, water));
            service.submit(() -> tails.drinkAndEat(food, water));
        } catch (Exception e) {
        } finally {
            service.shutdown();
        }
    }
}