package packageb;
import packagea.ClassA;

public class ClassB {
    public static void main(String[] args) {
        ClassA a = new ClassA();
        System.out.println("Got it");
        System.out.println(a.getClass().getName());
    }
}