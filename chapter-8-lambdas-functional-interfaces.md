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