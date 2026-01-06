# Chapter 3: Making Decisions

## OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER:

### Controlling Program Flow

- Create program flow control constructs including if/else, switch statements and expressions, loops, and break and continue statements.

### Using Object-Oriented Concepts in Java
- Implement inheritance, including abstract and sealed types as well as record classes. 
- Override methods, including that of the Object class. Implement polymorphism and differentiate between object type and reference type. 
- Perform reference type casting, identify object types using the instanceof operator, and pattern matching with the instanceof operator and the switch construct.

---

**Expression, Statements and Blocks**

An <u>expression</u> in Java is a combination of variables, literals, operators, and method calls that is evaluated to produce a single value. Examples,

```java
x + 5              // arithmetic expression, evaluates to int
a > b              // relational expression, evaluates to boolean
isValid()          // method call expression, evaluates to boolean
i++                // increment expression, evaluates to int (old value)
```

A <u>statement</u> in Java is a complete unit of execution, typically terminated with a semicolon (;).
A statement may contain one or more expressions. Examples,

```java
int x = 10;             // declaration statement
x++;                    // expression statement
System.out.println(x);  // method invocation statement
return x;               // return statement

```

A <u>block</u> in Java is a group of zero or more statements enclosed within balanced braces {}.
A block can be used anywhere a single statement is allowed. Examples,

```java
// initilizer block
{
    int a = 5;
    int b = 10;
    System.out.println(a + b);
}

// conditional block
if (x > 0) {
    System.out.println("Positive");
    x--;
}
```

**Summary**

*Expressions produce values, statements do work (with those values), blocks group statements.*

**Shortening Code with Pattern Matching**

Pattern matching is a technique of controlling program flow that only executes a section of code that meets certain criteria. Example,

```java
void compareIntegers(Number number) {
    if (number instanceof Integer data) {
        System.out.print(data.compareTo(5));
    }
}
```

**Reassigning Pattern Variables**

While possible, it is a bad practice to reassign a pattern variable since doing so can lead to ambiguity about what is and is not in scope. Example,

```java
if (number instanceof Integer data) {
   data = 10;   // Bad coding practice
}
```

The reassignment can be prevented with a `final` modifier, but it is better not to reassign the variable at all.

```java
if (number instanceof final Integer data) {
   data = 10;   // DOES NOT COMPILE
}
```

Pattern matching supports an optional conditional clause, declared as a boolean expression. This can be used to filter data out, such as in the following example,

```java
void printIntegersGreaterThan5(Number number) {
   if (number instanceof Integer data && data.compareTo(5) > 0)
      System.out.print(data);
}
```

The `instanceof` operator always evaluates `null` references to false. The same holds for pattern matching. Example,

```java
String noObjectHere = null;
 
if(noObjectHere instanceof String) {
   System.out.println("Not printed");
}
 
if(noObjectHere instanceof String s) {
    System.out.println("Still not printed");
}
 
if(noObjectHere instanceof String s && s.length() > -1) {
    System.out.println("Nope, not this one either");
}
```

**Pattern Variable Supported Types**

The type of the pattern variable must be a compatible type, which includes the same type, a subtype, or a supertype of the reference variable. Example,
```java
Number bearHeight = Integer.valueOf(123);

if (bearHeight instanceof Integer i) {}
if (bearHeight instanceof Number n) {}
if (bearHeight instanceof String s) {}  // DOES NOT COMPILE, incompatible type
if (bearHeight instanceof Object o) {}
```

**Flow Scoping**

The compiler applies flow scoping when working with pattern matching. 

Flow scoping means the variable is only in scope when the compiler can definitively determine its type. 

Flow scoping is unlike any other type of scoping, in that it is not strictly hierarchical. It is determined by the compiler based on the branching and flow of the program.

Understanding the way flow scoping works is very important. In particular, it is possible to use a pattern variable outside of the if statement, but only when the compiler can definitively determine its type.
Example,

```java
void printOnlyIntegers(Number number) {
    if (!(number instanceof Integer data)) {
        return;
    }
    System.out.print(data.intValue());
}
```
This code does compile. The method returns if the input does not inherit Integer. This means that when the last line of the method is reached, the input must inherit Integer, and therefore data stays in scope even after the if statement ends. 

Rewriting the above code with else branch,

```java
void printOnlyIntegers(Number number) {
    if (number instanceof Integer data) {
        System.out.print(data.intValue());
    } else {
        return;
    }
}
```

**Swith Statement and Switch Expression**

The primary difference between the two (aside from a lot of syntax differences!) is that a <u>switch expression</u> must return a value, while a <u>switch statement</u> does not.

Both switch types support an optional default clause. With switch expressions, a default clause is often required, as the expression must return a value (must be exhaustive).

Unlike a switch statement, a switch expression often requires a semicolon (`;`) after it, such as when it is used with the assignment operator (`=`) or a return statement. This has more to do with how the switch expression is used than the switch expression itself.

A switch expression also requires a semicolon (`;`) after each case expression that doesn’t use a block.

A switch statement is not required to contain any case clauses. This is perfectly valid: `switch (month) {}`

**Switch Variable Types**

`switch` has a target variable that is not evaluated until runtime. The following is a list of all data types supported by `switch`:

- `int` and `Integer`
- `byte` and `Byte`
- `short` and `Short`
- `char` and `Character`
- `String`
- `enum` values
- All object types (when used with pattern matching)
- `var` (if the type resolves to one of the preceding types)

*Notice that `boolean`, `long`, `float`, and `double` are not supported in switch statements and expressions.*

**Enum**

An enumeration, or enum, is a type in Java that represents a fixed set of constants.

Think of an enum as a class type with predefined object values known at compile time.

Since Java 21, switch statements and expressions support pattern matching, which means any object type can now be used as a switch variable, provided the pattern matching is used. 

**Accepted Case Values**

Not just any values can be used in a case clause. 

First, the values in each case clause must be <u>compile-time constant</u> values. This means we can use only literals, enum constants, or `final` constant variables.

By `final` constant, we mean that the variable must be marked with the `final` modifier and initialized with a literal value in the same expression in which it is declared. 

For example, we can’t have a case clause value that requires executing a method at runtime, even if that method always returns the same value. Example, 

```java
final int getCookies() { return 4; }
void feedAnimals() {
    final int bananas = 1;  // final
    int apples = 2;         // not final
    int numberOfAnimals = 3;            // not final
    final int cookies = getCookies();   // not final, depends on runtime
    switch (numberOfAnimals) {
        case bananas:       // Compiles, resolved at compile time, compile time final 
        case apples:        // DOES NOT COMPILE
        case getCookies():  // DOES NOT COMPILE, Depends on Runtime
        case cookies :      // DOES NOT COMPILE
        case 3 * 5 :        // Compiles, resolved at compile time
   } 
}
```

*Case value must be compile time final*

The data type for case clauses must match the data type of the switch variable. 

When the switch variable is an enum type, then the case clauses must be the enum values.

**Switch Expression**

Just as case values have to use a consistent data type, the switch expression must return a consistent value. 

**Exhausting the switch Branches**

Unlike a switch statement, a switch expression must return a value. So, the switch variables (cases values) must be exhaustive.

A switch is said to be exhaustive if it covers all possible values. All switch expressions must be exhaustive, which means they must handle all possible values. 

There are three ways to write an exhaustive switch:

1. Add a default clause.
2. If the switch takes an enum, add a case clause for every possible enum value.
3. Cover all possible types of the switch variable with pattern matching.

When writing switch expressions, it may be a good idea to add a default branch, even if you cover all possible values. This means that if someone modifies the enum with a new value, your code will still compile.
Example,

````java
enum Season { SPRING, SUMMER, FALL, WINTER }

String getWeatherCoveredAll(Season value) {
   return switch (value) {
        case WINTER -> "Cold";
        case SPRING -> "Rainy";
        case SUMMER -> "Hot";
        case FALL   -> "Warm";
        default     -> throw new RuntimeException("Unsupported Season");
    };
}
````

**Using the `yield` Statement**

A switch expression supports both case expressions and case blocks, the latter of which is denoted with braces (`{}`).

A switch expression must return a value. But how do you return a value from a case block? 

We could use a return statement, but that ends the method, not just the switch expression! 

The `yield` statement allows the case clause to return a value.

Think of the `yield` keyword as a return statement within a switch expression. 

Because a switch expression must return a value, a `yield` is often required within a case block. 
Example,

```java
int fish = 5;
int length = 12;
var name = switch (fish) {
    case 1 -> "Goldfish"; 
    case 2 -> { yield "Trout"; }
    case 3 -> {
        if (length> 10) yield "Blobfish";
        else yield "Green";
    }
    case 4 -> {
        throw new RuntimeException("Unsupported value");
    }
    default -> "Swordfish";
};
```

**Using Pattern Matching with switch**

Since Java 21 the pattern matching has been extended to switch. 
Example,

```java
void printDetails(Number height) {
   String message = switch (height) {         
      case Integer i -> "Rounded: " + i;
      case Double d  -> "Precise: " + d;
      case Number n  -> "Unknown: " + n; // type matches, default clause not needed (exhaustive)
   };
   System.out.print(message);
}
```

Pattern Variable can of the same type or sub type of the Reference Variable.

The pattern matching variable exists only within the case branch for which it is defined. This allows us to reuse the same name for two case branches.

A guard clause (using `when keyword`) can be included, which is an optional conditional clause that can be added to a case branch.

With switch, the `when` keyword is required between the variable and the conditional expression. 
Example,

```java
String getTrainer(Number height) {
   return switch (height) {         
      case Integer i when i > 10 -> "Joseph";
      case Integer i -> "Daniel";
      case Double num when num <= 15.5 -> "Peter";
      case Double num -> "Kelly";
      case Number num -> "Ralph";
   };
}
```

One advantage of guards is that now switch can do something it’s never done before: it can handle ranges. 

**Acceptable Pattern Variable Types in Pattern is Switch Expression**

One of the simplest rules when working with switch and pattern matching is that the type can’t be unrelated. It must be the same type as the switch variable or a subtype. 
Example,

```java
Number fish = 10;
String name = switch (fish) {
    case Integer freshWater -> "Bass";
    case Number saltWater   -> "ClownFish";
    case String s           -> "Shark";  // DOES NOT COMPILE
};
```

**Ordering switch Branches**

The order of case and default clauses for <u>switch statement</u> matters, because more than one branch might be reached during execution. 

For <u>switch expressions</u> that don’t use pattern matching, ordering isn’t important, as only one branch can be reached.

When working with pattern matching, the order matters regardless of the type of switch!

Ordering branches is also important if a when clause is used.

**Exhaustive switch Statements**

When using pattern matching, switch statements must be exhaustive too as like switch expression.

When the last case clause uses a pattern variable that matches the switch expression’s reference type, the switch is exhaustive and a default clause is not required. In that case, adding a default clause will fail the code to compile. 
Example,

```java
Number zooPatrons = Integer.valueOf(1_000);
switch (zooPatrons) {
    case Integer count -> System.out.print("Welcome: " + count);
    case Number count  -> System.out.print("Enjoy the zoo!");   // default case, dominating last clause
    default            -> System.out.print("Zoo is closed");    // fails to compile, unreachable code
}
```

**Using `null` is switch**

If the switch variable is null at runtime, it might throw exception at runtime (most likely `NullPointerException`), even if we can try using a default clause. The solution is using a `null` case (Since Java 21). 
Example,

```java
String fish = null;
System.out.print(switch (fish) {
    case "ClownFish" -> "Hello!";
    case "BlueTang"  -> "Hello again!";
    case null        -> "Unknown!"; // Absence of this would cause NullPointerException
    default          -> "Goodbye";
});
```

Anytime case `null` is used within a switch, then the switch statement is considered to use pattern matching. 

As you should remember from the previous section, that means the switch statement must be exhaustive. Adding a default branch allows the code to compile. So, the following switch statement does not compile,

```java
String fish = null;
switch (fish) {     // DOES NOT COMPILE
    case "ClownFish": System.out.print("Hello!");
    case "BlueTang":  System.out.print("Hello again!");
    case null:        System.out.print("What type of fish are you?");
}
```

Since using case `null` implies pattern matching, the ordering of branches matters anytime case `null` is used. While case `null` can appear almost anywhere in switch, it cannot be used after a default statement. 

Pattern matching alters two common rules with switch: 

1. A switch statement now must be exhaustive when pattern matching is used.
2. The ordering of switch expression branches is now important.

**Iteration, Loops**

The variables in the initialization block must all be of the same type in for loop.

We can use a for-each loop on a collection that implements Iterable.

In for each loop, the right side must be an array or collection of items, such as a List or a Set. This does not include all of the Collections Framework classes or interfaces, but only those that implement or extend that Collection interface. 

For example, `Map` is not supported in a for-each loop, although Map does include methods that return Collection instances like `Map.entrySet()` method.

**Optional Labels in Loop with break, continue**

```java
int[][] myComplexArray = {{5,2,1,3}, {3,9,8,9}, {5,7,12,7}};
 
OUTER_LOOP:  for (int[] mySimpleArray : myComplexArray) {
    INNER_LOOP:  for (int num: mySimpleArray) {
        System.out.print(num + "\t");
        if(num > 10) {
            break OUTER_LOOP;
        }
    }
    System.out.println();
}
```

While the `break` statement transfers control to the enclosing statement, the `continue` statement transfers control to the boolean expression that determines if the loop should continue. In other words, it ends the current iteration of the loop. 

Also, like the `break` statement, the `continue` statement is applied to the nearest inner loop under execution, using optional label statements to override this behavior. Examples of `continue` statement with label,

```java
CLEANING: for (char stables = 'a'; stables <= 'd'; stables++) {
    for (int leopard = 1; leopard <= 3; leopard++) {
        if (stables == 'b' || leopard == 2) {
            continue CLEANING;
        }
        System.out.println("Cleaning: " + stables + ", " + leopard);
    }
}
```

`break` is used to break out the current iteration of enclosing loop (or the label)

`continue`  is used to skip the current iteration and jump to next (or the label)