public class Park {
    public static class Ride {
        private int price = 6;
    }

    public static void main(String[] args) {
        var ride = new Ride();
        System.out.println(ride.price);
    }
}

class ParkTest {
    public static void main(String[] args) {
        Park.Ride ride = new Park.Ride();
        var anotherRide = new Park.Ride();
    }
}