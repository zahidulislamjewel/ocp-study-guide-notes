public class Gorilla {
    public void rest(Long x) {
        System.out.print("long");
    }

    public static void main(String[] args) {
        var g = new Gorilla();
        // g.rest(8);                   // DOES NOT COMPILE
        g.rest(8L);                  // DOES COMPILE
        g.rest(Long.valueOf(8));     // DOES COMPILE
    }
}