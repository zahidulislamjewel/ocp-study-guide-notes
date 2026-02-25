public class ExecutionOrder {

    // 1. Static Variable
    static String staticVar = initializeStatic("1. Static Variable");

    // 2. Static Block
    static {
        System.out.println("2. Static Block");
    }

    // 4. Instance Variable
    String instanceVar = initializeInstance("4. Instance Variable");

    // 5. Instance Initializer (Anonymous block)
    {
        System.out.println("5. Instance Initializer");
    }

    // 6. Constructor
    ExecutionOrder() {
        System.out.println("6. Constructor");
    }

    public static void main(String[] args) {
        // 3. Main Starts
        System.out.println("3. Main Starts");
        
        // Triggering the instance creation
        new ExecutionOrder();
    }

    // Helper methods to show when variables are assigned
    static String initializeStatic(String msg) {
        System.out.println(msg);
        return "Done";
    }

    String initializeInstance(String msg) {
        System.out.println(msg);
        return "Done";
    }
}