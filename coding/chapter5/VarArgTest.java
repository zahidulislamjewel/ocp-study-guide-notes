public class VarArgTest {
    public static void main(String[] args) {
        walk(1, new int[] { 1, 2, 3 });
        walk(1, 1, 2, 3);
        walk(1);
        walk(1, null); // triggers NullPointerException in the method if tried to access
    }

    public static void walk(int start, int... steps) {
        System.out.println("Starting walk at step: " + start);
        for (var step : steps) {
            System.out.println("Walking step: " + step);
        }
        System.out.println("Total steps: " + steps.length);
        System.out.println("=".repeat(100));
    }
}
