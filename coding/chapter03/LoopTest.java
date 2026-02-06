

public class LoopTest {
    public static void main(String[] args) {
        int[][] myComplexArray = { { 5, 2, 1, 3 }, { 3, 9, 8, 9 }, { 5, 7, 12, 7 } };

        OUTER_LOOP: for (int[] mySimpleArray : myComplexArray) {
            INNER_LOOP: for (int num : mySimpleArray) {
                System.out.print(num + "\t");
                if (num > 10) {
                    break OUTER_LOOP;
                }
                if (num > 20) {
                    break INNER_LOOP;
                }
            }
            System.out.println();
        }
        System.out.println("-------------------");

        CLEANING: for (char stables = 'a'; stables <= 'd'; stables++) {
            for (int leopard = 1; leopard <= 3; leopard++) {
                if (stables == 'b' || leopard == 2) {
                    continue CLEANING;
                }
                System.out.println("Cleaning: " + stables + ", " + leopard);
            }
        }
    }
}
