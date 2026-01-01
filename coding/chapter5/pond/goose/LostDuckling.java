package pond.goose;

import pond.duck.DuckTeacher;

public class LostDuckling {
    public void swim() {
        var teacher = new DuckTeacher();
        teacher.swim();                                 // Accessing the public method of DuckTeacher
        System.out.println("Thanks " + teacher.name);   // Accessing the public field of DuckTeacher
    }
}
