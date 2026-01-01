# OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER:

# Using Object-Oriented Concepts in Java

- Declare and instantiate Java objects including nested class objects, and explain the object life-cycle including creation, reassigning references, and garbage collection.
- Create classes and records, and define and use instance and static fields and methods, constructors, and instance and static initializers.
- Understand variable scopes, apply encapsulation, and create immutable objects. Use local variable type inference.
- Implement inheritance, including abstract and sealed types as well as record classes. 
- Override methods, including that of the Object class. 
- Implement polymorphism and differentiate between object type and reference type. 
- Perform reference type casting, identify object types using the instanceof operator, and pattern matching with the instanceof operator and the switch construct.
- Create and use interfaces, identify functional interfaces, and utilize private, static, and default interface methods.
- Create and use enum types with fields, methods, and constructors.

A top-level type can only be declared with public or package access.

**Annotations to Know for the Exam**<br>
Another top-level type available in Java is annotations, which are metadata “tags” that can be applied to classes, types, methods, and even variables.

`@Deprecated` lets other developers know that a feature is no longer supported and may be removed in future releases. 
`@SuppressWarnings` instructs the compiler to ignore notifying the user of any warnings generated within a section of code.
`@SafeVarargs` lets other developers know that a method does not perform any potential unsafe operations on its vararg parameters.

## Implementing Interfaces
An interface is an abstract data type that declares a list of abstract methods that any class implementing the interface must provide.
A class may implement any number of interfaces. 

**Declaring and Using an Interface**
Interface variables are referred to as constants because they are assumed to be `public`, `static`, and `final`. 
They are initialized with a constant value when they are declared.
Since they are `public` and `static`, they can be used outside the interface declaration without requiring an instance of the interface.
One aspect of an interface declaration that differs from an abstract class is that it contains implicit modifiers.
An implicit modifier is a modifier that the compiler automatically inserts into the code. 
An interface is always considered to be abstract, even if it is not marked so!
An interfaces cannot be marked as final for the same reason that abstract classes cannot be marked as final. 
Marking an interface final implies no class could ever implement it. 
An interface cannot be instantiated.
An instance of a class that implements the interface can be instantiated.
Example,
```java
public interface Climb {
    Number getSpeed(int age);
}
```

**Extending an Interface**
An interface can extend another interface using the `extends` keyword.
An interface can extend multiple interfaces.
Extending multiple interfaces by a class is permitted because interfaces are not initialized as part of a class hierarchy. 
Unlike abstract classes, interfaces do not contain constructors and are not part of instance initialization.
Interfaces simply define a set of rules and methods that a class implementing them must follow.

**Inheriting an Interface**
Like an abstract class, when a concrete class inherits an interface, all of the inherited abstract methods must be implemented.

**Mixing Class and Interface Keywords**
A class can implement an interface, a class cannot extend an interface. 
An interface can extend another interface, an interface cannot implement another interface. 

**Inheriting Duplicate Abstract Methods**
Java supports inheriting two abstract methods that have compatible method declarations.
Compatible means a method can be written that properly overrides both inherited methods

Example,
```java
public interface Herbivore { public int eatPlants(int plantsLeft); }
 
public interface Omnivore { public int eatPlants(int foodRemaining); }

public class Bear implements Herbivore, Omnivore {
    @Override
    public int eatPlants(int plants) { return plants - 1; } 
}
```

**Inserting Implicit Modifiers**
An implicit modifier is one that the compiler will automatically insert.
Implicit Modifiers,
- Interfaces are implicitly abstract.
- Interface variables are implicitly public, static, and final.
- Interface methods without a body are implicitly abstract.
- Interface methods without the private modifier are implicitly public.

Example of two equivalent inferace declaration,
```java
// Implicit modifiers
public interface Soar {
    int MAX_HEIGHT = 10;
    final static boolean UNDERWATER = true;
    void fly(int speed);
    abstract void takeoff();
    public abstract double dive();
}

// Equivalent, explicit modifiers
public abstract interface Soar {
    public static final int MAX_HEIGHT = 10;
    public final static boolean UNDERWATER = true;
    public abstract void fly(int speed);
    public abstract void takeoff();
    public abstract double dive();
}
```

**Conflicting Modifiers**
```java
// interface methods are public and abstract implicitly, so conflict
// interface variables are public, static and final implicitly, so conflict
public interface Dance {
   private int count = 4;  // DOES NOT COMPILE
   protected void step();  // DOES NOT COMPILE
}
```