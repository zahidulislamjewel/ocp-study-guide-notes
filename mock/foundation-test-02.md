## Question Review Notes

### Question 2

Return type can be any class since all objects can be cast to Object.

Note that the return type cannot be a primitive such as int or char. It must be a class. So it can be Integer or Character as well.

Version 1.5 onwards, Java allows covariant return types, which means that an overriding method can have its return type as any subclass of the return type of the overridden method.

Note that covariant return types is not applicable to primitives. So for example, if the overridden method returns int, the overriding method's return type must also be int. It cannot be short or long. It cannot even be Integer.

### Question 3

Monitor, Lock

It does not matter if a method does its job by acquiring monitors or not. What matters is whether ALL the methods that use a shared resource do their job after acquiring the same monitor or not. Because acquiring a monitor will not prevent any other method which does not use monitor (or uses a different monitor), from accessing the shared resource.

### Question 4

But remember that a member with default access is inherited only if both the classes are in same package.

Here are a few words from the Java Language Specification that are pertinent in this situation:
Members of a class that are declared private are not inherited by subclasses of that class. Only members of a class that are declared protected or public are inherited by subclasses declared in a package other than the one in which the class is declared.
Constructors and static initializers are not members and therefore are not inherited.

### Question 5

A TreeSet is a SequencedSet but remember that TreeSet keeps its elements sorted and so its addFirst/addLast methods throw UnsupportedOperationException.

`public class HashSet extends AbstractSet implements Set, Cloneable, Serializable`

This class implements the Set interface, backed by a hash table (actually a HashMap instance). It makes no guarantees as to the iteration order of the set; in particular, it does not guarantee that the order will remain constant over time. This class permits the null element.

### Question 7

Polymorphism causes a very slight degradation due to dynamic binding at run time (inefficiency).

Polymophism makes the code more reusable.

Polymophism makes the code more dynamic.

Polymophism allows the actual decision of which method is to be invoked to be taken at runtime based on the actual class of object. This is dynamic binding and makes the code more dynamic.

### Question 8
 
Classes declared as members of top-level classes can be declared static.

Anonymous classes cannot be declared static.

The modifier static pertains only to nested classes, not to top level or local or anonymous classes. That is, only classes declared as members of top-level classes can be declared static. 

Top leve/Package level classes, local classes (i.e. classes declared in methods) and anonymous classes cannot be declared static.

### Question 9

Text blocks

The compiler converts a Java text block into a String using a complicated set of rules


### Question 10

`null` can be a return value not a return type because `null` is not a type.

`var` cannot be used as a return type of a method.

### Question 11

variables defined in methods are called local variables (also known as automatic variables) where as instance members are defined in the class scope.

An instance member belongs to a single instance, not the class as a whole. An instance member is a member variable or a member method that belongs to a specific object instance. All non-static members are instance members.

### Question 12

`IntFunction`

It accepts an `int` but it returns an object.

`ToDoubleFunction`

It returns a `double`. There are several versions of `ToXXXFunction`. They are primitive returning versions of Function. For example, `ToIntFunction` returns an `int`.

### Question 13

"class level" (for class variables) means static fields and they can be accessed from anywhere (i.e. static as well as non-static methods) in the class (and from outside the class depending on their accessibility).
"instance level" (for instance variables) means the instance fields and they can be accessed only from instance methods in the class.

### Question 14

Exceptions

Exceptions provide the means to separate the details of what to do when something out of the ordinary happens from the main logic of a program.


### Question 15

Abstract classes and interfaces

Interfaces can have static methods (public as well as private). Interfaces cannot have protected methods. It cannot have non-public fields and instance fields.

Abstract classes can have instance fields but interfaces can't.

Fields of an interface are always public, static, and final. Fields of an abstract class can be static or instance and may have any accessibility (i.e. public, private, protected, or default).

An abstract class can have final methods but an interface cannot.

### Question 17

Wrapper classes

Very often it becomes necessary to represent a value of primitive type as if it were an object. There are following wrapper classes for this purpose: `Boolean`, `Byte`, `Character`, `Short`, `Integer`, `Long`, `Float`, and `Double`.  

Note that `Byte`, `Short`, `Integer`, `Long`, `Float` and `Double` extend from `Number` which is an abstract class. 

An object of type `Double`, for example, contains a field whose type is `double`, representing that value in such a way that a reference to it can be stored in a variable of reference type. These classes also provide a number of methods for converting among primitive values, as well as supporting such standard methods as `equals` and `hashCode`.  

It is important to understand that objects of wrapper classes are immutable.

### Question 18

A class cannot override the super class's constructor. Because constructors are not inherited (Very Important!).

### Question 19

The `break` statement is to break out of any loop completely. So the current iteration and any other remaining iterations of the loop will not execute.
Control is transferred to the first statement after the loop.

### Question 21

A try without resources statement must always have a catch or finally or both associated with it.

A regular try block must have a catch or a finally block associated with it. It may have both as well. So, for example, the following will not compile as the nested try block doesn't have any catch or finally block:

```java
try{    
  try{      
    //some code here    
  }    
  //must have catch, finally or both clauses here. 
} catch (Exception e} {   
  e.printStackTrace(); 
}
```

Note: try with resources may omit catch as well as finally blocks.

### Question 22

`java.util.concurrent.locks.Lock` interface's `lock()` method returns `void`, while its `tryLock()` returns `boolean`. 


So, the following code will not compile,

```java
var rlock = new ReentrantLock();         
var f1 = rlock.lock();         
System.out.println(f1);         
var f2 = rlock.lock();         
System.out.println(f2);
```

Had the code been:       

```java
var rlock = new ReentrantLock();      
var f1 = rlock.tryLock();      
System.out.println(f1);      
var f2 = rlock.tryLock();      
System.out.println(f2);  
```

It would have printed: 
```java
true true
```
  
Note that `java.util.concurrent.locks.ReentrantLock` class implements `java.util.concurrent.locks.Lock` interface.
 

### Question 23

`protected` accessibility

```java
public class MyClass {    
  protected int value = 10;  
} 
```

The field value can be read and modified from any class within the same package.

Note that since value is protected, a class in another package which extends MyClass will only inherit this variable, but it cannot read or modify the value of a variable of a MyClass instance. For example:

```java
// In a different package
class X extends MyClass {
  public static void main(String[] args) {
    // This will not compile, because X does not own MyClass's value.
    int a = new MyClass().value; 
                        
    // This will compile fine, because X inherits value.                        
    a = new X().value;  
  }
}
```

### Question 24

The default constructor is provided by the compiler only if the class does not define any constructor. 
It calls the no-args constructor of the super class calling `super()`.


### Question 25

Details about Text Blocks 

Reference: https://docs.oracle.com/en/java/javase/21/text-blocks/index.html

### Question 26

All arrays can be cloned using clone method.

Actually, the clone method is defined in Object class but it has protected access. All array classes override this method and make it public. 

The clone method returns a shallow clone of an array. For example, if you have, 

```java
Student[] sa1 = new Student[] { new Student(), new Student()}; //assuming a Student class exists, 
``` 

you can do: 
```java
Student[] sa2 = sa1.clone();  
```

sa2 will now point to a new array of Student objects. But the elements of this new array will point to the same Student objects that were there in the original array. 

In other words, when you clone an array, the array is cloned but the elements of the array are not cloned. Thus, `sa1 == sa2` will be false, but `sa1[0] == sa2[0]` will be true. 

```java
int[] a = new int[5];

System.out.println(a instanceof Object);        // true
System.out.println(a instanceof Cloneable);     // true
System.out.println(a instanceof java.io.Serializable); // true

System.out.println(int[].class.getName());   // [I
System.out.println(String[].class.getName()); // [Ljava.lang.String;
```

### Question 27

By default (i.e. no modifier) the member will be accessible only within the package.

We cannot specify visibility of local variables. They are always only accessible within the block in which they are declared.

A local variable (aka automatic variable) means a variable declared in a method. They don't have any accessibility. They are accessible only from the block they are declared in. Remember, they are not initialized automatically. You have to initialize them explicitly.

You cannot apply any modifier except final to a local variable, i.e., you cannot make them `transient`, `volatile`, `static`, `public`, and `private`. 

But you can apply access modifiers (`public`, `private` and `protected`) and `final`, `transient`, `volatile`, and `static` to instance variables. 

You cannot apply `native` and `synchronized` to any kind of variable.

### Question 27

`default` - Visible only inside the same package

`protected` - Visible inside the same package + visible to subclasses outside the package

Example,

Package a.b.c,

```java
package a.b.c;

public class Parent {
    int x = 10;            // default
    protected int y = 20;  // protected
}
```

Package x.y.z,

```java
package x.y.z;
import a.b.c.Parent;

public class Child extends Parent {
    public void test() {
        // System.out.println(x); // does not compile
        System.out.println(y);    // compiles
    }
}
```

x is default, so not visible outside package a.b.c
y is protected, so visible in subclass Child in another package x.y.z

**Very important protected rule**

In another package, `protected` does not mean “accessible through any parent object”. So, trying to access with parent object” will lead to compilation error. but, accessing through child object is fine.

### Question 31

`provides` and `uses` are used by a module definition that implements a service.

`provides`

The provider module must specify the service interface and the implementing class that implements the service interface. For example, 
`provides org.printservice.api.Print with com.myprinter.PrintImpl`

`uses`

A uses clause is used by the module that uses a service. For example, `uses org.printservice.api.Print`;

`requires`

The implementing module must require the module that defines the service interface.

For example, if an `abc.print` module implements an `org.printing.Print` service interface defined in `PrintServiceAPI` module using `com.abc.PrintImpl` class, then this is how its module-info should look:  

```java
module abc.print {    
  requires PrintServiceAPI; // required    
  // because this module defines the service interface org.printing.Print    
  provides org.printing.Print with com.abc.PrintImpl; 
} 
```

A module named `customer` that uses `Print` service may look like this: 

```java
module customer {    
  requires PrintServiceAPI; // required    
  // because this module defines the service interface org.printing.Print    
  uses org.printing.Print; // specifies that this module uses this service     

  // observe that abc.print module is not required. 
}
```

### Question 32

Valid methods for creating an `ExecutorService` instance,

```java
var es = Executors.newFixedThreadPool(n);

var es = Executors.newSingleThreadExecutor();

var es = Executors.newVirtualThreadPerTaskExecutor();
```

The last method was added In Java 21. It creates an `Executor` that starts a new virtual thread for each task. 

The number of threads created by the `Executor` is unbounded. This method is equivalent to invoking the `ExecutorService.newThreadPerTaskExecutor` (`ThreadFactory`) method with a thread factory that creates virtual threads.

You need to remember the following points about a few important classes in `java.util.concurrent` package:

1. `ExecutorService` interface extends `Executor` interface. While `Executor` allows you to execute a `Runnable`, `ExecutorService` allows you to execute a `Callable`.
2. `Executors` is a utility class that provides several static methods to create instances of `ExecutorService`. All such methods start with new e.g. `newSingleThreadExecutor()`.

You should at least remember the following methods: 

```java
newFixedThreadPool(int noOfThreads), 

newSingleThreadExecutor(), 
newSingleThreadScheduledExecutor(), 
newScheduledThreadPool(int corePoolSize), 

newCachedThreadPool(), 
newVirtualThreadPerTaskExecutor().
```

### Question 36

A plain try block does require at least a catch or a finally block but a try with resources block (introduced in Java 7, by the way), does not require any catch or finally blocks.

In a try-with-resources statement, the catch and finally blocks are executed after the resources are closed. (Although try-with-resources is not explicitly mentioned in the exam objectives, some candidates have reported getting questions that require a basic idea about it.)
 
A try can have at most one finally block.

A regular try block must have a catch or a finally block associated with it whether it is nested or not.

### Question 37

`finally` is always executed (even if you throw an exception in try or catch) but this is the exception to the rule. When you call `System.exit` method the JVM exits. So, there is no way to execute the finally block.

### Question 38

Code that uses generic collection classes can interoperate with code that uses raw collections classes because of type erasure

Type erasure means that a compiled java class does not contain any of the generic information that is present in the java file. In other words, the compiler removes the generic information from a java class when it compile it into byte code. 

For example, List<String> list; and List list; are compiled to the same byte code. Therefore, at run time, it does not matter whether you've used generic classes or not and this allows both kinds of classes to interoperate because they are essentially the same class to the JVM.

Type erasure ensures that no new classes are created for parameterized types; consequently, generics incur no runtime overhead.

Note: There's another concept named reification.

This is just the opposite of type erasure. Here, all the type information is preserved in the byte code. In Java, arrays are reified. For example,

```java
ArrayList[] alArray = new ArrayList[1];         
Collection[] cArray = alArray;         
cArray[0] = new HashSet();
```

The above code will compile fine. But it will throw an `java.lang.ArrayStoreException` at run time because the byte code contains the information that cArray actually points to an array of ArrayLists and not of HashSets.

### Question 39

A record CAN define any instance field explicitly.

Instance fields are created in a record using the parameters given in the record header.

A record CANNOT define private instance fields explicitly.

A record may inherit methods from an interface (default methods).

Default methods of an interface are inherited by a record. Even fields of an interface (which are always static) are inherited for the purpose of accessing them using the reference of a record.

A record may define instance methods (but not instance fields).

A record may have at most one varargs component.

A record may or may not implement the `java.io.Serializable` interface.

The serialization mechanism treats instances of a record class differently than ordinary serializable or externalizable objects. In particular, a record object is deserialized using the canonical constructor.

<Come Back for the Rules; Explanation>

### Question 40

`IntFunction` is most suitable to process a large collection of int primitives and return processed data for each of them.

Using the regular functional interfaces by parameterizing them to Integer is inefficient as compared to using specially designed interfaces for primitives because they avoid the cost of boxing and unboxing the primitives. 

Now, since the problem statement requires something to be returned after processing each int, you need to use a `Function` instead of a `Consumer` or a `Predicate`.

Therefore, `IntFunction` is most appropriate in this case.

### Question 41

`final` keyword prevents a class from being subclassed and a method from being overridden. For example,

```java
// This class cannot be sub-classed
final class Car { }
```

On the other hand the following class won't compile,

```java
sealed class Car { }
```

This will not compile because a sealed class must mention the list of subclasses are permitted to extend this class in its permits clause. 

Note: The permits clause is optional for nested sealed types.

A class can be extended unless it is declared final. 

An nested class can be declared static and still be extended. 

Notice the distinction. For classes, final means it cannot be extended, while for methods, final means it cannot be overridden in a subclass.

The `native` keyword can only be used on methods, not on classes and instance variables.

### Question 42

The following interfaces are in java collection framework

`Set`, `Collection`, `Map`, `NavigableMap`.

`BitSet` is not.

This is not required for the exam but in case you want to know what a `BitSet` is:

```java
public class BitSet extends Object implements Cloneable, Serializable
```

This class implements a vector of bits that grows as needed. 

Each component of the bit set has a boolean value. 

The bits of a BitSet are indexed by nonnegative integers.

Individual indexed bits can be examined, set, or cleared. 

One BitSet may be used to modify the contents of another BitSet through logical AND, logical inclusive OR, and logical exclusive OR operations.

By default, all bits in the set initially have the value false.

Every bit set has a current size, which is the number of bits of space currently in use by the bit set. 

Note that the size is related to the implementation of a bit set, so it may change with implementation. 

The length of a bit set relates to logical length of a bit set and is defined independently of implementation.

### Question 43

The extends clause is used to specify that a class extends another class and thereby inherits the instance members of that class.
 
Note that only those instance members that are accessible in the subclass are considered to be inherited. Thus, private instance members are not inherited by any subclass, and instance members with default access are not inheritied by the subclass if the subclass is in a different package. 

A subclass can be declared abstract regardless of whether the superclass was declared abstract. 

A class cannot be declared abstract and final at the same time. This restriction makes sense because abstract classes need to be subclassed to be useful and final forbids subclasses.

The visibility of the class is not limited by the visibility of its members. A class with all the members declared as private can still be declared public and an inner class class having all public members may be declared as private.

### Question 45

An overriding method is allowed to change the return type to any subclass of the original return type, also known as covariant return type. 

This does not apply to primitives, in which case, the return type of the overriding method must match exactly to the return type of the overridden method.

### Question 46

The switch statement transfers control to one of several statements or expressions, depending on the value of an expression. This expression is called "switch selector expression".

```java
switch ( Switch selector Expression ) {
  SwitchBlock
}
```

switch selector expression of type int and case label value of type char or vice versa is possible.

But, switch selector expression of type Integer and case label value of type char is not possible.

switch expression of type char and case label value of type byte is not work because a byte may have negative values which cannot be assigned to a char.

<Come Back for the Rules; Explanation>

### Question 47

An effectively final variable means even though it is not declared final, it is never assigned a value again throughout the code after being assigned a value at the time of declaration.

### Question 48

loss of precision while using float and double casting from int.


### Question 49

<Come Back for the Rules; Explanation>

### Question 

### Question 

### Question 

### Question 

### Question 

### Question 50

An anonymous class can be declared in a static method. Example,

```java
abstract class SomeClass {   
    public abstract void m1(); 
}

public class TestClass {
  // static method containing an anonyomous class
  public static SomeClass getSomeClass() {
    return new SomeClass() {
        public void m1() {}
    };
  }
}  
```
