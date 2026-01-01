# OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER:

## Using Object-Oriented Concepts in Java

- Create classes and records, and define and use instance and static fields and methods, constructors, and instance and static initializers.
- Implement overloaded methods, including var-arg methods.

**Designing Methods**

- Two of the parts—the method name and parameter list—are called the method signature. 
- The method signature provides instructions for how callers can reference this method. 
- The method signature does not include the return type and access modifiers, which control where the method can be referenced.

**Parts of a Method**
1. Access Modifiers: `private`, package / package-private / default, `protected`, `public`
2. Optional Specifiers: `static`, `abstract`, `final`, `default`, `synchronized`, `native`, `strictfp`
3. Return Type
4. Method Name
5. Parameter List
6. Method Signature (Method Name + Parameter List)
7. Exception List
8. Method Body

While access modifiers and optional specifiers can appear in any order, they must all appear before the return type. In practice, it is common to list the access modifier first.

once the return type is specified, the rest of the parts of the method are written in a specific order:
*access modifiers > (optional specifiers) > return type > method name > parameter list > (optional exception list) > method body.*

Java allows the optional specifiers to appear before the access modifier.

Return type must appear after any access modifiers or optional specifiers and before the method name. 
A method must have a return type. If no value is returned, the void keyword must be used. Return type cannot be omitted.

Method names begin with a lowercase letter by convention, but they are not required to. 

A method signature, composed of the method name and parameter list, is what Java uses to uniquely determine exactly which method you are attempting to call. Once it determines which method you are trying to call, it then determines if the call is allowed. 

The names of the parameters in the method signature are not used as part of a method signature. The parameter list is about the types of parameters and their order.

A method body is simply a code block. It has braces that contain zero or more Java statements.

**Declaring Local and Instance Variables**

- Local variables are those defined within a method or block. 
- Instance variables are those that are defined as a member of a class.

All local variable references are destroyed after the block is executed, but the objects they point to may still be accessible.
That means, the lifetime of a location variable is limitied within it's surrounding block. But, a local variable can reference an object of another class. So, if we are able to save / create a reference of that object outside of that method (inside calling method), then we might be able access that object, if that's alive yet.

**Local Variable Modifiers**
There’s only one modifier that can be applied to a local variable: `final`.

We don’t need to assign a value when a final variable is declared. The rule is only that it must be assigned a value before it can be used /  consumed. We can even use var (inference) and final together.

The compiler does not allow the use of local variables that may not have been assigned a value. An unassigned local variable doesn't have any default value unlike the instance or class variable. So, an unassigned local variable cannot be accessd / consumed, and doing so will throw compilation error.

**Notes on Local `final` variable**

- `final` is the only modifier allowed for local variables
- A `final` local variable does not need to be initialized at declaration
- A local variable must be assigned before it is used
- Local variables do not have default values (unlike instance/static variables)
- A `final` local variable can be assigned only once
- The compiler checks all code paths to ensure definite assignment
- `final` can be used with `var` for local variable type inference
- A final local variable does not need to be initialized at declaration
- A final local variable must be assigned exactly once
- A final local variable must be assigned before use

Example,

```java
void finalLocalProcess(String[] args) {

    // final local variable assigned later
    final int x;
    x = 10;
    System.out.println(x);

    // final with var
    final var y = "Java";
    System.out.println(y);

    // local variable must be assigned before use
    int z;
    // System.out.println(z); // DOES NOT COMPILE

    // definite assignment in all branches
    final int a;
    if (args.length > 0) {
        a = 5;
    } else {
        a = 10;
    }
    System.out.println(a);

    // not assigned in all paths
    final int b;
    if (args.length > 0) {
        b = 100;
    }
    // System.out.println(b); // DOES NOT COMPILE
}
```

> Local variables must be definitely assigned before use.
> final means assign once, not necessarily at declaration.

Using the `final` modifier doesn't mean we can’t modify the data. The final attribute refers only to the variable reference; the contents can be freely modified (assuming the object isn’t immutable). Example,
```java
public void zooAnimalCheckup() {
    final int rest = 5;
    final Animal giraffe = new Animal();
    final int[] friends = new int[5];
 
    giraffe.setName("George");  // Compiles fine
    friends[2] = 2;             // Compiles fine
    giraffe = null;             // DOES NOT COMPILE
}
```

**Effectively Final Variables**
An effectively final local variable is one that is not modified after it is assigned. This means that the value of a variable doesn’t change after it is set, regardless of whether it is explicitly marked as final. 

A quick test of effectively final is to just add final to the variable declaration and see if it still compiles. 

- A local variable is effectively final if its value is assigned exactly once
- The variable does not need to be declared `final`
- After assignment, the variable must never be reassigned
- Modification includes: 1. Reassigning the variable 2. Assigning it in more than one execution path
- The compiler determines effective finality at compile time
- A variable that is conditionally assigned can still be effectively final if all paths assign it exactly once
- A variable loses effective finality immediately after any reassignment
- Effectively final variables can be used in: 1.Lambdas 2. Anonymous classes
- Instance and static variables are not subject to effective-final rules (local variables only)

**What “effectively final” really means**
A local variable is effectively final if the compiler can prove that the variable’s value is assigned exactly once and never changed afterward.
If a variable is assigned to the same value in more than one execution path, then it is effectively final. 
If a variable is assigned to different values in more than one execution path, then it is not effectively final. 

Example,
```java
void processEffectivelyFinal(boolean flag) {

    // Effectively final: assigned once
    int a = 10;
    System.out.println(a);

    // Not effectively final: reassigned
    int b = 5;
    b = 8; // reassignment, thus not effectively final

    // Effectively final: assigned once in all paths
    // Compiler's perspective int c = 1
    int c;
    if (flag) {
        c = 1;
    } else {
        c = 1;
    }
    System.out.println(c);

    // Not effectively final: assigned in multiple paths
    // Compiler's perspective int d = 1 or 2 (conditionally reassigned)
    int d;
    if (flag) {
        d = 1;
    } else {
        d = 2; // different assignment, thus not effectively final
    }

    // Effectively final usage in lambda
    int x = 100;
    Runnable r = () -> {
        System.out.println(x); // OK: x is effectively final
    };

    // x = 200; // would break effective finality, leading to compilation error above
}
```

> Effectively final = assigned once, never changed.
> Looks final to the compiler, even without `final`.

Effective finality is about whether the value is conceptually constant, not about how many times the assignment statement appears.
- If same value in all paths, then effectively final
- If different values in different paths, then not effectively final

If the compiler cannot prove the value never changes, the variable is not effectively final — even if it’s assigned only once at runtime.

The decision is made entirely at compile time, not at runtime. The Java compiler performs static code analysis. It examines all possible execution paths. It determines whether a local variable’s value:
- is assigned exactly once, and
- is never changed afterward

Method and constructor parameters are local variables that have been pre-initialized.
In the context of local variables, the same rules around final and effectively final apply. This is especially important in the context of “Lambdas and Functional Interfaces”. Local classes and lambda expressions declared within a method can only reference local variables that are final or effectively final.

**Why the rule exists (important concept)**

Local variables:
- Live on the stack
- Are destroyed when the method ends

Local classes, anonymous classes, and lambdas:
- May outlive the method excution
- May execute later or in another thread

To avoid inconsistent state, Java captures a copy, not the variable itself. Therefore:
- The variable must not change
- Hence: final or effectively final

Parameters are local variables (pre-initialized). Method and constructor parameters:
- Are treated as local variables
- Are automatically assigned when the method is called
- If not reassigned, they effectively final

```java
// Here the method parameter is treated as local variable and effectively final
// as it is not reassigned inside the method
void test(int x) {
    Runnable r = () -> System.out.println(x);
}
```

**Instance Variable Modifier**
Like methods, instance variables can have different access levels, such as `private`, package, `protected`, and `public`. 
Instance variables can also use optional specifiers like `final`, `volatile`, `transient`.

`final` instance variable modifier specifies that the instance variable must be initialized with each instance of the class exactly once.
`volatile` instance variable modifier instructs the JVM that the value in this variable may be modified by other threads.
`transient` instance variable modifier is used to indicate that an instance variable should not be serialized with the class.

If an instance variable is marked `final`, then it must be assigned a value when it is declared (field level) or when the object is instantiated (constructor or instance initializer). 
Like a local `final` variable, it cannot be assigned a value more than once, though.
Instance variables receive default values based on their type when not set.
The compiler does not apply a default value to final instance / static variables, though. 
A final instance or final static variable must receive a value when it is declared or as part of initialization.

**Working with Varargs**

**Rules for Creating a Method with a Varargs Parameter**
1. A method can have at most one varargs parameter.
2. If a method contains a varargs parameter, it must be the last parameter in the list.

Example,
```java
public void walk(int start, int... steps) {
    for(var step: steps) {
        // process step
    }
}

// calling
walk(1, new int[] {1, 2, 3});
walk(1, 1, 2, 3);
walk(1);
```

Accessing a varargs parameter is just like accessing an array. It uses array indexing. 

**Applying Acess Modifiers**
There are four access levels: private, package, protected, and public access (from most restrictive to least restrictive):
- private: Only accessible within the same class.
- Package access: private plus other members of the same package. Sometimes referred to as package-private or default access.
- protected: Package access plus access within subclasses.
- public: protected plus classes in the other packages.

**Protected Access**
A subclass that has access to any protected or public members of the parent class.
`protected` also gives us access to everything that package access does. This means a class in the same package as Bird can access its protected members.

The `protected` rules apply under two scenarios:
- A member is used without referring to a variable (using inheritance).
- A member is used through a variable (using object reference of subclass). If it is a subclass, protected access is allowed. This works for references to the same class or a subclass.
- We are not allowed to refer to members of the super class if we are not in the same package.

Golden Mental Mode: 
> protected = package access OR subclass access (but only through inheritance)

Access Modifiers – Memory Ladder (from strict to open): 
private  >  package (default)  >  protected  >  public


One-Line Rules to Memorize
- `private`: Only inside the same class
- package (default): Same package only
- `protected`: Same package OR subclass (even in another package)
- `public`: Everywhere

*A subclass in another package can access a protected member only through inheritance, not through a reference variable of the superclass.*

protected behaves like package access… until inheritance is involved.
Protected = package + inheritance

**Accessing `static` Data**

`static` methods have two main purposes:
1. For utility or helper methods that don’t require any object state. Since there is no need to access instance variables, having `static` methods eliminates the need for the caller to instantiate an object just to call the method.
2. For state that is shared by all instances of a class, like a counter. All instances must share the same state. Methods that merely use that state should be `static` as well.

We can use an instance of the object to call a static method. The compiler checks for the type of the reference and uses that instead of the object—which is sneaky of Java.

```java
public class Snake {
    public static long hiss = 2;
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Snake hiss value (static access): " + Snake.hiss);

        Snake s = new Snake();
        System.out.println("Snake hiss value (instance / reference access): " + s.hiss);
        s = null; // Nullifying the reference
        System.out.println("Snake hiss value after nullifying reference (instance / reference access): " + s.hiss);
    }
}
```

**Class vs. Instance Membership**
A static member cannot call an instance member without referencing an instance of the class.
This shouldn’t be a surprise since static doesn’t require any instances of the class to even exist.

Both a static method and a non-static / instance method can call a static method because static methods don’t require an object to use. 
Only an instance method can call another instance method on the same class without using a reference variable, because instance methods do require an object. 

*A static method can be called by both static and instance methods*
*An instance method can be called only by an instance method, unless an object reference is used*

Above rule applies for static and instance variables too. 

**Static methods**
- Exist without any object
- Therefore cannot assume an instance exists
- Static method cannot directly call instance methods or instance variables

**Instance methods**
- Run on an object
- An object always has access to the class
- Instance method can call both instance and static methods

Static methods run even when no objects exist.
Instance methods require an object.

**One-Line Memory Rules**
- Static: cannot access instance directly
- Instance: can access static and instance
- Static needs an object reference to touch instance
- Instance already has an object (this)

Static methods never use this
Instance methods always run with this

A static interface method cannot call a default interface method without a reference, much the same way that within a class, a static method cannot call an instance method without a reference.

**Static Variable Modifiers**
Constants use the modifier static final and a different naming convention than other variables.

The rules for static final variables are similar to instance final variables, except they do not use static constructors (there is no such thing!) and use static initializers instead of instance initializers. Example,

```java
public class Panda {
    final static String name = "Ronda";
    static final int bamboo;
    static final double height; // DOES NOT COMPILE
    static { bamboo = 5;}
}
```

`final` variables must be initialized with a value.
static initializers run when the class is first loaded. 
static initializers can be used to assign final variable for the first time, they cannot be reassigned though.

Note: Try to Avoid static and Instance Initializers
Using static and instance initializers can make your code much harder to read. Everything that could be done in an instance initializer could be done in a constructor instead. Many people find the constructor approach easier to read.

There is a common case to use a static initializer: when you need to initialize a static field and the code to do so requires more than one line. When you do need to use a static initializer, put all the static initialization in the same block, so that the order is obvious.

**Question:** Can we initialize static fields inside constructor?
**Answer:** We can use constructor to initalize static field, but is is almost always a bad idea.
static field belongs to a class, not to any object.
A constructor runs each time an object is created, that's the main problem.
Java doesn't forbid assigning to a static field inside a constructor.
Constructor might run multiple times, which make initialization unpredictable.
This breaks the meaning of static. `static` implies class-level, one-time initialization.
Constructor imply pre-object behavior.
Mixing them is conceptually wrong and confusing.
Static fields should be initialized once, not every time an object is created.

**Static Imports**
Static imports are only for importing static members like a method or variable, not for class.
Regular imports are for importing a class.

**Passing Data among Methods /  Pass-by-Value**
Java is always a “pass-by-value” language. This means that a copy of the variable is made and the method receives that copy. Assignments made in the method do not affect the caller. Example,

```java
public static void main(String... args) {
    int num = 4;
    newNumber(num);
    System.out.println(num);    // prints 4
}

public static void newNumber(int num) {
    num = 8;
    num++;  // this doesn't affect the original value of num, it's just a local copy
}
```

**Pass-by-Value vs. Pass-by-Reference**

**Autoboxing and Unboxing Variables**

Autoboxing is the process of converting a primitive into its equivalent wrapper class.
Unboxing is the process of converting a wrapper class into its equivalent primitive.
Autoboxing applies to all primitives and their associated wrapper types.

Autoboxing: automatic conversion from primitive to wrapper
Unboxing: automatic conversion from wrapper to primitive
Example,
```java
Integer a = 10;   // autoboxing (int to Integer)
int b = a;        // unboxing (Integer to int)
```

**Important Points**

1. Unboxing can throw `NullPointerException`
Calling any method on null gives a `NullPointerException`

```java
Integer x = null;  // valid assignment
int y = x;         // NPE
```

1. `==` behaves differently
Java caches wrapper objects for some small integer values to save memory and improve performance.
When you autobox certain primitive values, Java may reuse existing wrapper objects instead of creating new ones.

Wrapper caching range: -128 to 127 for `Byte`, `Short`, `Integer`, `Long`
Wrapper caching range: 0 to 127 for `Character`
Caching happens during autoboxing, not when using `new`

```java
Integer a = 100;
Integer b = 100;
System.out.println(a == b);   // true (Both refer to the same cached Integer object)

Integer c = 200;
Integer d = 200;
System.out.println(c == d);   // false (Outside the cache range, new objects are created, so references differ)

// The following's are not wrapper cached
Integer x = new Integer(100);
Integer y = new Integer(100);
System.out.println(x == y);   // false

// So, it's always better to use equals for value comparison
x.equals(y);  // true
```

3. Method overloading prefers primitives
Method overriding resolution preferes the following orders,
Exact match > Widening primitive conversions (Numeric Promotion) > Autoboxing > Varargs

Overloading resolution never considers boxing first if a primitive match exists.
So, exact match always beats autoboxing

```java
void test(int x) {}
void test(Integer x) {}

test(10);  // calls test(int)
```

> Boxing creates objects, unboxing risks NPE, `==` compares references unless unboxed.

While Java will implicitly cast a smaller primitive to a larger type, as well as autobox, it will not do both at the same time.
The following code doesn't compile,
```java
Long badGorilla = 8; // primitive int needs to be numerically promoted to long, the autoboxed to Long

Integer e = Integer.valueOf(9);
long ears = e;                         // Unboxing, then implicit casting, this is okay, Integer to int, then into to long
```

Java will not automatically convert to a narrower type.
Java will cast or autobox the value automatically, but not both at the same time.

**Overloading Methods**
Method overloading occurs when methods in the same class have the same name but different method signatures, which means they use different parameter lists.
Everything other than the method name can vary for overloading methods.
This means there can be different access modifiers, optional specifiers (like static), return types, and exception lists.

Return type, access modifier, and exception list are irrelevant to overloading. Only the method name and parameter list matter.

If method signatures are the same (method name + parameter types, not method name), then they don't qualify for overloading.
The names of the parameters in the list do not matter when determining the method signature, types of the parameter matter. 
Java picks the most specific version of a method that it can. 
Java tries to find the most specific matching overloaded method.
Java tries to use the most specific parameter list it can find. This is true for autoboxing as well as other matching types.
Arrays don't autobox or unbox i.e. int[] are not autoboxed to Integer[], and vice versa.

Java treats varargs as if they were an array. So, the following two method signatures are the same, so not overloading,
Even though the code doesn’t look the same, it compiles to the same parameter list.
```java
public class Toucan {
    public void fly(int[] lengths) {}
    public void fly(int… lengths) {}     // DOES NOT COMPILE
}
```

Java calls the most specific method it can. When some of the types interact, the Java rules focus on backward compatibility. A long time ago, autoboxing and varargs didn’t exist. Since old code still needs to work, this means autoboxing and varargs come last when Java looks at overloaded methods.