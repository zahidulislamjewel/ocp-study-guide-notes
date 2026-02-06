# OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER:

## Using Object-Oriented Concepts in Java
- Create classes and records, and define and use instance and static fields and methods, constructors, and instance and static initializers.
- Understand variable scopes, apply encapsulation, and create immutable objects. 
- Use local variable type inference.
- Implement inheritance, including abstract and sealed types as well as record classes. 
- Override methods, including that of the Object class. 
- Implement polymorphism and differentiate between object type and reference type. 
- Perform reference type casting, identify object types using the instanceof operator, and pattern matching with the instanceof operator and the switch construct.


## Understanding Inheritance

**Inheritance is transitive**

- If `X extends Y` and `Y extends Z`, then `X` is a subclass (descendant) of `Z`.
- Likewise, `Z` is a superclass (ancestor) of `X`.
- `X` is a direct subclass of `Y`
- `X` is an indirect subclass of `Z`
- Direct subclass/descendant means the class explicitly extends another class.


**Inheritance and Member Access**

- Public members are always inherited and accessible.
- Protected members are inherited and accessible in subclasses.
- Package-private members are inherited only if the subclass is in the same package.
- Private members are never inherited and are accessible only within the defining class.
- A superclass may have private members, but subclasses cannot access them directly.

**Class Modifiers**
- `final`: The class may not be extended.
- `abstract`: The class is abstract, may contain abstract methods, and requires a concrete subclass to instantiate.
- `sealed`: The class may only be extended by a specific list of classes.
- `non-sealed`: A subclass of a sealed class permits potentially unnamed subclasses.
- `static`: Used for static nested classes defined within a class.

**Single vs. Multiple Inheritance**

- Java supports single inheritance (one direct parent class).
- Java allows multiple levels of inheritance.
- A class can have any number of ancestors (direct or transitive), gaining access to inherited members.
- Java does not support multiple inheritance of classes to avoid complexity and maintenance issues caused by conflicting inherited behavior.
- Multiple inheritance can lead to complex, often difficult-to-maintain data models.
- Part of what makes multiple inheritance complicated is determining which parent to inherit values from in case of a conflict.
- Java avoids these issues by disallowing multiple inheritance altogether.

**Inheriting Object**

- In Java, every class inherits from `java.lang.Object`.
- `Object` is the only class with no parent.
- If a class does not explicitly extend another class, the compiler automatically adds `extends Object`.
- These two declarations are equivalent:

  ```java
  class Zoo { }
  class Zoo extends java.lang.Object { }
  ```
- Because of this, all classes have access to methods defined in `Object`, such as `toString()` and `equals()`.
- If a class extends another class, Java does not add `extends Object` explicitly, since the parent already inherits from `Object`.

## Creating Classes

**Applying Class Access Modifiers**
- `private` members are never inherited, and package members are only inherited if the two classes are in the same package.
- A top-level class is one not defined inside another class.
- A `.java` file can have at most one public top-level class.
- Trying to declare a top-level class with `protected` or `private` class will lead to a compiler error.

**Accessing the `this` Reference**

- The `this` reference refers to the current instance of the class.
- `this` can be used to access any member of the class, including inherited members.
- `this` can be used in any instance method, constructor, or instance initializer block. 
- `this` cannot be used when there is no implicit instance of the class, such as in a static method or static initializer block. 

**Calling the super Reference**
Properties are not overridden, they are hidden (called field hiding, cause they are resolved at compile time).
Only instance methods can be overridden, static methdos are hidden (called method hiding, cause they are resolved at compile time)

**Variable resolution order in Java**

When a variable is referenced in a class method, Java resolves it in the following order:
1. Local variables: variables declared inside the method or as method parameters.
2. Local variable shadowing: If a method parameter or local variable has the same name as a field, the local variable takes precedence.
3. Instance variables of the current class (`this`): fields declared in the current class.
4. Inherited instance variables from the superclass (`super`): accessible parent class fields.
5. Static variables (class-level) if referenced statically: less common in instance methods, usually accessed via ClassName.field.

Hierarchy:
Local variable (method scope) > this.field (current class field) > super.field (parent class field) > Static fields (optional)

Notes:
- Fields are not polymorphic. The field accessed depends on reference in the class (`this` or `super`)
- Methods are polymorphic. `this.method()` resolves at runtime
- Local variables always take precedence over fields

Declaring a variable with the same name as an inherited variable is referred to as hiding a variable.
`this` includes current and inherited members, `super` only includes inherited members.

**Declaring Constructors**
A constructor is a special method that matches the name of the class and has no return type. It is called when a new instance of the class is created. 

**Constructor Parameter**
Like method parameters, constructor parameters can be any valid class, array, or primitive type, including generics, but may not include var.
Example,
```java
public class Bonobo {
        public Bonobo(var food) {  // DOES NOT COMPILE
    }
}
```

**Constructor Overloading**
A class can have multiple constructors, as long as each constructor has a unique constructor signature. 
That means the constructor parameters must be distinct. Like methods with the same name but different signatures, declaring multiple constructors with different signatures is referred to as constructor overloading. 

**Instantiation**
Constructors are used when creating a new object. This process is called instantiation because it creates a new instance of the class. A constructor is called when we write new followed by the name of the class we want to instantiate. 

**The Default Constructor**
The default constructor is added any time a class is declared without any constructors. This constructor is often called default no-argument constructor.

User defined no-arg constructor and the default constructor are not same, they are similar though.

They are similar but not the same. Let’s clarify carefully:

- Default constructor automatically provided by the compiler if no constructor is defined in the class.
- Default constructor simply calls the superclass no-arg constructor.

```java
class Animal {
    // no constructor defined
}

// Compiler automatically creates:
class Animal {
    Animal() {       // default constructor
        super();     // call Object() by default
    }
}
```

User-defined no-arg constructor can have any code inside it, not just `super()`.

```java
class Animal {
    Animal() {       // user-defined no-arg constructor
        System.out.println("Animal created!");
    }
}
```

Notes:
- Default constructor: invisible, automatically inserted, just calls `super()`.
- User-defined no-arg constructor: visible, written by you, can do extra work.

**Private Constructor**
Having only private constructors in a class tells the compiler not to provide a default no-argument constructor. 
It also prevents other classes from instantiating the class. 
This is useful when a class has only static methods or the developer wants to have full control of all calls to create new instances of the class.

**Calling Overloaded Constructors with `this()`**

**`this` vs. `this()`**
- `this` refers to an instance of the class. 
- `this()` refers to a constructor call within the class.

Calling `this()` has one special rule: the `this()` call must be the first statement in the constructor. The side effect of this is that there can be only one call to `this()` in any constructor.

Some invalid call to `this()`
```java
// infinite call to the self constructor
public class Gopher {
    public Gopher(int dugHoles) {
        this(5);    // DOES NOT COMPILE
    }
}

// infinite call to one another constructor, cycle
public class Gopher {
    public Gopher() {
        this(5);    // DOES NOT COMPILE
    }
    public Gopher(int dugHoles) {
        this();     // DOES NOT COMPILE
    }
}
```

**Constructor Rules**
- A class can contain many overloaded constructors, provided the signature for each is distinct.
- The compiler inserts a default no-argument constructor if no constructors are declared.
- If a constructor calls this(), then it must be the first line of the constructor.
- Java does not allow cyclic constructor calls.

**Calling Parent Constructors with `super()`**

calling constructors in the parent class`
The first statement of every constructor is a call to a parent constructor using `super()` or another overloaded constructor in the current class using `this()`.

**`super` vs. `super()`**

- `super` is used to reference members of the parent class.
- `super()` calls a parent constructor.

Like calling `this()`, calling `super()` can only be used as the first statement of the constructor.
`super()` can only be called once as the first statement of the constructor.

If the parent class has more than one constructor, the child class may use any valid and accessible parent constructor in its definition.

**Default Constructor Confusions**

- If a parent class defines any constructor, the compiler does not generate a default constructor for that parent.
- If a subclass declares no constructors, or declares a constructor without an explicit `super(...)` or `this()`, the compiler inserts `super()` automatically.
- If the parent does not have a no-arg constructor, this causes a compile-time error.

Example, the following code doesn't compile because there are implicit call to the non-existent `super()`, which the parent `Mammal` class doesn't define
```java
// As it defines a constructor
// Compiler doesn't insert no-arg constructor
public class Mammal {
    public Mammal(int age) {}
}

// DOES NOT COMPILE
// The compiler will insert a default no-argument constructor
// Which calls the non-existed parent default no-arg constructor
// It shold define it's own and call the super()
public class Seal extends Mammal {
    // no constructor defined, compiler inserts one
    // compiler inserts no-arg constructor with `super()` call in it
    // but the `super()` no-arg constructor doesn't exists
}  

// DOES NOT COMPILE
// Compiler doesn't insert any default constructor, as it has one
// And, it doesn't see any call to the super() or this()
public class Elephant extends Mammal {
    // compiler inserts `super()` call in the defined no-arg constructor
    // but the `super()` no-arg constructor doesn't exists
    public Elephant() {}              
}
```

The following explicit call to the parent class constructor via `super(...)` fixes the issue,
```java
public class Seal extends Mammal {
    public Seal() {
        super(6);  // Explicit call to parent constructor
    }
}
 
public class Elephant extends Mammal {
    public Elephant() {
        super(4);  // Explicit call to parent constructor
    }
}
```
Subclasses may include no-argument constructors even if their parent classes do not.
And, the following compiles too, because the parent includes a no-argument constructor
```java
class Mammal {}
class Elephant extends Mammal {}
class AfricanElephant extends Elephant {
    AfricanElephant() {
        super();  // calls Elephant(), not Mammal()
    }
}
```

> “If the parent has no no-arg constructor, the child must call `super(...)` explicitly — the compiler will not save you.”

**`super()` Always Refers to the Most Direct Parent**
- A class may have multiple ancestors via inheritance.
- `super()` always refers to the most direct parent.

Notes:
- If a constructor calls super() or this(), then it must be the first line of the constructor.
- If the constructor does not contain a this() or super() reference, then the compiler automatically inserts super() with no arguments as the first line of the constructor.

## Initializing Objects

**Order of initialization**

- Class initialization runs static fields and static blocks.
- Class initialization happens top-down through the class hierarchy (superclass first).
- The JVM decides when initialization occurs (before first use).
- A class may be initialized when: the program starts, or a static member is accessed, or an instance is about to be created.
- Each class is initialized at most once.
- A class is never initialized if it is never used.

**Initialize Class X** (Not Object / Instance Initialization)
1. Initialize the superclass of X.
2. Process all static variable declarations in the order in which they appear in the class.
3. Process all static initializers in the order in which they appear in the class.

**The rules for when classes are loaded are determined by the JVM at runtime.**
**class containing the program entry point, aka the main() method, is loaded before the main() method is executed.**

**Initializing final Fields**

Instance and class variables are assigned a default value based on their type if no value is specified. 
A default value is only applied to a non-final field, though.

`static final` variables must be explicitly assigned a value exactly once. Either during declration or inside static initialize (static block). 
Class fields marked `final` follow similar rules. They can be assigned values in the line in which they are declared or in an instance initializer.
Unlike `static` class members, though, final instance fields can also be set in a constructor. The constructor is part of the initialization process, so it is allowed to assign final instance variables.
**One important rule:** by the time the constructor completes, all final instance variables must be assigned a value exactly once.
Unlike local final variables, which are not required to have a value unless they are actually used, final instance variables must be assigned a value.
If they are not assigned a value when they are declared or in an instance initializer, then they must be assigned a value in the constructor declaration. 
In terms of assigning values, each constructor is reviewed individually (considering each constructor stanalone, independent of one another)

Exam Notes:
> On the exam, be wary of any instance variables marked final. Make sure they are assigned a value in the line where they are declared, in an instance initializer, or in a constructor. They should be assigned a value only once, and failure to assign a value is considered a compiler error.

**Initializing Instances**

The first line of every constructor is a call to `this()` or `super()`, and if omitted, the compiler will automatically insert a call to the parent no-argument constructor `super()`.

**Initialize Instance of X** (After the initialization of Class from Inheritance Chain Top to Bottom)
1. Initialize Class X if it has not been previously initialized.
2. Initialize the superclass instance of X.
3. Process all instance variable declarations in the order in which they appear in the class.
4. Process all instance initializers in the order in which they appear in the class.
5. Initialize the constructor, including any overloaded constructors referenced with this().

**Order of Initilization:**
1st, Class initialization (static fields/blocks), top-down inheritance chain
2nd, main() execution (after its class is initialized; no inheritance order)
3rd, Object initialization on `new`, top-down inheritance chain

> Static > main > instance; inheritance order applies only to static and instance initialization, not to main.

**Important rules to know for the exam:**
- A class is initialized at most once by the JVM before it is referenced or used.
- All `static final` variables must be assigned a value exactly once, either when they are declared or in a static initializer.
- All `final` fields must be assigned a value exactly once, either when they are declared, in an instance initializer, or in a constructor.
- Non-final static and instance variables defined without a value are assigned a default value based on their type. (finals are not!)
- The order of initialization is as follows: variable declarations, then initializers, and finally constructors.

**Initialization Order Summary**

**1. Class initialization (static)**
- Happens once per class
- Follows top-down inheritance order
- Triggered when a class is first used, a static member is accessed, or `main()` is about to run
- Includes static fields and static initializer blocks

**2. `main()` execution**
- JVM loads and initializes the class containing `main()` first
- `main()` executes like a normal static method
- No inheritance order applies to `main()` execution

**3. Object initialization (instance)**
- Triggered by `new`
- Happens every time an object is created
- Follows top-down inheritance order
- Order: superclass instance fields/blocks > superclass constructor > subclass instance fields/blocks > subclass constructor

## Inheriting Members

The ability of an object to take on many different forms as polymorphism.

**Overriding a Method**
Overriding a method occurs when a subclass declares a new implementation for an inherited method with the same signature and compatible return type.
Method signature is composed of the name of the method and method parameters. It does not include the return type, access modifiers, optional specifiers, or any declared exceptions.

Method overriding infinite calls can lead to `StackOverflowError` 

To override a method, you must follow a number of rules. The compiler performs the following checks when you override a method:
1. The method in the child class must have the same signature as the method in the parent class.
2. The method in the child class must be at least as accessible as the method in the parent class. (equal or more accessible)
3. The method in the child class may not declare a checked exception that is new or broader than the class of any exception declared in the parent class method.
4. If the method returns a value, it must be the same or a subtype of the method in the parent class, known as covariant return types.

---

> **Explanation of Point 2:** <br>
> When a child class overrides a method, the overridden method must be at least as accessible as the parent’s version.
> This rule exists to preserve polymorphism.
> If code is written using a parent reference, it must be able to safely call methods regardless of whether the actual object is a parent or a child.
> If a child could reduce access, the method call could suddenly become illegal at runtime — which Java does not allow.
> “An overriding method must not be less accessible than the method it overrides.”
> This guarantees: If the method is callable via the parent reference, it is always callable on the child object
> No ambiguity, no surprises.
> “Overriding can only keep or widen visibility, never narrow it.”

---

> **Explanation of Point 3:** <br>
> When a child class overrides a method, it cannot introduce new checked exceptions or broader checked exceptions than the parent method declares.
> Because code written against the parent type must remain safe when a child object is used (polymorphism). <br>
> **Key idea to remember:** <br>
> If the parent method promises:
> “I will only throw these checked exceptions (or none)”
> then the child method cannot break that promise.
> One implication of this rule is that overridden methods are free to declare any number of new unchecked exceptions.
> If a parent method declares no checked exceptions, the child (overriding) method cannot declare any checked exceptions.
> if the return type of the overriding method is the same as or a subtype of the return type of the inherited method, that's called co-variant return type.
> This rule applies to primitive types and object types alike. If one of the return types is void, then they both must be void, as nothing is covariant with void except itself.

Example 1: Legal overriding (narrower exception)
```java
class Parent {
    void read() throws IOException {
    }
}

class Child extends Parent {
    @Override
    void read() throws FileNotFoundException {
    }
}
```
This compiles because:
- FileNotFoundException is a subclass of IOException
- The child is throwing a narrower checked exception

Example 2: Illegal overriding (broader exception)
```java
class Parent {
    void read() throws FileNotFoundException {
    }
}

class Child extends Parent {
    @Override
    void read() throws IOException {
    }
}
```
This does NOT compile because:
- IOException is broader than FileNotFoundException
- The child is declaring a wider checked exception

Example 3: Illegal overriding (new checked exception)
```java
class Parent {
    void read() {
    }
}

class Child extends Parent {
    @Override
    void read() throws IOException {
    }
}
```
This does NOT compile because:
- Parent declares no checked exceptions
- Child introduces a new checked exception

Example 4: Unchecked exceptions are allowed
```java
class Parent {
    void read() {
    }
}

class Child extends Parent {
    @Override
    void read() throws RuntimeException {
    }
}
```
This compiles because:
- RuntimeException is unchecked
- The rule applies only to checked exceptions

Conclusion: <br>
Overriding methods can throw fewer or narrower checked exceptions, but never more or broader ones.

---

> **Explanation of Point 4:** <br>
> When overriding a method that returns a value, the child method must return: the same type, or a subtype of the parent’s return type
> This rule also exits because of polymorphism.
> The compiler trusts the parent’s return type.
> So whatever the child returns must still be usable where the parent’s return type is expected.
> A subtype is safe. A broader type is not.

Example,
```java
class Document { }

class Printer {
    Document print() {
        return new Document();
    }
}

class PdfDocument extends Document { }

class PdfPrinter extends Printer {
    @Override
    PdfDocument print() {
        return new PdfDocument();   // PdfDocument is a co-variant type (subtype or same type) of Document, so valid
    }
}

// Usage
Printer p = new PdfPrinter();
Document d = p.print();   // This is valid, due to polymorphic behavior, p.print() returns PdfDocument which is a subtype of Document
```

**Notes**
If two methods have the same name but different signatures, the methods are overloaded, not overridden. 
Overloaded methods are considered independent and do not share the same polymorphic properties as overridden methods.
Overloading differs from overriding in that overloaded methods use a different parameter list. 
Overridden methods have the same signature and a lot more rules than overloaded methods.

**Annotation**
An annotation is a metadata tag that provides additional information about your code.
When the method is incorrectly overridden, the `@Override` annotation can prevent from making a mistake.

**Redeclaring private Methods**
- Private methods cannot be overridden because they are not inherited.
- A child class can define a new method with the same name and signature; this is a separate, independent method.
- The new method is not subject to overriding rules (access, return type, exceptions).
- If the parent method were public, the child method could not use a more restrictive access or incompatible return type due to overriding being in action.
 
Example:
```java
class Beetle {
    private String getSize() { return "Undefined"; }
}

class RhinocerosBeetle extends Beetle {
    private int getSize() { return 5; } // valid, new method
}
```

**Hiding Static Methods**
A `static` method cannot be overridden because class objects do not inherit from each other in the same way as instance objects. 
On the other hand, they can be hidden. 
A hidden method occurs when a child class defines a `static` method with the same name and signature as an inherited `static` method defined in a parent class. 
Method hiding is similar to but not exactly the same as method overriding. 
The previous four rules for overriding a method must be followed when a method is hidden. In addition, a new fifth rule is added for hiding a method. All the rules combined,

Rules (Overriding 4 + Method Hiding 1)
1. The method in the child class must have the same signature as the method in the parent class.
2. The method in the child class must be at least as accessible as the method in the parent class. 
3. The method in the child class may not declare a checked exception that is new or broader than the class of any exception declared in the parent class method.
4. If the method returns a value, it must be the same or a subtype of the method in the parent class, known as covariant return types.
5. The method defined in the child class must be marked as static if it is marked as static in a parent class.

Put simply, it is method hiding if the two methods are marked `static` and method overriding if they are not marked `static`. 
If one is marked `static` and the other is not, the class will not compile.

Example,
```java
class Bear {
    public static void sneeze() { System.out.println("Bear is sneezing"); }

    public void hibernate() { System.out.println("Bear is hibernating"); }

    public static void laugh() { System.out.println("Bear is laughing"); }

    public static void eat() { System.out.println("Bear is eating"); }
}

class SunBear extends Bear {
    // Doesn't compile. This instance method cannot override the static method from Bear
    public void sneeze() { System.out.println("Sun Bear is sneezing"); }

    // Doesn't compile. This static method cannot hide the instance method from Bear
    public static void hibernate() { System.out.println("Sun Bear is hibernating"); }

    // Doesn't compile. Cannot reduce the visibility of the inherited method from Bear
    protected static void laugh() { System.out.println("Sun Bear is laughing"); }

    public static void eat() { System.out.println("Sun Bear is eating"); }

    public static void main(String[] args) {
        eat();  // SunBear class static method eat() hides Bear class static method eat()
    }
}
```

**Hiding Variables**
Java doesn’t allow variables to be overridden. Variables can be hidden, though.
A hidden variable occurs when a child class defines a variable with the same name as an inherited variable defined in the parent class. 
This creates two distinct copies of the variable within an instance of the child class: one instance defined in the parent class and one defined in the child class.
Example,
```java
class Carnivore {
    protected boolean hasFur = false;
}

public class Meerkat extends Carnivore {
    protected boolean hasFur = true;

    public static void main(String[] args) {
        Meerkat meerkat = new Meerkat();
        Carnivore carnivore = meerkat;

        // The output changes depending on the reference variable used.
        // This is resolved at compiled time
        System.out.println(meerkat.hasFur);     // true, calls the child class boolean (method hiding)
        System.out.println(carnivore.hasFur);   // false, calls the parent class boolean
    }
}
```

**Key Points**
> Only instance method overriding is runtime (dynamic).
> Static method hiding and variable hiding are compile-time (static).

1. Instance method overriding (baseline for comparison)
- Resolved at runtime (dynamic method dispatch)
- Based on the actual object, not the reference type
- Uses polymorphism

2. Static method hiding
- Static methods are not overridden; they are hidden.
- Resolved at compile time
- Based on the reference type, not the object
- No dynamic dispatch
- Polymorphism does not apply
- Static methods belong to the class, not the object
- The compiler decides which method to call using the reference type

3. Variable hiding (instance variables)
- Instance variables are never overridden.
- Always resolved at compile time
- Based on the reference type
- No polymorphism
- Works the same way as static method hiding

4. Variable hiding (static variables)
- Static variables behave exactly like static methods.
- Resolved at compile time
- Based on reference type
- No runtime dispatch
- No polymorphism

**Writing final Methods**
`final` methods cannot be overridden. 
By marking a method `final`, you forbid a child class from replacing this method. 
This rule is in place both when you override a method and when you hide a method. 
In other words, you cannot hide a static method in a child class if it is marked final in the parent class.

## Creating Abstract Classes
An abstract class is a class declared with the `abstract` modifier that cannot be instantiated directly and may contain abstract methods.

An abstract class can contain abstract methods. 
An abstract method is a method declared with the `abstract` modifier that does not define a body. 
An abstract method forces subclasses to override the method.
By declaring a method abstract, we can guarantee that some version will be available on an instance without having to specify what that version is in the abstract parent class.

**Abstract Class Rules**
1. Only instance methods can be marked abstract within a class, not variables, constructors, or static methods.
2. An abstract class can include zero or more abstract methods, while a non-abstract class cannot contain any.
3. A non-abstract class that extends an abstract class must implement all inherited abstract methods.
4. Overriding an abstract method follows the existing rules for overriding methods.

Variables cannot be marked abstract.
An abstract class is most commonly used when you want another class to inherit properties of a particular class, but you want the subclass to fill in some of the implementation details.
An abstract class is one that cannot be instantiated. 
An abstract class may include non-abstract methods.
An abstract class can include all of the same members as a non-abstract class, including variables, static and instance methods, constructors, etc.
An abstract class is not required to include any abstract methods at all.
Like the final modifier, the abstract modifier can be placed before or after the access modifier in class and method declarations.
The abstract modifier cannot be placed after the class keyword in a class declaration or after the return type in a method declaration.

**Declaring Abstract Methods**
An abstrct method cannot be marked final. They are opposite of each other.

- An abstract method can exist only inside an abstract class.
- A non-abstract class cannot contain abstract methods.
- An abstract class cannot be instantiated, even if it has no abstract methods.
- The abstract modifier can appear before or after the access modifier.
- The abstract modifier cannot appear after the class name or after the return type.
- An abstract method cannot have a body.
- A method with a body cannot be marked abstract.
- Abstract methods cannot be final (they are meant to be overridden).

**Creating a Concrete Class**
An abstract class becomes usable when it is extended by a concrete subclass. 
A concrete class is a non-abstract class.
The first concrete subclass that extends an abstract class is required to implement all inherited abstract methods.
This includes implementing any inherited abstract methods from inherited interfaces.

- Core rule: the first concrete (non-abstract) subclass in an inheritance chain must implement all inherited abstract methods.
- Abstract intermediate classes may implement some abstract methods; any left unimplemented must be implemented later by the first concrete subclass.
- If you change an intermediate class from abstract to concrete, it must implement every abstract method inherited from its ancestors or it will not compile.
- Quick checklist: before making a class non-abstract, ensure it implements every abstract method from its ancestors.

**Creating Constructors in Abstract Classes**
Abstract classes can have constructor.
Even though abstract classes cannot be instantiated, they are still initialized through constructors by their subclasses.
Abstract classes are initialized with constructors in the same way as non-abstract classes.
If an abstract class does not provide a constructor, the compiler will automatically insert a default no-argument constructor.
The primary difference between a constructor in an abstract class and a non-abstract class is that a constructor in an abstract class can be called only when it is being initialized by a non-abstract subclass. 
This makes sense, as abstract classes cannot be instantiated.

Notes:
- Instantiation = creating an object
- Initialization = running constructors and initializing fields
- Abstract classes cannot be instantiated, but they are initialized as part of subclass instantiation

**Spotting Invalid Declarations**

**`abstract` and `final` Modifiers**
abstract and final Modifiers in a same class or method are condtracdictory.
These concepts are in direct conflict with each other.
Example,
```java
public abstract final class Tortoise {      // DOES NOT COMPILE
    public abstract final void walk();      // DOES NOT COMPILE
}
```

**`abstract` and `private` Modifiers**
A method cannot be marked as both abstract and private.
private methods are not inherited by subclass. They cannot be.
Example,
```java
public abstract class Whale {
   private abstract void sing();        // DOES NOT COMPILE
}

public class HumpbackWhale extends Whale {
    private void sing() {
        System.out.println("Humpback whale is singing");
    } 
}
```
While it is not possible to declare a method `abstract` and `private`, it is possible (albeit redundant) to declare a method `final` and `private`.

**`abstract` and `static` Modifiers**
A static method can only be hidden, not overridden. 
It is defined as belonging to the class, not an instance of the class. 
If a static method cannot be overridden, then it follows that it also cannot be marked abstract since it can never be implemented. 
Example,
```java
abstract class Hippopotamus {
    abstract static void swim();  // DOES NOT COMPILE
}
```

## Creating Immutable Objects
an immutable object is one that cannot change state after it is created.
The immutable objects pattern is an object-oriented design pattern in which an object cannot be modified after it is created.

Immutable objects are helpful when writing secure code because you don’t have to worry about the values changing. 
They also simplify code when dealing with concurrency since immutable objects can be easily shared between multiple threads.

**Declaring an Immutable Class**
Common strategy for making a class immutable:
1. Mark the class as final or make all of the constructors private.
2. Mark all the instance variables private and final.
3. Don’t define any setter methods.
4. Allow getters that returns depensive copy (return a copy of the mutable object any time it is requested).
5. Don’t allow referenced mutable objects to be modified.
6. Use a constructor to set all properties of the object, making a copy if needed (create object with defensive copy of user provided mutable arguments).

**Copy on Read Accessor Methods**
Of course, changes in the copy won’t be reflected in the original, but at least the original is protected from external changes. 
This can be an expensive operation if called frequently by the caller.
Example,
```java
public ArrayList<String> getFavoriteFoods() {
    return new ArrayList<String>(this.favoriteFoods);
}
```

**Performing a Defensive Copy**
Example, creating object with user provided list, which maskes this object mutable
```java
public final class Animal {  // Not an immutable object declaration
    private final ArrayList<String> favoriteFoods;
 
    public Animal(ArrayList<String> favoriteFoods) {
        Object.requireNonNull(favoriteFoods);
        this.favoriteFoods = favoriteFoods;
    }
}
```

Example, creating object with a defensive copy of user provided list, which maskes this object immutable
```java
public final class Animal {  // Not an immutable object declaration
    private final ArrayList<String> favoriteFoods;
 
    public Animal(ArrayList<String> favoriteFoods) {
        Object.requireNonNull(favoriteFoods);
        this.favoriteFoods = List.copyOf(favoriteFoods);;
    }
}
```

Returning a defensive copy through getters,
Example,
```java
public ArrayList<String> getFavoriteFoods() {
    return List.copyOf(this.favoriteFoods);
}
```

The copy operation is called a defensive copy because the copy is being made in case other code does something unexpected.
It’s the same idea as defensive driving: prevent a problem before it exists. With this approach, our Animal class is once again immutable.