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


## Encapsulating Data with Records

A POJO, which stands for Plain Old Java Object, is a class used to model and pass data around, often with few or no complex methods.

JavaBean is POJO that has some additional rules applied.

Encapsulation is a way to protect class members by restricting access to them. In Java, it is commonly implemented by declaring all instance variables private. Callers are required to use methods to retrieve or modify instance variables.

Encapsulation is about protecting a class from unexpected use. 

The immutable objects pattern is an object-oriented design pattern in which an object cannot be modified after it is created. 
Instead of modifying an immutable object, you create a new object that contains any properties from the original object you want copied over.
setters must be omitted for a class to be immutable. 
Example,
```java
public record Crane(int numberEggs, String name) {}

// usage
var mommy = new Crane(4, "Cammy");
System.out.println(mommy.numberEggs());  // 4
System.out.println(mommy.name());        // Cammy
```

`record` is a special type of data-oriented class in which the compiler inserts boilerplate code itself.

**Members Automatically Added to Records**
1. Constructor: A constructor with the parameters in the same order as the record declaration
2. Accessor method: One accessor for each field
3. equals(): A method to compare two elements that returns true if each field is equal in terms of equals()
4. hashCode(): A consistent hashCode() method using all of the fields
5. toString(): A toString() implementation that prints each field of the record in a convenient, easy-to-read format
   
The `println()` method always calls the `toString()` method automatically on any object passed to it.

An empty but valid record example,
```java
public record Crane() {}
```

**Declaring Constructors**
Record can have two types of constructor
1. Long Constructor (traditional class constructor that compiler inserts automatically)
2. Compact Constructor

**The Long Constructor**
The constructor the compiler normally inserts automatically, which is referred as the long constructor.
The compiler will not insert a constructor if we define one with the same list of parameters in the same order. 
Since each in record is field is final, the constructor must set every field.
Example,
```java
public record Crane(int numberEggs, String name) {
    public Crane(int numberEggs, String name) {
        if (numberEggs < 0) throw new IllegalArgumentException();
        this.numberEggs = numberEggs;
        this.name = name;
    }
}
```

**Compact Constructors**
A compact constructor is a special type of constructor used for records to process validation and transformations succinctly. 
It takes no parameters and implicitly sets all fields. 
A compact constructor is declared without parentheses.
Java executes the full constructor after the compact constructor. 

Example,
```java
public record Crane(int numberEggs, String name) {
    public Crane {
        if (numberEggs < 0) throw new IllegalArgumentException();
        name = name.toUpperCase();  // refers to the input parameters, not instance members
    }
}
```

We cannot this reference inside the compact constructor, thus cannot assign and mutate the instace field.
We can only access the input paratemeters and mutate them, before the compiler sets the instance fields with them using the compiler generated long constructor at the end of the compact constructor.

**Transforming Parameters**
Compact constructors give use the opportunity to apply transformations to any of the input values (not the instance fields, no `this` reference).
While compact constructors can modify the constructor parameters, they cannot modify the fields of the record. 
Example,
```java
public record Crane(int numberEggs, String name) {
    public Crane {
        this.numberEggs = 10;  // DOES NOT COMPILE
    }
}
```
Removing the `this` reference allows the code to compile, as the constructor parameter is modified instead.

It is highly recommended to stick with the compact constructor form unless we have a good reason not to.

**Overloaded Constructors**
We can also create overloaded constructors that take a completely different list of parameters.
They are more closely related to the long-form constructor and don’t use any of the syntactical features of compact constructors.
The first line of an overloaded constructor must be an explicit call to another constructor via `this()`.
If there are no other constructors, the long constructor must be called.
Unlike compact constructors, we can only transform the data on the first line. 
After the first line, all of the fields will already be assigned, and the object is immutable.
So, modifying the variables after the first line will have no effect at all.
Example,
```java
public record Crane(int numberEggs, String name) {
    public Crane(String firstName, String lastName) {
        this(0, firstName + " " + lastName);    // Using the compiler generated long constructor with this(int, String)
        numberEggs = 10; // NO EFFECT (applies to parameter, not instance field)
    }
}
```

Only the long constructor, with fields that match the record declaration, supports setting field values with a this reference. Compact and overloaded constructors do not.
Tha means, the overloaded default long constructor provided by compiler (exact same signature) supports setting value with `this(..)` referece.

**Understanding Record Immutability**
Records don’t have setters. Every field is inherently final and cannot be modified after it has been written in the constructor. 
Just as interfaces are implicitly abstract, records are also implicitly final. The final modifier is optional but assumed.

The following two declarations are equivalent,
```java
public record Crane(int numberEggs, String name) {}

// is equivalent to
public final record Crane(int numberEggs, String name) {}
```

Like enums, a record cannot be extended or inherited.
Also like enums, a record can implement a regular or sealed interface, provided it implements all of the abstract methods.
```java
public interface Bird {}

public record Crane(int numberEggs, String name) implements Bird {}
```

While instance members of a record are final, the static members are not required to be.

**Using Pattern Matching with Records**
Records have been updated to support pattern matching. 
The new feature is really about the members of the record, rather than the record itself.
Example,
```java
record Monkey(String name, int age) {}

Object animal = new Monkey("George", 3);

// Since Java 17
if(animal instanceof Monkey m) {
    // access m.name() and m.age() with the reference variable
}

// Since Java 21 (Unwraping or Unpacking)
if(animal instanceof Monkey(String name, int age)) {
    // access name and age directory without record reference
}
```

**Pattern Matching Rules for Records**
1. If any field declared in the record is included, then all fields must be included.
2. The order of fields must be the same as in the record.
3. The names of the fields do not have to match.
4. At compile time, the type of the field must be compatible with the type declared in the record.
5. The pattern may not match at runtime if the record supports elements of various types.

We can name the record or its fields, but not both.
Numeric promotion is not supported in record pattern matching. 

**Matching Records**
Pattern matching for records include matching both the type of the record and the type of each field.

**Nesting Record Patterns**
If a record includes other record values as members, then you can optionally pattern match the fields within the record. 
Example,
```java
record Bear(String name, int age) {}
record Couple(Bear a, Bear b) {}

var c = new Couple(new Bear("Yogi", 3), new Bear("Fozzie", 2));

if(c instanceof Couple(Bear a, Bear b)) {
    // access a.name(), b.name()
}

if(c instanceof Couple(Bear a, Bear b)) {
    // access a.name(), a.age(), b.name(), b.age()
}

if(c instanceof Couple(Bear a, Bear(String name, int age))) {
    // access a.name(), a.age(), acess 2nd bear name and age directly
}

if(c instanceof Couple(Bear(String name, int age), Bear(String name, int age))) {
    // DOES NOT COMPILE due to variable name ambiguity
}

if(c instanceof Couple(Bear(String name1, int age1), Bear(String name2, int age1))) {
    // access name1, age1, name2, age2
}

if (c instanceof Couple(var a, var b)) {
    // acess a.name(), a.age(), b.name(), b.age()
}
```

**Matching Records with var and Generics**
`var` keyword can be used in a pattern matching record.
When `var` is used for one of the elements of the record, the compiler assumes the type to be the exact match for the type in the record.
Pattern matching generics within records follow the similar rules for overloading generic methods.

The diamond operator (`<>`) cannot be used for pattern matching (nor overloading generics).

**Applying Pattern Matching Records to Switch**
We can use switch with pattern matching and records. The rules are the same as classes, sealed-classes, enums etc.
`default` clause is not required if all types are covered in the pattern matching expression.
Example,
```java
record Snake(Object data) {}

var x = switch(Snake snake) {
    case Snake(Long hiss)       -> hiss + 1;
    case Snake(Integer nagina)  -> nagina + 10;
    case Snake(Number crowley)  -> crowley.intValue() + 10;
    case Snake(Object other)    -> -1;
};
```

**Customizing Records**
Records actually support many of the same features as a class.
Here are some of the members that records can include:
- Overloaded and compact constructors
- Instance methods including overriding any provided methods (accessors, equals(), hashCode(), toString())
- Nested classes, interfaces, annotations, enums, and records

Notes:
We can add methods, static fields, and other data types. 
We cannot add instance fields outside the record declaration, even if they are private and final. 
Doing so defeats the purpose of using a record and could break immutability.
Example,
```java
public record Crane(int numberEggs, String name) {
    private static int TYPE = 10;               // Compiles fine, static ann static final field can be added
    public int size;                            // DOES NOT COMPILE, adding new instance field prohibited
    private final boolean friendly = true;      // DOES NOT COMPILE, adding new instance field prohibited even if they are final
}
```

Records also do not support instance initializers. 
All initialization for the fields of a record must happen in a constructor. 
Records do support static initializers, though.
Example,
```java
public record Crane(int numberEggs, String name) {
    static { System.out.print("Hello Bird!"); } // Compiles fine, static initializer
    { System.out.print("Goodbye Bird!"); }      // DOES NOT COMPILE, instance initializer
    { this.name = "Big"; }                      // DOES NOT COMPILE, instance initializer
}
```

**Creating Nested Classes**
A nested class is a class that is defined within another class. 
A nested class can come in one of four flavors, with all supporting instance and static variables as members.
1. Inner class: A non-static type defined at the member level of a class
2. Static nested class: A static type defined at the member level of a class
3. Local class: A class defined within a method body
4. Anonymous class: A special case of a local class that does not have a name


**Benefits of Nested Classes**
- Improved encapsulation: Helper classes can be hidden inside the enclosing class and restricted from external use.
- Clear usage intent: Nesting shows the class is meant to be used in only one place.
- Better organization: Closely related classes stay together, improving structure.
- Cleaner, more readable code: When used properly, nesting reduces clutter and makes code easier to understand.

When used improperly, though, nested classes can sometimes make the code harder to read.
They also tend to tightly couple the enclosing and inner class.

> Nested classes should be avoided if they need independent use, as they tightly couple with the enclosing class.

Nested class also includes nested interfaces, enums, records and annotations.
They are also called inner classes.

**Declaring an Inner Class**
An inner class, also called a member inner class, is a non-static type defined at the member level of a class (the same level as the methods, instance variables, and constructors).
Because they are not top-level types, they can use any of the four access levels, not just public and package access.
Inner classes have the following properties:
- Can be declared public, protected, package, or private
- Can extend a class and implement interfaces
- Can be marked abstract or final
- Can access members of the outer class, including private members

**Referencing Members of an Inner Class**
Outer class instance variables can be referenced from the inner class using the syntax `ClassName.this.variableName`
Inner classes can be instantiated using the syntax `OuterClass.InnerClass reference = new OuterClass().new InnerClass();`

**Creating a static Nested Class**
A static nested class is a static type defined at the member level. 
Unlike an inner class, a static nested class can be instantiated without an instance of the enclosing class. 
The trade-off, though, is that it can’t access instance variables or methods declared in the outer class.

Static Nested Class is like a top-level class except for the following:
1. The nesting creates a namespace because the enclosing class name must be used to refer to it.
2. It can additionally be marked private or protected.
3. The enclosing class can refer to the fields and methods of the static nested class.

Example,
```java
public class Park {
    public static class Ride {
        // Code
    }
}
// Instantiating outside of the class
Park.Ride ride = new Park.Ride();
```

**Nested Records are Implicitly static**
Nested records are similar as static inner class. They can be instantiated in the same way as static inner class.

**Writing a Local Class**
A local class is a nested class defined within a method. 
Like local variables, a local class declaration does not exist until the method is invoked, and it goes out of scope when the method returns.
This means we can create instances only from within the method. 
Those instances can still be returned from the method. 
Local classes are not limited to being declared only inside methods. 
They can be declared inside constructors and initializers. 

Local classes have the following properties:
- Do not have an access modifier.
- Can be declared final or abstract.
- Can include instance and static members.
- Have access to all fields and methods of the enclosing class (when defined in an instance method).
- Can access final and effectively final local variables.

**Defining an Anonymous Class**
An anonymous class is a specialized form of a local class that does not have a name. 
It is declared and instantiated all in one statement using the new keyword, a type name with parentheses, and a set of braces {}. 
Anonymous classes must extend an existing class or implement an existing interface.

Interfaces require abstract methods to be public. 
Anonymous class is just an unnamed local class.
An anonymous class cannot both implement an interface and extend a class.
An anonymous class can also be defined outside a method body. 
An valid anonymous class defined in class level,
```java
public class Gorilla {
    interface Climb {}
    Climb climbing = new Climb() {};
}
```

**Anonymous Classes and Lambda Expressions**
Since the introduction of lambda expressions, anonymous classes are now often replaced with much shorter implementations.

Anonymous class can access local variables of instance method, if the variables are final or effectively final (just like local class and lambda expression).