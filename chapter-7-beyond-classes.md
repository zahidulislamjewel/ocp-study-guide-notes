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

## Working with Enums
An enumeration, or enum for short, is like a fixed set of constants.
Using an enum is much better than using a bunch of constants because it provides type-safe checking. 
With numeric or String constants, you can pass an invalid value and not find out until runtime. 
With enums, it is impossible to create an invalid enum value without introducing a compiler error.
Enumerations show up whenever you have a set of items whose types are known at compile time.

- Enum values are considered constants and are commonly written using snake_case.
- Enums can be compared using `==` because they are like `static final` constants.
- `equals()` or `==` both works when comparing enums, since each enum value is initialized only once in the Java Virtual Machine (JVM).
- Enums cannot be extended by other enums or classes.
- Because, the vale in an enum is fixed. More value cannot be added by extending the enum.
- Enum themselves can implement interface.
- Enum cannot be marked final (nor required to, they are implicity final).
- Each enum value is created once when the enum is first loaded. Once the enum has been loaded, it retrieves the single enum value with the matching name.

**Using Enums in switch Statements**
enums can be used in switch statements and expressions. Enums have the unique property that they do not require a default branch for an exhaustive switch if all enum values are handled.
A default branch can also be added but is not required, so long as all values are handled.

Example,
```java
enum Season { SPRING, SUMMER, FALL, WINTER; }

String getWeather(Season value) {
   return switch (value) {
      case SUMMER        -> "Too hot";
      case Season.WINTER -> "Too cold";
      case SPRING, FALL  -> "Just right";         
   };
}
```

**Creating Enum Variables**
An enum declaration can include both static and instance variables.

Although it is possible to create an enum with instance variables that can be modified, it is a very poor practice to do so since they are shared within the JVM. 
When designing enum values, they should be immutable.

**Declaring Enum Constructors**
All enum constructors are implicitly private, with the modifier being optional.
Enum cannot be extended, and enum constructors can be called only within the enum itself.

**Writing Enum Methods**
Enum can contain statice and instance methods.
Enum can implement an interface and override the abstract method.
The enum itself can contain an abstract method. This means that each and every enum value (enum instance) is required to implement this method.

Just because an enum can have lots of methods doesn’t mean that it should. Try to keep your enums simple. If your enum is more than a screen length or two, it is probably too long, and you probably need a class instead.

## Sealing Classes
A sealed class is a class that restricts which other classes may extend it.
A class that limits the direct subclasses to a fixed set of classes.

**Declaring a Sealed Class**

Example,
```java
public sealed class Bear permits Kodiak, Panda {}

public final class Kodiak extends Bear {}

public non-sealed class Panda extends Bear {}
```

Sealed Class Keywords:
- `sealed`: Indicates that a class or interface may only be extended/implemented by named classes or interfaces
- `permits`: Used with the sealed keyword to list the classes and interfaces allowed
- `non-sealed`: Applied to a class or interface that extends a sealed class, indicating that it can be extended by unspecified classes

Another example,
```java
// concrete sealed class
public class sealed Frog permits GlassFrog {}   // DOES NOT COMPILE, wrong order of keyword usage

public final class GlassFrog extends Frog {}

// abstract sealed class 
public abstract sealed class Mammal permits Wolf {}

public final class Wolf extends Mammal {}

public final class Tiger extends Mammal {}      // DOES NOT COMPILE, final class cannot be extended
```

Sealed classes are commonly declared with the abstract modifier, although this is certainly not required.

**Sealed class rules:**
1. `sealed` class needs to be declared (and compiled) in the same package as its direct subclasses. And the subclasses must each extend the sealed class. 
2. named modules allow sealed classes and their direct subclasses in different packages, provided they are in the same named module.
3. Every class that directly extends a sealed class must specify exactly one of the following three modifiers: final, sealed, or non-sealed.

**Creating `final` Subclasses**
Example,
```java
public sealed class Antelope permits Gazelle {}
 
public final class Gazelle extends Antelope {}
 
public class DamaGazelle extends Gazelle {}  // DOES NOT COMPILE
```

**Creating `sealed` Subclasses**
Example,
```java
public sealed class Fish permits ClownFish {}
 
public sealed class ClownFish extends Fish permits OrangeClownFish {}
 
public final class OrangeClownFish extends ClownFish {}
```

**Creating `non-sealed` Subclasses**
Example,
```java
abstract sealed class Mammal permits Feline {}

non-sealed class Feline extends Mammal {}

class Tiger extends Feline {}

class BengalTiger extends Tiger {}
```

**Omitting the `permits` Clause**
To omit the `permits` clause, the declarations of the sealed class and it's direct subclasses must be in the same file.
Example,
```java
// Snake.java
public sealed class Snake {}    // permits <class list> are resolved to the direct subclasses

final class Cobra extends Snake {}

non-sealed class Python extends Snake {}
```

The `permits` clause can also be omitted if the subclasses are nested.
Example,
```java
public sealed class Snake {
    final class Cobra extends Snake {}
}
```
But, if `permits` clause is used, then the nested subclass requires a reference to the `sealed` class namespace 
Example,
```java
public sealed class Snake permits Cobra {   // DOES NOT COMPILE
    final class Cobra extends Snake {}
}

public sealed class Snake permits Snake.Cobra { // Compiles fine
    final class Cobra extends Snake {}
}
```

So, if all the subclasses are nested, it is recommended to omit the `permits` clause.

**Sealing Interfaces**
`sealed` interface must appear in the same package or named module as the classes or interfaces that directly extend or implement it.
One distinct feature of a sealed interface is that the `permits` list can apply to a class that implements the interface or an interface that extends the interface.
Example,
```java
// Sealed interface
public sealed interface Swims permits Duck, Swan, Floats {}
 
// Classes permitted to implement sealed interface
public final class Duck implements Swims {}
public final class Swan implements Swims {}
 
// Interface permitted to extend sealed interface
public non-sealed interface Floats extends Swims {}
```

interfaces are implicitly abstract and cannot be marked final. For this reason, interfaces that extend a sealed interface can only be marked sealed or non-sealed. They cannot be marked final.

**Applying Pattern Matching to a Sealed Class**
`sealed` classes can be treated like an enum (exhaustive, no default clause) in a switch by applying pattern matching. 
Example,
```java
abstract sealed class Fish permits Trout, Bass {}
final class Trout extends Fish {}
final class Bass extends Fish {}

public String getType(Fish fish) {
    return switch (fish) {
        case Trout t -> "Trout!";
        case Bass b -> "Bass!";
    };
}
```

The above code only works because Fish is `abstract` and `sealed`, and all possible subclasses are handled. If we remove the `abstract` modifier in the Fish declaration, then the switch expression would not compile.
Like enums, make sure that if a switch uses a sealed class with pattern matching that all possible types are covered or a default clause is included.
If we remove `abstract` modifier and still want our switch expression to work, then we would need to add the case for Fish class itself, at the end of the switch expression to make it exhaustive.

**Sealed Class Rules**
1. Sealed classes are declared with the sealed and permits modifiers.
2. Sealed classes must be declared in the same package or named module as their direct subclasses.
3. Direct subclasses of sealed classes must be marked final, sealed, or non-sealed. For interfaces that extend a sealed interface, only sealed and non-sealed modifiers are permitted.
4. The permits clause is optional if the sealed class and its direct subclasses are declared within the same file or the subclasses are nested within the sealed class.
5. Interfaces can be sealed to limit the classes that implement them or the interfaces that extend them.