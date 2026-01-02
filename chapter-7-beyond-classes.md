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
- interface methods are public and abstract implicitly (inserted by compiler), so private or protected raise conflict
- interface variables are public, static and final implicitly (inserted by compiler),  so private or protected raise conflict
```java
public interface Dance {
   private int count = 4;   // DOES NOT COMPILE
   protected void step();   // DOES NOT COMPILE
}
``` 
**Interfaces vs. Abstract Classes**
Abstract classes and interfaces both are considered abstract types.
Only interfaces make use of implicit modifiers.
If access level not declared in classes (abstract or concreted), the access level is considered default package level
If access level not declared in interfaces, the access level is considered public always
Interfaces do not support protected members, as a class cannot extend an interface. 
They also do not support package access members.

```java
abstract class Husky {      // abstract modifier required
    abstract void play();   // abstract modifier required
}
 
interface Poodle {          // abstract modifier optional
    void play();            // abstract modifier optional
}

public class Webby extends Husky {
    // override declares the access modifier default, same as the parent.
    void play() {}          // play() is declared with package access in Husky
}
 
public class Georgette implements Poodle {
    // override reduces the access modifier on from public to package access.
    void play() {}       // DOES NOT COMPILE - play() is public in Poodle
}
```

**Writing a `default` Interface Method**
A default method is a method defined in an interface with the default keyword and includes a method body. 
It may be optionally overridden by a class implementing the interface.

One use of default methods is for backward compatibility. You can add a new default method to an interface without the need to modify all of the existing classes that implement the interface.
Rules for declaring default methods:
1. A default method may be declared only within an interface.
2. A default method must be marked with the default keyword and include a method body.
3. A default method is implicitly public.
4. A default method cannot be marked abstract, final, or static.
5. If a class inherits two or more default methods with the same method signature, then the class must override the method.

**Inheriting Duplicate default Methods**
If the class implementing the interfaces overrides the duplicate default method, the code compiles without issue, and the ambiguity problem is solved. By overriding the conflicting method, the ambiguity about which version of the method to call can be removed. 

**Calling a default Method**
A default method exists on any object inheriting the interface, not on the interface itself. 
A default method should be treated like an inherited method that can be optionally overridden, rather than as a static method. 

**Declaring static Interface Methods**
Static Interface Method Definition Rules:
1. A static method must be marked with the static keyword and include a method body.
2. A static method without an access modifier is implicitly public.
3. A static method cannot be marked abstract or final.
4. A static method is not inherited and cannot be accessed in a class implementing the interface without a reference to the interface name.

`private` access modifier can be used with static interface methods.

**Private Interface Methods**
Interface can have `private` and `private static` interface methods. 
Because both types of methods are private, they can only be used in the interface declaration in which they are declared.
Private methods exist to remove duplication inside default / static methods, not to be used by implementing classes.

**Private Interface Method Definition Rules**
1. A private interface method must be marked with the private modifier and include a method body.
2. A private static interface method may be called by any method within the interface definition.
3. A private interface method may only be called by default and other private non-static methods within the interface definition.

A private interface method is only accessible to non-static methods defined within the interface.
A private static interface method, on the other hand, can be accessed by any method in the interface. 
For both types of private methods, a class inheriting the interface cannot directly invoke them.

Notes:
- Treat abstract, default, and non-static private methods as belonging to an instance of the interface.
- Treat static methods and variables as belonging to the interface class object.
- All private interface method types are only accessible within the interface declaration.