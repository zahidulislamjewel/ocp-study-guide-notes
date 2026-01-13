# Chapter 8: Lambdas and Functional Interfaces

## OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER:

### Using Object-Oriented Concepts in Java

- Understand variable scopes, apply encapsulation, and create immutable objects. Use local variable type inference.
- Create and use interfaces, identify functional interfaces, and utilize private, static, and default interface methods.

---

**Writing Simple Lambdas**

Functional programming is a way of writing code more declaratively. 

In functional programming, we need to specify what we want to do rather than dealing with the state of objects. We need to focus more on expressions than loops. 

Functional programming uses lambda expressions to write code. A lambda expression is a block of code that gets passed around. 

Lambdas are analogous to closures in other functional programming languages.

Lambdas uses a concept called deferred execution, which means that code is specified now but will run later. 

**Learning Lambda Syntax**

Lambdas work with interfaces that have exactly one abstract method.

Java relies on context when figuring out what lambda expressions mean. Context refers to where and how the lambda is interpreted. 

Fun fact: `s -> {}` and `() -> {}` are valid lambdas.

Equivalent Lambda example,

```java
Predicate<String> booleanPredicate = (String s) -> { return s.startsWith("A"); }
Predicate<String> booleanPredicate = (String s) -> s.startsWith("A");
Predicate<String> booleanPredicate = s -> s.startsWith("A");
```

**Assigning Lambdas to `var`**

```java
var invalid = (Animal a) -> a.canHop();  // DOES NOT COMPILE, not enough context
```

Java infers information about the lambda from the context. `var` assumes the type based on the context as well.

Neither the lambda nor `var` have enough information to determine what type of functional interface should be used.

**Coding Functional Interfaces**

A functional interface is an interface that contains a single abstract method. This is officially known as a single abstract method (SAM) rule.

Functional Interface example,

```java
@FunctionalInterface
public interface Sprint {
    public void sprint(int speed);
}

public interface Dash extends Sprint {} // Valid functional interface, inherits the single abstract method sprint()
```

The `@FunctionalInterface` annotation tells the compiler the intent of the code to be a functional interface. 

This annotation means the authors of the interface promise it will be safe to use in a lambda in the future.

Having exactly one abstract method is what makes it a functional interface, not the annotation.
So, the annotation is optional but recommended for functional interface or SAM interface.

Default methods function like abstract methods, in that they can be overridden in a class implementing the interface.

**Adding Object Methods**

All classes inherit certain methods from Object. Object method signatures:

- `public String toString()`
- `public boolean equals(Object)`
- `public int hashCode()`

If a functional interface includes an abstract method with the same signature as a public method found in `Object`, those methods do not count toward the single abstract method test.

Since Java assumes all classes extend from `Object`, we also cannot declare an interface method that is incompatible with `Object`. 

For example, declaring an abstract method `int toString()` in an interface would not compile since Object’s version of the method returns a `String`.

Example of an invalid functional interface for not having any abstract method,

```java
public interface Soar {
    abstract String toString();  // Not counted as present in Object
}
```

Example of a valid functional interface for having single abstrat method (SAM),

```java
public interface Dive {
    String toString();          // Not counted as present in Object
    boolean equals(Object o);   // Not counted as present in Object
    abstract int hashCode();    // Not counted as present in Object
    void dive();                // Counted as valid SAM
}
```


Example of an invalid functional interface for having two abstract method,

```java
public interface Hibernate {
    String toString();           // Not counted as present in Object
    boolean equals(Hibernate o); // Counted, method signature mismatch
    abstract int hashCode();     // Not counted as present in Object
    void rest();                 // Counted as abstract method
}
```

**Using Method References**

Method references are another way to make the code easier to read, such as simply mentioning the name of the method.

A **method reference** is a shorthand syntax used to refer to an existing method instead of writing a lambda expression that simply calls that method.

It does not create a new method. It only *references* an already-existing one.

Method reference is possible only when the referenced method’s signature **matches the functional interface’s single abstract method**.

Operator `::` is like a lambda, and it is used for deferred execution with a functional interface. 

A method reference and a lambda behave the same way at runtime. 

```java
Cosumer<String> printer = s -> System.out.println(s);

// Equivalent method Reference
Cosumer<String> printer = System.out::println;
```

There are four formats for method references.

1. `static` methods
2. Instance methods on a particular object
3. Instance methods on a parameter to be determined at runtime
4. Constructors

**1. Calling `static` Methods**

With both lambdas and method references, Java infers information from the context.

Example,

```java
interface Converter {
    long convert(double num);    
}

Converter lambda = x -> Math.round(x);
Converter methodRef = Math::round;  // `long round(double)` method signature matches `long convert(double)` 
```

**2. Calling Instance Methods on a Particular Object**

An instance is required to invoke the instance method as method reference

Example,

```java
interface StringStart {
    boolean beginningCheck(String prefix);    
}

var str = "Zoo";    // This instance is required to invoke startsWith method, thus instance method reference
StringStart lambda = prefix -> str.startsWith(prefix);
StringStart methodRef = str::startsWith;

lambda.beginningCheck("A");     // false
methodRef.beginningCheck("A");  // false
```

Another Example,

```java
interface StringChecker {
    boolean check();    
}

var str = "";   // This instance is required to invoke isEmpty method, thus instance method reference
StringChecker lambda = () -> str.isEmpty();
StringChecker methodRef = str::isEmpty;

lambda.check();     // true
methodRef.check();  // true
```

While all method references can be turned into lambdas, the opposite is not always true. 

We can use a method reference **only when the lambda body does nothing except call another method**.

**Condition:**

Lambda with the following signature can be reoplced with method reference,

```java
// lambda
(params) -> existingMethod(params)

// equivalent method reference
ClassName::existingMethod
```

**A lambda can be replaced with method reference if and only if**:

* Method signatures are compatible
* No additional logic exists inside the lambda
* Parameters are passed unchanged

**3. Calling Instance Methods on a Parameter**

Can be used when the object is provided as a parameter.

Example 1,

```java
interface StringParamChecker {
    boolean check(String text);    
}

StringParamChecker lambda = s -> s.isEmpty();
StringParamChecker methodRef = String::isEmpty;

lambda.check("");    // true
methodRef.check("");    // false
```

Example 2,

```java
interface StringTwoParamChecker {
    boolean check(String text, String prefix);    
}

StringTwoParamChecker lambda = (str, prefix) -> str.startsWith(prefix);
StringTwoParamChecker methodRef = String::startsWith;

lambda.check("Zoo", "Z");       // true
methodRef.check("Zoo", "Z");    // true
```

Example 3,

```java
@FunctionalInterface
public interface Function<T, R> {
   R apply(T var1);
}

Function<String, String> lowerCaseLambda = s -> s.toLowerCase();
Function<String, String> lowerCaseMethodRef = String::toLowerCase;

lowerCaseLambda.apply("Animal");    // animal
lowerCaseMethodRef.apply("Animal"); // animal
```

Another common example:

```java
@FunctionalInterface
public interface Comparator<T> {
   int compare(T var1, T var2);
}

// String has a overridden compareTo with signature int compareTo(String)
Comparator<String> compareLambda = (a, b) -> a.compareTo(b);    // takes two args, returns boolean, matches Comparator SAM signature
Comparator<String> compareMethodRef = String::compareTo;

compareLambda.compare("A", "a");    // false
compareMethodRef.compare("A", "a"); // false
```

**Calling Instance Methods on a Particular Object** and **Calling Instance Methods on a Parameter** look similar, although one references a local variable (instance), while the other only references the functional interface parameters (object) passed to it.

**4. Calling Constructors**

A constructor reference is a special type of method reference that uses new instead of a method and instantiates an object.

Example 1,

```java
interface EmptyStringCreator {
    String create();
}

EmptyStringCreator lambda = () -> new String();
EmptyStringCreator methodRef = String::new;

var emptyString = methodRef.create();
emptyString.isEmpty();  // true
```

Example 2,

```java
interface StringCopier {
    String copy(String value);
}

StringCopier lambda = x -> new String(x);
StringCopier methodRef = String::new;

var copyString = methodRef.copy("Zebra");
copyString.equals("Zebra"); // true
```

You can’t always determine which method can be called by looking at the method reference. Instead, you have to look at the context to see what parameters are used and if there is a return type.

**Working with Built-in Functional Interfaces**

In generics declaration of an interface, the symbol `<T>` allows the interface to take an object of a specified type. 

If a second type parameter is needed, we use the next letter, `U` with symbol `<T, U>`. 

If a distinct return type is needed, we choose `R` for return as the generic type with symbol `<T, U, R>`.

**Common functional interfaces**

1. **Supplier<T>**
   Supplies a value of type `T` without taking any input. 
   Method: `get()` which returns `T`

2. **Consumer<T>**
   Consumes a value of type `T` and performs an operation without returning a result. 
   Method: `accept(T)` which returns `void`

3. **BiConsumer<T, U>**
   Consumes two input values of types `T` and `U` and performs an operation without returning a result. 
   Method: `accept(T, U)` which returns `void`

4. **Predicate<T>**
   Evaluates a condition on a value of type `T` and returns a boolean result.
    Method: `test(T)` which returns `boolean`

5. **BiPredicate<T, U>**
   Evaluates a condition involving two values of types `T` and `U`. 
   Method: `test(T, U)` which returns `boolean`

6. **Function<T, R>**
   Takes an input of type `T`, applies a transformation, and returns a result of type `R`. 
   Method: `apply(T)` which returns `R`

7. **BiFunction<T, U, R>**
   Takes two inputs of types `T` and `U`, applies a transformation, and returns a result of type `R`. 
   Method: `apply(T, U)` which returns `R`

8. **UnaryOperator<T>**
   A specialization of `Function` where the input and output types are the same. 
   Method: `apply(T)` which returns `T`

9.  **BinaryOperator<T>**
   A specialization of `BiFunction` where both input parameters and the return type are the same. 
   Method: `apply(T, T)` which returns `T`


Most of the time we don’t assign the implementation of the interface to a variable. The interface name is implied, and it is passed directly to the method that needs it.

Few more interfaces we need to know (next chapters),

1. **Comparator<T>**
   Defines a custom ordering logic for objects of type `T`, typically used for sorting collections.
   Method: `compare(T o1, T o2)` returns an `int`
   Returns a negative value if `o1` is less than `o2`, zero if they are equal, and a positive value if `o1` is greater than `o2`.

2. **Runnable**
   Represents a task that can be executed by a thread but does not return any result.
   Method: `run()` returns `void`
   Commonly used when creating threads or submitting tasks to an `Executor`.

3. **Callable<V>**
   Represents a task that can be executed by a thread and returns a result of type `V`.
   Method: `call()` returns `V`
   Unlike `Runnable`, it can return a value and throw checked exceptions.

A useful mental contrast is:

* `Runnable` is for fire-and-forget tasks.
* `Callable` is for tasks that produce a result.
* `Comparator` is for defining ordering logic.

**Implementing Supplier**

Example,

```java
@FunctionalInterface
public interface Supplier<T> {
      T get();
}

Supplier<LocalDate> s1 = LocalDate::now;
Supplier<LocalDate> s2 = () -> LocalDate.now();
 
LocalDate d1 = s1.get();
LocalDate d2 = s2.get();
 
System.out.println(d1);    // 2025-02-20
System.out.println(d2);    // 2025-02-20
```

**Implementing Consumer and BiConsumer**

Consumer Example,

```java
@FunctionalInterface
public interface Consumer<T> {
   void accept(T t);
   // omitted default method
}

Consumer<String> c1 = System.out::println;
Consumer<String> c2 = x -> System.out.println(x);
 
c1.accept("Annie");  // Annie
c2.accept("Annie");  // Annie
```

BiConsumer Example,

```java
@FunctionalInterface
public interface BiConsumer<T, U> {
   void accept(T t, U u);
   // omitted default method
}

var map = new HashMap<String, Integer>();
BiConsumer<String, Integer> b1 = map::put;   // instance method reference on an object since we want to call a method on the local variable map
BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);
 
b1.accept("chicken", 7);
b2.accept("chick", 1);
 
System.out.println(map);  // {chicken=7, chick=1}
```

**Implementing Predicate and BiPredicate**

Predicate Example,

```java
@FunctionalInterface
public interface Predicate<T> {
   boolean test(T t);
   // omitted default and static methods
}

Predicate<String> p1 = String::isEmpty;
Predicate<String> p2 = x -> x.isEmpty();
 
System.out.println(p1.test(""));  // true
System.out.println(p2.test(""));  // true
```

BiPredicate Example,

```java
@FunctionalInterface
public interface BiPredicate<T, U> {
   boolean test(T t, U u);
   // omitted default methods
}

BiPredicate<String, String> b1 = String::startsWith;
BiPredicate<String, String> b2 = (str, prefix) -> str.startsWith(prefix);
 
System.out.println(b1.test("chicken", "chick"));  // true
System.out.println(b2.test("chicken", "chick"));  // true
```

**Implementing Function and BiFunction**

Function Example,

```java
@FunctionalInterface
public interface Function<T, R> {
   R apply(T t);
   // omitted default and static methods
}

Function<String, Integer> f1 = String::length;
Function<String, Integer> f2 = x -> x.length();
 
System.out.println(f1.apply("cluck"));  // 5
System.out.println(f2.apply("cluck"));  // 5
```

BiFunction Example,

```java
@FunctionalInterface
public interface BiFunction<T, U, R> {
   R apply(T t, U u);
   // omitted default method
}

BiFunction<String, String, String> b1 = String::concat;
BiFunction<String, String, String> b2 = (str, suffix) -> str.concat(suffix);
 
System.out.println(b1.apply("baby ", "chick"));  // baby chick
System.out.println(b2.apply("baby ", "chick"));  // baby chick
```

**Implementing UnaryOperator and BinaryOperator**

UnaryOperator Example,

```java
@FunctionalInterface
public interface UnaryOperator<T> extends Function<T, T> {
   // omitted static method
}

UnaryOperator<String> u1 = String::toUpperCase;
UnaryOperator<String> u2 = x -> x.toUpperCase()

u1.apply("chirp");  // CHIRP
u2.apply("chirp");  // CHIRP
```

BinaryOperator Example,

```java
@FunctionalInterface
public interface BinaryOperator<T> extends BiFunction<T, T, T> {
   // omitted static methods
}

BinaryOperator<String> b1 = String::concat;
BinaryOperator<String> b2 = (s, t) -> s.concat(t);

b1.apply("baby ", "chick");  // baby chick
b2.apply("baby ", "chick");  // baby chick
```

**Checking Functional Interfaces**

The first thing to do is look at how many parameters the lambda takes and whether there is a return value.

When you see a `boolean` returned, think Predicate unless the generics specify a `Boolean` return type. 

**Using Convenience Methods on Functional Interfaces**

Convenience methods like, `and()`, `or()`, `negate()`, `andThen()` etc. are present in functional interfaces which themselves return new functional interfaces.

Example 1,

```java
Predicate<String> eggPredicate = s -> s.contains("egg");
Predicate<String> brownPredicate = s -> s.contains("brown");

Predicate<String> brownEggPredicate = eggPredicate.and(brownPredicate);
Predicate<String> otherEggPredicate = eggPredicate.and(brownPredicate.negate());
```

Example 2,

```java
Function<Integer, Integer> before = x -> x + 1;
Function<Integer, Integer> after = x -> x * 2;
 
Function<Integer, Integer> combined1 = after.compose(before);  // x + 1, and then x * 2
Function<Integer, Integer> combined2 = before.compose(after);  // x * 2, and then x + 1
combined1.apply(3);  // 8
combined2.apply(3);  // 7
```

**Learning the Functional Interfaces for Primitives**

**Functional Interfaces for `boolean`**

```java
@FunctionalInterface
public interface BooleanSupplier {
   boolean getAsBoolean();
}

BooleanSupplier b1 = () -> true;
BooleanSupplier b2 = () -> Math.random() > .5;

b1.getAsBoolean();  // true
b2.getAsBoolean();  // false or true (non-deterministic)
```

**Functional Interfaces for double, int, and long**

1. Suppliers - `DoubleSupplier`, `IntSupplier`, `LongSupplier`
2. Consumer - `DoubleConsumer`, `IntConsumer`, `LongConsumer`
3. Predicate - `DoublePredicate`, `IntPredicate`, `LongPredicate`
4. Function - `DoubleFunction`, `IntFunction`, `IntFunction`
5. UnaryOperator - `DoubleUnaryOperator`, `IntUnaryOperator`, `LongUnaryOperator`
6. BinaryOperator - `DoubleBinaryOperator`, `IntBinaryOperator`, `LongBinaryOperator`

**Working with Variables in Lambdas**

Functional interfaces can appear in three places with respect to lambdas: 
1. Parameter list
2. Local variables declared inside the lambda body
3. Variables referenced from the lambda body

**Listing Parameters in Lambdas**

Specifying the type of parameters is optional. Additionally, var can be used in place of the specific type. That means all three of these statements are interchangeable:

```java
Predicate<String> p = x -> true;
Predicate<String> p = (var x) -> true;
Predicate<String> p = (String x) -> true;
```

The type of the lambda parameter can be determined from the context.
A lambda infers the types from the surrounding context.

Lambda parameters are just like method parameters. We can add modifiers to them. Specifically, we can add the `final` modifier or an annotation, as shown in this example:

```java
list.sort((final var x, @Deprecated var y) -> x.compareTo(y));
```

**Parameter List Formats**

The compiler requires all parameters in the lambda to use the same format. Either of the following,
- without types
- with types
- with `var`

Some invalid parameter type declaration,

```java
(var x, y) -> "Hello"                  // DOES NOT COMPILE
(var x, Integer y) -> true             // DOES NOT COMPILE
(String x, var y, Integer z) -> true   // DOES NOT COMPILE
(Integer x, y) -> "goodbye"            // DOES NOT COMPILE
```

**Using Local Variables Inside a Lambda Body**

It is legal to define block instead of single expression in lambda. That block can have anything that is valid in a normal Java block, including local variable declarations.

A lambda can define parameters or variables in the body as long as their names are different from existing local variables.

**Keep Your Lambdas Short**

Instead of writing a lot of code in lambda body or block, it is better to refactor that block into an instance method and calling that in the lambda expression.

Example,

```java
Predicate<Integer> p1 = a -> returnSame(a);

Predicate<Integer> p1 = this::returnSame;
```

**Referencing Variables from the Lambda Body**

Lambda bodies are allowed to reference some variables from the surrounding code. 

A lambda can access an instance variable, method parameter, or local variable under certain conditions (effectively final). 

Instance variables (and class variables) are always allowed.

The only thing lambdas cannot access are variables that are not final or effectively final.

Again, the act of assigning a value is only a problem from the point of view of the lambda. Therefore, the lambda has to be the one to generate the compiler error.

Rules for accessing a variable from a lambda body inside a method:

1. Instance variable - Allowed
2. Static variable - Allowed
3. Local variable - Allowed if final or effectively final
4. Method parameter - Allowed if final or effectively final
5. Lambda parameter - Allowed