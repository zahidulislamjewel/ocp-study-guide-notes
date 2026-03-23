## Performance Report

Question: 50
Correct: 30 
Status: Failed (60%)
Time Taken: 58:11
Start Time: 23 March 26 00:30

## Weakness

1. Handling values
   - Manipulating text (2/5)
   - Using primitives and wrapper classess text (0/1)
2. Java OOA
   - Using fields and methods (0/2)
   - Nested classes (1/2)
   - Object initialization (1/2)
   - Interfaces (1/2)
   - Overriding/Polymorphism (2/3)
3. Controlling Program Flow
   - Loops (1/2)
   - if/else, switch (1/2)
4. Arrays and Collections
   - Collection API (5/8)
   - Arrays (2/3)
7. Java I/O API
   - Character and Binary Streams (0/1)
8. Modules 
   - Services in a Modular Application (0/1)
   - Migration to Modular Application (1/2)  
10. Streams and Lambda
    - Builtin Functional Interfaces (0/1)  

---

## Error Notes

### Question 2 

`Runnable` vs `Callable`, more specifically `run()` vs `call()` usage

**Question 4:** 

`"12345".charAt(6)` will throw an `IndexOutOfBoundsException`, not `StringIndexOutOfBoundsException` as per [API documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#charAt(int))

### Question 5 

Conversion / Coercion `byte` -> (`short` | `char`) -> (`int` | `long`) -> (`float` | `double`)

**Explanation**

Think of it as transferring contents of one bucket into another. You can always transfer the contents of a smaller bucket to a bigger one. But the opposite is not always possible. You can transfer the contents of the bigger bucket into the smaller bucket only if the actual content in the bigger bucket can fit into the smaller one. Otherwise, it will spill.  

It is the same with integral types as well. byte is smaller than short and int. So, you can assign a byte to an int (or an int to a float, or a float to a double) without any cast. But for the reverse, you need to assure the compiler that the actual value in your int variable at runtime will be smaller than a byte to let you assign the int variable to a byte variable. This is achieved by a cast. 

```java
int i = 10; 
byte b = 20; b = i; //will not compile because byte is smaller than int 
b = (byte) i; //OK   
```

Further, if you have a final variable and its value fits into a smaller type, then you can assign it without a cast because compiler already knows its value and realizes that it can fit into the smaller type. This is called implicit narrowing (it is implicit because there is not explicit cast) and is allowed between byte, int, char, and, short but not for long, float, and double.   

```java
final int k = 10; 
b = k; //Okay because k is final and 10 fits into a byte  

final float f = 10.0; // will not compile 
//because 10.0 is a double even though the value 10.0 fits into a float 
i = f; // will not compile either even after changing 10.0 to 10.0f.
```

### Question 6 

Console object acquire

**Explanation**

A JVM gets a `Console` object only when it is started in a way that gives it access to a real interactive terminal. In simple terms, if you run a Java program directly from a command line and its input and output are still connected to that terminal, the JVM can recognize that terminal as its console. In that case, calling `System.console()` returns a `Console` object representing that connection. 

But if the program is started in a non-interactive way, such as from an IDE, a background scheduler, a service, or with input/output redirected to files or pipes, then the JVM usually does not see a real console device. In that situation, `System.console()` returns `null`. So, the existence of the console object is not guaranteed by Java itself; it depends on how and where the JVM process was launched. 

This can be checked easily with a simple program like this:

```java
import java.io.Console;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();

        if (console == null) {
            System.out.println("No console available");
        } else {
            String name = console.readLine("Enter your name: ");
            System.out.println("Hello, " + name);
        }
    }
}
```

If you run this from a real terminal, `System.console()` will usually return a valid `Console` object, and the program will ask for input. But if you run it from many IDEs or in a background environment, it will often print `No console available`, showing that the JVM could not acquire a console.

### Question 8 

`public`, `protected`, and package-private `static` methods can be accessed by other classes in the same package without creating an instance of the class.

*static methods can't be abstract*

`protected` is not a package-level access modifier, although it does include package access as part of its visibility.

A `protected` member can be accessed:

* by any class in the same package
* by subclasses in other packages

So its access is wider than package-private.

Quick comparison:

* `private` only inside the same class
* package-private (no modifier) only inside the same package
* `protected` same package + subclasses outside the package
* `public` everywhere

So, `protected` is not the same as package-private, but within the same package it behaves like it is accessible.package

### Question 10

`break` and `continue` proper usage location with or without label. `breaks` vs. `continue`

`break` and `continue` are both control flow statements, but they are used in different ways. 

*A `break` statement is used to exit a switch or loop completely, while a `continue` statement is used only in loops and skips the rest of the current iteration, moving directly to the next one.*

`break` without a label can occur only in a `switch`, `while`, `do`, or `for` statement.

The `continue` statement can appear only within the context of a loop. It cannot occur in a `switch` (unless the switch itself is inside a loop).

A `continue` statement without a label attempts to transfer control to the innermost enclosing `while`, `do`, or `for` statement; this statement, which is called the continue target, then immediately ends the current iteration and begins a new one. If no `while`, `do`, or `for` statement encloses the `continue` statement, a compile-time error occurs.

A `continue` statement with label Identifier attempts to transfer control to the enclosing labelled statement that has the same Identifier as its label; that statement, which is called the continue target, then immediately ends the current iteration and begins a new one. The continue target must be a `while`, `do`, or `for` statement or a compile-time error occurs. If no labelled statement with Identifier as its label contains the continue statement, a compile-time error occurs.

A `break` statement with no label attempts to transfer control to the innermost enclosing `switch`, `while`, `do`, or `for` statement; this statement, which is called the break target, then immediately completes normally. If no `switch`, `while`, `do`, or `for` statement encloses the `break` statement, a compile-time error occurs.  

On the other hand, a `break` statement with a label Identifier attempts to transfer control to the enclosing labeled statement that has the same Identifier as its label; this statement, which is called the break target, then immediately completes normally. In this case, the break target need not be a `while`, `do`, `for`, or `switch` statement.

A labeled `continue` transfers control to the next iteration of the labeled loop. The label must belong to a loop, otherwise it is a compile-time error.

A labeled `break` transfers control out of the labeled statement completely. Unlike continue, the target of a labeled break does not have to be a loop or a switch; it can be any labeled block.

In summary, `break` means exit the loop or switch entirely, while `continue` means skip the current loop iteration and move to the next one. 

Without a label, both act on the nearest valid enclosing construct. With a label, `break` can jump out of any labeled statement, but `continue` can jump only to a labeled loop.

### Question 11

`java.time.Instant` and `java.time.Duration` are thread safe

**Explanation**

The `java.time` package contains the new Date and time API. The classes defined in this package represent the principle date-time concepts, including instants, durations, dates, times, time-zones and periods. All the classes of this package are immutable and therefore thread-safe.

### Question 12

Java 8 allows interfaces to have static methods as well as default methods. Java 9 allows interfaces to have private methods also.

All fields of an interface are public, static, and final. Therefore, volatile, transient, and synchronized do not make sense for such fields.

Interfaces allow multiple implementation inheritance through default methods. default methods introduce one form of multiple inheritance of implementation.

`default` methods in interfaces allow an interface to provide not just a method declaration, but also a ready-made implementation. Because a class can implement multiple interfaces, this means a class can inherit method implementations from more than one source. That is why people say `default` methods introduce a limited form of multiple inheritance of implementation.

But this works only as long as Java can determine a single clear implementation. For example,

```java
interface I1 {
    public default void m1() {
        System.out.println("in I1.m1");
    }
}

interface I2 {
    public default void m1() {
        System.out.println("in I2.m1");
    }
}

class CI implements I1, I2 {
    // This class will not compile.
}

// This class will compile because it provides its own implementation of m1.
class C2 implements I1, I2 {
    public void m1() {
        System.out.println("in C2.m1");
    }
}
```

Both `I1` and `I2` define the same default method `m1()`. When `CI` implements both interfaces, Java sees two equally valid implementations of the same method and cannot decide which one should be inherited. Since neither interface is more specific than the other, this becomes a conflict, and the class does not compile.

`C2` compiles because it removes that ambiguity by defining its own `m1()` method. Once the class provides its own implementation, Java no longer has to choose between `I1.m1()` and `I2.m1()`. The class’s version takes precedence, and the conflict is resolved.

This is different from the case where the same method signature comes from both an interface and a superclass. In that situation, Java always gives priority to the superclass method. So there is no ambiguity there. The class does not end up with two competing implementations; it effectively gets only the superclass version, while the interface default method is ignored unless the class explicitly chooses otherwise.

### Question 15

`java.lang.StringBuilder` methods

- `ensureCapacity(int)`
- `append(boolean)`
- `reverse()`
- `setLength(int)`
- `compareTo`

`setLength(int)` changes the current length of a `StringBuilder`.

It can do two things:

- shorten the content
- increase the length

When it shortens, extra characters at the end are cut off.
When it increases, Java adds invisible empty characters called null characters (\u0000) to fill the new space.

Example,

```java
// Example 1, shorten the content
StringBuilder sb = new StringBuilder("HelloWorld");
sb.setLength(5);

System.out.println(sb); // Hello

// Example 2, increase the length
StringBuilder builder = new StringBuilder("Hi");
builder.setLength(5);

System.out.println(builder.length()); // 5
System.out.println(builder);          // looks like: Hi

for (int i = 0; i < builder.length(); i++) {
    System.out.print((int) builder.charAt(i));
    System.out.print(" ");
}

// Output: 72 105 0 0 0
```

### Question 16

Constructor, field access and initialization

A default no args one will be provided if not defined any.

All non-final instance variables get default values if not explicitly initialized.

A constructor is non-static, and so it can access directly both the static and non-static members of the class.

Few notes,

- Non-final instance fields get default values if you do not initialize them explicitly.
- Non-final static fields also get default values if you do not initialize them explicitly.
- Final fields are different in terms of compilation rules: even though fields have default initialization at the runtime model level, a blank final field cannot be left unassigned in valid Java code. You must explicitly assign it in a field initializer, initializer block, or constructor/static initializer as appropriate.

### Question 17

Array of integer

```java
// Note that an array of integers IS an Object
Object obj = new int[]{ 1, 2, 3 }; // is valid. 

// But it is not an array of objects. 
Object[] o = new int[10]; // is not valid.  
```

Difference between the placement of square brackets: 

```java
int[] i, j; //here i and j are both array of integers. 
int i[], j; //here only i is an array of integers. j is just an integer.
```

### Question 18

We can create arrays of any type with length zero

Java allows arrays of length zero to be created.  

```java
int[] zeroLengthArray1 = new int[0]; 
System.out.println(zeroLengthArray1.length); // will print 0 

String[] zeroLengthArray2 = new String[0]; 
System.out.println(zeroLengthArray2.length); // will print 0
```

A null pointer is different from an array of length zero. A reference being null or pointing to null means it is not pointing to anything at all. But an array of length zero is a valid object. Thus, a reference pointing to such an array is not pointing to null.

For example, 
```java
int[] intArr = new int[0];

if (intArr == null) evaluates to false.
```

Example: When a Java program is run without any program arguments, the `String[]` args argument to `main()` gets an array of length Zero.

### Question 19

We can declare a variable named bool. bool is not a Java keyword.

### Question 20

Some tricky conditionals,

The statement : `if (false) ; else ;` is legal.

if-clause and the else-clause can have empty statements. Empty statement ( i.e. just  a semi-colon ) is a valid statement.

if-clause and the else-clause can have empty statements. Empty statement ( i.e. just ; ) is a valid statement. 

However, the following is illegal:  `if (true) else;` because the if part doesn't contain any valid statement. (A statement cannot start with an else!)
 
But the following is valid:  `if(true) if(false);` because if(false); is a valid statement.


### Question 21

`uses` and `requires` clauses are used by a module definition that uses a service

`provides` is used by the provider module to specify the service interface and the implementing class that implements the service interface. For example, 

```java
provides org.printservice.api.Print with com.myprinter.PrintImpl;
```

`uses` clause is used by the module that uses a service. For example, 

```java
uses org.printservice.api.Print;
```

`requires` The module that uses a service must require the module that defines the service interface.

For example, if an `abc.print` module implements an `org.printing.Print` service interface defined in `PrintServiceAPI` module using `com.abc.PrintImpl` class, then this is how its module-info should look:  

```java
module abc.print{    
    requires PrintServiceAPI; //required    
    //because this module defines the service interface org.printing.Print     
    
    provides org.printing.Print with com.abc.PrintImpl; 
}  
```

A module named `customer` that uses `Print` service may look like this:  

```java
module customer {    
    requires PrintServiceAPI; //required    
    //because this module defines the service interface org.printing.Print     
    
    uses org.printing.Print; //specifies that this module uses this service     
    //observe that abc.print module is not required. 
}
```

### Question 22

`String` and `StringBuilder` immutability

`String` class itself is final and so all of its methods are implicitly final.

The idea is this: when a class is declared final, no other class can extend it. And if no class can extend it, then no subclass can override any of its instance methods. Because of that, marking each method of a final class as final would make no practical difference. That is why people say the methods are “implicitly final”.

The `final` modifier on a method mainly exists to prevent overriding in subclasses. But if the class itself cannot have subclasses, that protection is already guaranteed automatically. So the methods behave as if they were final, even if the keyword is not written on each one.

> Since `String` is a final class, its methods cannot be overridden, so they are effectively final.

Both - `String` and `StringBuilder` are final classes. So is `StringBuffer`.

`String`, `StringBuilder`, and `StringBuffer` - all are final classes.  

1. Remember that wrapper classes (`java.lang.Boolean`, `java.lang.Integer`, `java.lang.Long`, `java.lang.Short` etc.) are also final and so they cannot be extended.  
2. `java.lang.Number`, however, is not final. `Integer`, `Long`, Double etc. extend `Number`.  
3. `java.lang.System` is final as well.

`String` is a final class and final classes cannot be extended. There are questions on this aspect in the exam and so you should remember that `StringBuffer` and `StringBuilder` are also final. All primitive wrappers are also final (i.e. `Boolean`, `Character`, `Short`, `Integer`, `Byte` etc). `java.lang.System` is also final.

### Question 23

Autoboxing rules

`System.out.println(2+"");` is an autoboxing. Here's why,

Whenever only one operand of the `+` operator is a `String`, the other operand is converted into a `String` using a string conversion. 

If the other operand is a numeric primitive type, then it is first converted to a reference type using the boxing conversion and then the boxed reference is used to produce a `String`. 

Thus, in this case, the numeric part will first be boxed into an `Integer` object and then the `Integer` object will be used to produce the String "2", which will then be concatenated with "". 

Note that, no autoboxing occurs in `System.out.println(2)`; because the `println(int)` method is invoked in this case.


### Question 26

`String` methods,

1. `isBlank()` - Returns true if the string is empty or contains only white space codepoints, otherwise false.
2. `isEmpty()` - Returns true if, and only if, `length()` is 0.
3. `lines()` - Returns a stream of lines (`Stream<String>`) extracted from this string, separated by line terminators.
4. `strip()` - Returns a string whose value is this string, with all leading and trailing white space removed. Note: `stripLeading()` and `stripTrailing()` are also available.
5. `indent(int n)` - Adjusts the indentation of each line of this string based on the value of n, and normalizes line termination characters. Added in Java 12

### Question 27

`java.util.function.IntFunction`

It takes int primitive as an argument. It can be parameterized to return any type.

For example, the following returns a `String`,

```java
IntFunction<String> f = x -> "" + x;
```

It avoids additional cost associated with autoboxing/unboxing.

Remember that primitive and object versions of data types (i.e. `int` and `Integer`, `double` and `Double`, etc.) are not really compatible with each other in java. They are made compatible through the extra step of auto-boxing/unboxing.  Thus, if you have a stream of primitive ints and if you try to use the object versions of Stream and Function (i.e. `Stream<Integer>` and `Function<Integer, Integer>`), you will incur the cost of boxing and unboxing the elements.  

To eliminate this problem, the function package contains primitive specialized versions of streams as well as functional interfaces. For example, instead of using `Stream<Integer>`, you should use `IntStream`. You can now process each element of the stream using `IntFunction`. This will avoid auto-boxing/unboxing altogether.  

Thus, whenever you want to process streams of primitive elements, you should use the primitive specialized streams (i.e. `IntStream`, `LongStream`, and `DoubleStream`) and primitive specialized functional interfaces (i.e. `IntFunction`, `IntConsumer`, `IntSupplier` etc.) to achieve better performance.

None of the primitive specialized functional interfaces (such as `IntFunction`, `DoubleFunction`, or `IntConsumer`) extend the non-primitive functional interfaces (i.e. `Function`, `Consumer`, and so on).

`java.util.function` package contains `int`, `double`, and `long` (but no `float`) versions of all the functional interfaces. For example, there is an `IntFunction`, a `DoubleFunction`, and a `LongFunction`, which are `int`, `double`, and `long`, versions of `Function`.

These functions are used along with primitive specialized versions of streams such as `IntStream`, `DoubleStream`, and `LongStream`


### Question 28

Command line options for module

You should remember the following command line options:  

Module options applicable for java as well as javac: 

* `--module` or `-m`: used to run or compile only the specified module. 
* `--module-path` or `-p`: used to specify the paths where java or javac will look for module definitions.  
 
Module options applicable only for javac: 

* `--module-source-path` has no shortcut. Used by javac to look for source module definitions.
* `-d`: used to specify output directory where the class files will be created after compilation.  
 
Module options applicable only for java: 

* `--list-modules` has no shortcut. It lists observable modules and exits. 
* `--show-module-resolution` has no shortcut. It shows module resolution output during startup.
* `--describe-module` or `-d`: It describes a module and exits.  
 
Note that `-d` works differently in java and javac. Further, `-d` is very different from `-D`, which is used while running a java program to specify name-value pairs of properties at the command line.

### Question 29

Module path, automatic and unnamed module question

If a request is made from an automatic module to load a type whose package is not defined in any known module then the module system will attempt to load it from the classpath.

The unnamed module reads every other module. In other words, a class in an unnamed module can access all exported types of all modules.

A named module cannot access any random class from the classpath. If your named module requires access to a non-modular class, you must put the non-modular class/jar on module-path and load it as an automatic module. Further, you must also put an appropriate `requires` clause in your `module-info`.

If a package is defined in both a named module and the unnamed module then the package in the unnamed module is ignored. 

Remember that named modules cannot access classes from the unnamed module because it is not possible for a named module to declare dependency on the unnamed module.

But what if a named module needs to access a class from a non-modular jar? Well, you can put the non-modular jar on the module-path, thereby making it an automatic module. A named module can declare dependency on an automatic module using the requires clause.

Now, what if that jar in turn requires access to some other class from another third party non-modular jar? Here, the original modular jar doesn't directly access the non-modular jar, so it may not be wise to create an automatic module out of all such third party jars. This is where the -classpath options is helpful.

In addition to reading every other named module, an automatic module is also made to read the unnamed module.  Thus, while running a modular application, the classpath option can be used to enable automatic modules to access third party non-modular jars.

**Bottom Up Approach for modularizing an application**

While modularizing an app using the bottom-up approach, you need to convert lower level libraries i.e. dependencies into modular jars before you can convert the higher level libraries. For example, if a class in A.jar directly uses a class from B.jar, and a class in B.jar directly uses a class from C.jar, you need to first modularize C.jar and then B.jar before you can modularize A.jar.

Thus, bottom up approach is possible only when the dependencies are modularized already. Effectively, when bottom-up migration is complete, every class/package of an application is put on the module-path. Nothing is left on the classpath.

**Top Down Approach for modularising an application**

While modularizing an app in a top-down approach, you need to remember the following points:

1. Any jar file can be converted into an automatic module by simply putting that jar on the module-path instead of the classpath. Java automatically derives the name of this module from the name of the jar file. An automatic module implicitly exports all of its packages.
2. Any jar that is put on classpath (instead of module-path) is loaded as a part of the unnamed module. The unnamed module implicitly exports all of its packages.
3. An explicitly named module (which means, a module that has an explicitly defined name in its module-info.java file) can specify dependency on an automatic module just like it does for any other module i.e. by adding a requires <module-name>; clause in its module info but it cannot do so for the unnamed module because there is no way to write a requires clause without a name.  In other words, an explicitly named module can "read" classes present in an automatic module using an appropriate requires clause but cannot read classes in the unnamed module by any means.
4. An automatic module exports all of its packages and is allowed to read all packages exported by other modules. Thus, a class in an automatic module can access: all packages of other automatic modules + all packages exported by explicitly named modules + all packages of the unnamed module.
5. The unnamed module exports all of its packages and is allowed to read all packages exported by other modules. Thus, a class in the unnamed module can access: all packages of the unnamed module + all packages of automatic modules + all packages exported by explicitly named modules.

Thus, if your application jar A directly uses a class from another jar B, then you would have to convert B into a module (either named or automatic). If B uses another jar C, then you can leave C on the class path if B hasn't yet been migrated into a named module. Otherwise, you would have to convert C into an automatic module as well. 

Note: There are two possible ways for an automatic module to get its name:

1. When an Automatic-Module-Name entry is available in the manifest, its value is the name of the automatic module.
2. Otherwise, a name is derived from the JAR filename (see the ModuleFinder JavaDoc for the derivation algorithm) - Basically, hyphens are converted into dots and the version number part is ignored. So, for example, if you put mysql-connector-java-8.0.11.jar on module path, its module name would be mysql.connector.java

### Question 32

`java.time` package

Java 8 introduced a new package java.time to deal with dates. The old classes such as java.util.Date are not recommended anymore.

Briefly:
`java.time` Package: This is the base package of new Java Date Time API. All the commonly used classes such as `LocalDate`, `LocalTime`, `LocalDateTime`, `Instant`, `Period`, `Duration` are part of this package. All of these classes are immutable and thread safe. 

`java.time.format` Package: This package contains classes used for formatting and parsing date time objects such as `java.time.format.DateTimeFormatter`.

`java.time.zone` Package: This package contains classes for supporting different time zones and their rules.

`java.time.chrono` Package: This package defines generic APIs for non ISO calendar systems. We can extend `AbstractChronology` class to create our own calendar system.

`java.time.temporal` Package: This package contains temporal objects and we can use it for find out specific date or time related to date/time object. For example, we can use these to find out the first or last day of the month. You can identify these methods easily because they always have format “withXXX”.

### Question 33

Interface properties

Java 8 onwards, an interface is allowed to have default and static methods, which are non-abstract methods. Java 9 onwards, an interface is also allowed to have private methods.

An interface may extend a sealed interface.

### Question 34

Default constructor

It takes no arguments.

It is provided by the compiler if the class does not define any constructor. It is immaterial if the super class provides a constructor or not.

The access type of a default constructor is same as the access type of the class. Thus, if a class is public, the default constructor will be public. 

Note that you can use private and protected access modifers for nested classes only and not for top level (aka package level classes).

The default constructor is provided by the compiler only when a class does not define ANY constructor explicitly. For example, 

```java
public class B {
    // This constructor is automatically inserted by the compiler
    // because there is no other constructor defined explicitly by the programmer.
    public B() {
        super(); // Observe that it calls the superclass's default no-args constructor.
    }
}

public class A {
    // Compiler will not generate any constructor because
    // the programmer has already defined one.
    public A(int i) {
        // do something
    }
}
```

### Question 35

Startvation, Deadlock, Livelock, Threashing

Thrashing occurs when a program makes little or no progress because threads perform excessive context switching. This may leave little or no time for the application code to execute.

Starvation occurs when one thread cannot access the CPU because one or more other threads are monopolizing the CPU. Thread starvation may be caused because of different thread priorities. A lower-priority thread can be starved by higher-priority threads if the higher-priority threads do not yield control of the CPU from time to time.

The exam needs you to understand and differentiate among Deadlock, Starvation, and Livelock. The following are brief descriptions taken from Oracle Java Tutorial:

1. Deadlock describes a situation where two or more threads are blocked forever, waiting for each other. For example, two threads T1 and T2 need a File and a Printer. T1 acquires the lock for the file and is about to acquire the lock for the Printer but before it could acquire the lock, T2 acquires the lock for the Printer and tries to acquire the lock for the file (which is already held by T1). So now, both the threads keep waiting for ever for each other to release their locks and neither will be able to proceed. 

2. Starvation describes a situation where a thread is unable to gain regular access to shared resources and is unable to make progress. This happens when shared resources are made unavailable for long periods by "greedy" threads. For example, suppose an object provides a synchronized method that often takes a long time to return. If one thread invokes this method frequently, other threads that also need frequent synchronized access to the same object will often be blocked. 

3. Livelock: A thread often acts in response to the action of another thread. If the other thread's action is also a response to the action of another thread, then livelock may result. As with deadlock, livelocked threads are unable to make further progress. However, the threads are not blocked — they are simply too busy responding to each other to resume work. For example, after acquiring the File lock, T1 tries to acquire the Printer lock. Finding the Printer lock to be already taken, it releases the lock for the File and notifies T2. At the same time, T2 tries to acquire the File lock and seeing that it is already taken it releases Printer lock and notifies T1. This process can go on and on, both the threads releasing and acquiring the locks in tandem but none of them getting both the locks at the same time. So neither of the threads is blocked but neither of the threads is able to do any real work. All they are doing is notifying each other.

### Question 36

Inner class

A non static inner class may have static members.

Since Java 16, non-static nested class i.e. inner class is allowed to have static members. Example,

```java
class OuterClass {
    public class InnerClass {
        static int VAL = 10;                 // COMPILES FINE
        static String STR = "1234";          // COMPILES FINE
        static Object obj = new Object();    // COMPILES FINE
        static int val2 = 10;                // COMPILES FINE

        static final void method() { }       // COMPILES FINE
    }
}
```

Anonymous inner classes cannot be static.

Explicit extends or implements clauses are not allowed for anonymous inner classes. Other inner class (i.e. non anonymous) can have them.

Inner class can extend the outer class.

Anonymous inner class can be created for classes. (Not just for interfaces). They implicitly extend the class.

Anonymous inner class can have initialization parameter. (If the class they extend has a corresponding constructor).

You cannot pass parameters when you implement an interface by an anonymous class.

### Question 37

`System.in` refers to the standard input stream of a Java program. It is opened when the program is started.

`public static final InputStream in` The "standard" input stream. This stream is already open and ready to supply input data. Typically this stream corresponds to keyboard input or another input source specified by the host environment or user.

The `System.in` variable cannot be reassigned to any other stream directly.

Although `in` is a final variable and so, it cannot be reassigned directly. However, System class has a `public static void setIn(InputStream in)` method that can be used to change `in` to refer to any other `InputStream`.

It is opened when the program is started and can be closed by calling `System.in.close();`.

The declared type of System.in is `java.io.InputStream`. It refers to an object of type `java.io.BufferedInputStream`.

An InputStream is meant to read bytes. Whether these bytes are characters or not is irrelevant. A program could potentially interpret the bytes reads through this stream as characters.

### Question 38

Inner class

A final class can never be abstract.

'implements' part comes only in class definition not in instantiation.

Inner class can be private

### Question 39

Module system

It encapsulates the packages.

It provides a reliable mechanism to let components of an application specify their dependencies on other components of the same application.

A java module specifies the packages that other modules can use using the `exports` and `exports to` clauses and it defines its dependencies on other modules using the `requires` and `requires transitive` clauses in its module-info.

It provides a way to specify services and declare dependence on such services.

The Java module system allows a module to specify the services that it provides using the `provides` clause and it allows a module to declare its dependency on services using the `uses` clause.

The specific goals of the module system are to provide:
1. Reliable configuration, to replace the brittle, error-prone class-path mechanism with a means for program components to declare explicit dependences upon one another, along with,
2. Strong encapsulation, to allow a component to declare which of its public types are accessible to other components, and which are not.

### Question 42

Default values for static fields

```java
public class TestClass {
    static char ch;
    static float f;
    static boolean bool;

    public static void main(String[] args) {
        System.out.print(f);
        System.out.print(" ");
        System.out.print(ch);
        System.out.print(" ");
        System.out.print(bool);
    }
}
```

This question tests you on two aspects - 
1. the default values that are given to variables of various primitive types. You should remember that all numeric types, including char, get the value of 0 (or 0.0 for float and double) and boolean gets the value of false.  
2. how the value is printed by System.out.print method - java.lang.System class has a public static variable named out, which is of class java.io.PrintStream.  The PrintStream class has multiple print/println methods for printing out primitive values as well as objects.  
 
For `byte`, `short`, and `int`, these `print/println` methods simply print the integer value as it is.  

For `char`, the `print/println` methods translate the character into one or more bytes according to the platform's default character encoding. That is why while printing a `char` value of 0, a blank space is printed instead of 0 (even though the char's integral value is 0).  

For `long`, `float`, and `double` values, these `print/println` methods use the respective primitive wrapper class's `toString` method to get the String representation of that primitive. 

For example, to print the value of a `float` variable f, it internally calls `Float.toString(f)`. Now, this method doesn't append an "f" at the end of a float value. That is why a float value of 0.0 is printed as 0.0 and not 0.0f.

### Question 43

The concept of autoboxing is applicable to primitive types (`byte`, `char`, `short`, `int`, `long`, `float`, `double`, and `boolean`) only. `String` is a not a primitive type. It is a reference type. Example,

```java
Float f = 2.0f;

Byte b = 2;

Character c = 2;
```

### Question 44

Multi dimensional array initialization

Size of the dimensions is required to be specified only at the time of instantiation and not at the time of declaration. For example,

```java
int[][] ia; //this is a valid declaration. 
int[][] ia = new int[2][3];//This is a valid declaration and a valid instantiation
```

Further, only the size of the first dimension is required to be specified at the time of instantiation for an array of more than one dimension. Sizes of the other dimensions may be left out.

```java
int[][] iaa=new int[3][]; 
int[][][] iaaa = new int[3][][]; //Both are valid. 
```

This is allowed because a multi dimensional array in Java is just an array of arrays. They do not have to be symmetric, that is, each sub array is an independent array and so they do not have to be of the same size. So, in the above example, iaa[0] can be initialized to new int[5], and ia[1] to new int[10], while ia[2] can be left null.

Unlike some other languages, multi dimensional arrays in Java are not like matrices. They are just arrays of arrays. For example, if you have a two dimensional array then each element of this array is a one dimensional array. Each such array element is independent and therefore can be of different lengths (but not of different type). 

### Question 45

`super()` and `this()` usage

`super();` is automatically added if the sub class constructor doesn't call any of the super class's constructors.

If neither `super()` or `this()` is declared as the first statement of the body of a constructor, then `super()` will implicitly be inserted as the first statement.

You can either call `super(<appropriate list of arguments>)` or `this(<appropriate list of arguments>)` but not both from a constructor.

Note that calling `super()`; will not always work because if the super class has defined a constructor with arguments and has not defined a no args constructor then no args constructor will not be provided by the compiler. It is provided only to the class that does not define ANY constructor explicitly.

### Question 48

A `SortedSet` keeps unique elements in their natural order.

A `NavigableSet` keeps the elements sorted.

A `NavigableSet` is a SortedSet extended with navigation methods reporting closest matches for given search targets. 

Methods lower, floor, ceiling, and higher return elements respectively less than, less than or equal, greater than or equal, and greater than a given element, returning null if there is no such element. Since `NavigableSet` is a SortedSet, it keeps the elements sorted.

Calling `addFirst/addLast` on `TreeSet` throws `UnsupportedOperationException`.

`addFirst`, `addLast`, `getFirst`, `getLast`, `removeFirst`, `removeLast`, and `reversed` methods are declared in `SequencedCollection`. `TreeSet` does implement `SequencedCollection` but since a `TreeSet` keeps its elements sorted, its `addFirst` and `addLast` methods throw `UnsupportedOperationException`.

Note that its `getFirst`, `getLast`, `removeFirst`, `removeLast` methods work fine.

### Question 49

The standard JDK provides thread safe collection classes such as `CopyOnWriteArrayList` and `ConcurrentMap`. It also provides `Collections.synchronizedXXX` methods that provides thread safe wrappers over regular collection classes.

Further, although `Vector` and `Hashtable` have been out of favor for quite a while now, they are thread safe!

Some operations may throw an `UnsupportedOperationException`. This exception type is unchecked, and code calling these operations is not required to explicitly handle exceptions of this type. For example, `TreeSet` implements `SequencedSet` but since it keeps its elements sorted, its `addFirst/addLast` method throw `UnsupportedOperationException`.

### Question 50

benefits of an array over an `ArrayList`

It consumes less memory. There's ambiguity though.In certain situation an `ArrayList` may consume a little bit more memory than an array (because of additional internal data structure and pointers), while in some other situation it may consume less (when your array is only half full).

Accessing an element in an array is faster than in `ArrayList`. Although very little, but a direct array access using an index is faster than calling a method on ArrayList.

Neither an `ArrayList` nor an array is thread safe. If you have multiple threads trying to add and remove elements from an `ArrayList` or an array, you have to write additional code to ensure thread safety.

arrays do not implement `Collection` interface. `ArrayList` does. This is actually an advantage of an `ArrayList` over an array.

An `ArrayList` resized dynamically at run time as per the situation. An array cannot be resized once created. This reduces the amount of boiler plate code that is required to do the same task using an array.