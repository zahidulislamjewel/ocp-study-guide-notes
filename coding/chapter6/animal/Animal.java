package animal;

public class Animal {
    private int age;
    private String name;

    public Animal(int age, String name) {
        super();
        this.age = age;
        this.name = name;
    }

    public Animal(int age) {
        super();
        this.age = age;
        this.name = null;
    }

    public Animal(String name) {
        super();
        this.name = name;
        this.age = 0;
    }
}

class Zebra extends Animal {
    public Zebra(int age) {
        super(age);
    } 

    public Zebra() {
        this(0);
    }
}

class Gorilla extends Animal {
    public Gorilla(int age) {
        super(age, "Gorilla");
    } 

    public Gorilla() {
        super(0);
    }
}


