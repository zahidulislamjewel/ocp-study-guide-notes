public class Parent {
    void print() {
        System.out.println("I'm from Parent Class: " + this.getClass().getName());
    }
}

class Child1 extends Parent {
    void print() {
        System.out.println("I'm from Child1 Class: " + this.getClass().getName());
    }
}

class Child2 extends Parent {
    void print() {
        System.out.println("I'm from Child2 Class: " + this.getClass().getName());
    }
}

class HierarchyTest {
    public static void main(String[] args) {
        Parent parent = new Parent();
        Parent parentChild1 = new Child1();
        Parent parentChild2 = new Child2();

        Child1 child21 = new Child1();
        Child2 child22 = new Child2();

        Child1 child1 = (Child1) parentChild1;
        child1.print();
        // Child1 child1 = (Child1) parent;    // Does not compile

        if(child21 instanceof Parent p) {
            p.print();
        }

        if(parent instanceof Child1 c) {
            c.print();
        }
    }
}