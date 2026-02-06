
public class Home {
    private String greetingMessage = "Hi";

    // Nested or Inner class
    protected class Room {
        private int repeat = 3;

        public void enter() {
            for (int i = 0; i < repeat; i++) {
                greet(greetingMessage);
            }
        }

        private void greet(String greetingMessage) {
            System.out.println(greetingMessage);
        }
    }

    public void enterRoom() {
        var room = new Room();
        room.enter();
    }

    public static void main(String[] args) {
        var home = new Home();
        home.enterRoom();
        System.out.println("=".repeat(100));
        var anotherHome = new Home();
        Room anotherRoom = anotherHome.new Room();
        anotherRoom.enter();
        System.out.println("=".repeat(100));
        var roomFromHome = new Home().new Room();
        roomFromHome.enter();
        System.out.println("=".repeat(100));
        new Home().new Room().enter();
        (new Home().new Room()).enter();
    }
}
