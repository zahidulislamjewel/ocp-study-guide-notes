

import java.util.Arrays;

public class ArrayTest {
    public static void main(String[] args) {
        String[] bugs = { "cricket", "beetle", "ladybug" };
        String bugString = String.join(", ", bugs);
        System.out.println(bugs);
        System.out.println(Arrays.toString(bugs));
        System.out.println(bugString);
        String[] alias = bugs;
        String[] anotherArray = bugString.split(", ");
        System.out.println(bugs.equals(alias));
        System.out.println(bugs == alias);
        System.out.println(bugs.equals(anotherArray));
        System.out.println(bugs == anotherArray);
        System.out.println(Arrays.equals(bugs, anotherArray));

        System.out.println("=".repeat(100));
        String[] strings = { "apple", "banana", "cherry", "date" };
        Object[] objects = strings; // Autoboxing to Object[]
        String[] backToStrings = (String[]) objects; // Downcasting back to String[]
        System.out.println(Arrays.toString(backToStrings));
        // The following line would throw ArrayStoreException at runtime
        // objects[0] = new StringBuilder();

        System.out.println("=".repeat(100));
        int[] nums = { 4, 9, 2, 3, 5, 7, 8, 1, 6, 0 };
        System.out.println("Before sorting: " + Arrays.toString(nums));
        Arrays.sort(nums);
        System.out.println("After sorting: " + Arrays.toString(nums));
        System.out.println("=".repeat(100));
        System.out.println(Arrays.binarySearch(nums, 5)); // Should return the index of 5
        System.out.println(Arrays.binarySearch(nums, 10)); // Should return a negative value
        System.out.println("=".repeat(100));

        int[] arr1 = { 1, 2, 3 };
        int[] arr2 = { 1, 2, 3 };
        int[] arr3 = { 1, 2, 6 };
        System.out.println(Arrays.compare(arr1, arr2)); // 0
        System.out.println(Arrays.compare(arr1, arr3)); // negative value
        System.out.println(Arrays.compare(arr3, arr1)); // positive value
        System.out.println(Arrays.compare(null, arr1)); // negative value
        System.out.println(Arrays.mismatch(arr1, arr2)); // -1
        System.out.println(Arrays.mismatch(arr1, arr3)); // 2
        System.out.println("=".repeat(100));
        processArray("apple", "banana", "cherry");
        processArray("dog", "elephant");
        System.out.println("=".repeat(100));
        System.out.println("# Asymmetric Arrays #");
        String[][] asymmetricArray = {
                { "A1", "A2", "A3" },
                { "B1", "B2" },
                { "C1", "C2", "C3", "C4" }
        };
        for(var row: asymmetricArray) {
            for(var num: row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
        int[][] numArray = new int[3][];
        numArray[0] = new int[]{4, 9};   
        numArray[1] = new int[]{2, 3, 5};
        numArray[2] = new int[]{1, 6, 7, 8};

        for(var row: numArray) {
            for(var num: row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }   
    }

    public static void processArray(String... args) {
        int n = args.length;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int count = 0;
        for (String arg : args) {
            if (count == n - 1) {
                sb.append(arg).append("]");
            } else {
                sb.append(arg).append(", ");
            }
            count++;
        }
        System.out.println(sb.toString());
    }
}
