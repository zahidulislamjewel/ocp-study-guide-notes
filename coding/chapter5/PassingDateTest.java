public class PassingDateTest {
    public static void main(String[] args) {
        String name = "Webby";
        speak(name);
        System.out.println(name);
        System.out.println("=".repeat(100));

        Dog carol = new Dog("Cargol");
        System.out.println(carol);
        System.out.println(carol.hashCode());
        bark(carol);
        System.out.println(carol);
        System.out.println(carol.hashCode());
        growl(carol);
        System.out.println(carol);
        System.out.println(carol.hashCode());

    }

    public static void speak(String name) {
        name = "Georgette";
    }

    public static void bark(Dog dog) {
        // here this do variable is a copy of the passed variable, not the original one
        // as it points to the original, it can change the variable of the original object
        dog.name = "Carolina";
        System.out.println("Woof! Woof!");
    }

    public static void growl(Dog dog) {
        // here this do variable is a copy of the passed variable, not the original one
        // as it doesn't point to the original, rather points to a new object, all the changes to the new dog is local to it.
        // this doesn't apply to the original dog
        dog = new Dog("Mike");
        System.out.println(dog);
        System.out.println(dog.hashCode());
        System.out.println("Grrr! Grrr!");
    }

    public static class Dog {
        private String name;

        public Dog(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Dog [name=" + name + "]";
        }
    }
}
