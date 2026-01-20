import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ClassHierarchy {
    public static void main(String[] args) {
        
        printInheritanceTree(ConcurrentHashMap.class);
        System.out.println("=".repeat(100));
        printInheritanceTree(Integer.class);
    }

    static void printInheritanceTree(Class<?> clazz) {
        // First, build the inheritance chain in reverse order
        List<Class<?>> chain = new ArrayList<>();
        Class<?> current = clazz;
        while (current != null) {
            chain.add(0, current); // insert at the beginning
            current = current.getSuperclass();
        }

        // Now print the chain with pipes
        for (int i = 0; i < chain.size(); i++) {
            Class<?> c = chain.get(i);
            System.out.println(c.getSimpleName());
            if (i < chain.size() - 1) {
                System.out.println("\s|");
            }
        }
    }
}