package dog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class Animal {
}

class Dog extends Animal {
}

class Puppy extends Dog {
}

public class Main {
    public static void main(String[] args) {
        printInheritanceTree(Puppy.class);
        System.out.println("=".repeat(100));
        printInheritanceTree(LinkedList.class);
        System.out.println("=".repeat(100));
        printInheritanceTree(LinkedHashMap.class);
        System.out.println("=".repeat(100));
        printInheritanceTree(BigDecimal.class);
        System.out.println("=".repeat(100));
        printInheritanceTree(LocalDate.class);
        System.out.println("=".repeat(100));
        printInheritanceTree(String.class);
        System.out.println("=".repeat(100));
        printInheritanceTree(StringBuilder.class);
        System.out.println("=".repeat(100));
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
