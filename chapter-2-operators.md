# OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER:

## Handling Date, Time, Text, Numeric and Boolean Values
- Use primitives and wrapper classes. 
- Evaluate arithmetic and boolean expressions, using the Math API and by applying precedence rules, type conversions, and casting.

**Operators**
A Java operator is a special symbol that can be applied to a set of variables, values, or literals—referred to as operands—and that returns a result.

**Types of Operators**
Three falvors of operators: Unary, Binary, and Ternary

**Operator Precedence**
Java more closely follows the rules for mathematics.

- The multiplication operator (*) has a higher precedence than the addition operator (+).
- The assignment operator (=) has the lowest order of precedence.
- If two operators have the same level of precedence, then Java guarantees left-to-right evaluation for most operators.

**Operator Precedence Flow:**

“Unary (R-L) > Math (Arithmetic) > Shift > Comparison > Equality > Bitwise AND-OR > Logical AND-OR > Ternary (R-L) > Assignment (R-L) > Arrow (R-L)”

**Tips:** When encountering code in your professional career in which you are not sure about the order of operation, feel free to add optional parentheses. While often not required, they can improve readability, especially as you’ll see with ternary operators.

For modulo operator (%), if the divisor is negative, then the negative sign is ignored. Negative values do change the behavior of modulus when applied to the dividend, though. That means, the modulo operation takes the sign of the dividend, ignoring the divisor altogether. For example,

```java
System.out.println(2 % 5);   // 2
System.out.println(7 % 5);   // 2
System.out.println(2 % -5);  // 2
System.out.println(7 % -5);  // 2
 
System.out.println(-2 % 5);  // -2
System.out.println(-7 % 5);  // -2
System.out.println(-2 % -5); // -2
System.out.println(-7 % -5); // -2
```

**Numeric Promotion**

**Numeric Promotion Rules**
1. If two values have different data types, Java will automatically promote one of the values to the larger of the two data types.
2. If one of the values is integral and the other is floating-point, Java will automatically promote the integral value to the floating-point value’s data type.
3. Smaller data types, namely, byte, short, and char, are first promoted to int any time they’re used with a Java binary arithmetic operator with a variable (as opposed to a value), even if neither of the operands is int. (unary increment/decrement operators are excluded)
4. After all promotion has occurred and the operands have the same data type, the resulting value will have the same data type as its promoted operands.

**Casting Values**
Casting is a unary operation where one data type is explicitly interpreted as another data type. 
- Casting is optional and unnecessary when converting to a larger or widening data type, but it is required when converting to a smaller or narrowing data type. 
- Without casting, the compiler will generate an error when trying to put a larger data type inside a smaller one.
- Casting primitives is required any time you are going from a larger numerical data type to a smaller numerical data type, or converting from a floating-point number to an integral value.

*Casting a numeric value may change the data type, while casting an object only changes the reference to the object, not the object itself.*

Casting Examples,

```java
// Compilation error
float egg = 2.0 / 9;        // DOES NOT COMPILE
int tadpole = (int)5 * 2L;  // DOES NOT COMPILE
short frog = 3 - 2.0;       // DOES NOT COMPILE

// Compilation error
int fish = 1.0;        // DOES NOT COMPILE
short bird = 1921222;  // DOES NOT COMPILE
int mammal = 9f;       // DOES NOT COMPILE
long reptile = 192_301_398_193_810_323;  // DOES NOT COMPILE

// Fixes
int fish = (int)1.0;
short bird = (short)1921222;  // Stored as 20678
int mammal = (int)9f;

// This still does not compile because the value is first interpreted as an int by the compiler and is out of range. 
long reptile = (long)192301398193810323;  // DOES NOT COMPILE

// The following fixes this code without requiring casting
long reptile = 192301398193810323L;
```

**Overflow and Underflow**
Overflow is when a number is so large that it will no longer fit within the data type, so the system “wraps around” to the lowest negative value and counts up from there, similar to how modulus arithmetic works.

There’s also an analogous underflow, when the number is too low to fit in the data type, such as storing -200 in a byte field.

**Numeric Promotion along with Casting Values**
```java
short mouse = 10;
short hamster = 3;

// Compilation error
short capybara = mouse * hamster;               // DOES NOT COMPILE
short capybara = (short)mouse * hamster;        // DOES NOT COMPILE
short capybara = 1 + (short)(mouse * hamster);  // DOES NOT COMPILE

// Fix
short capybara = (short)(mouse * hamster);
```

**Casting Values vs. Variables**
Revisiting our third numeric promotional rule, the compiler doesn’t require casting when working with literal values that fit into the data type. 
When working with values, the compiler had enough information to determine the writer’s intent. 
When working with variables, though, there is ambiguity about how to proceed, so the compiler reports an error. 

Compound operators are useful for more than just shorthand—they can also save you from having to explicitly cast a value.

The result (return value) of an assignment is an expression in and of itself equal to the value of the assignment. Examples,
```java
long wolf = 5;
long coyote = (wolf = 3);
System.out.println(wolf);   // 3
System.out.println(coyote); // 3

// healthy is assigned true value (return value of assignment is true here)
boolean healthy = false;
if(healthy = true)
   System.out.print("Good!");
```

**Comparing Values**
Two references are equal if and only if they point to the same object or both point to null.

In some languages, comparing null with any other value is always false, although this is not the case in Java.
```java
System.out.print(null == null);  // true
```

**`instanceof` Operator**
It is useful for determining whether an arbitrary object is a member (instance) of a particular class or interface at runtime.

Where polymorphism often comes into play is when you create a method that takes a data type with many possible subclasses.

It is considered a good coding practice to use the instanceof operator prior to casting from one object to a narrower type.

**Invalid `instanceof`**
`instanceof` with incompatible types will lead compilation error. If the compiler can determine that a variable cannot possibly be cast to a specific class, it reports an error. Example,
```java
public void openZoo(Number time) {
   if(time instanceof String) // DOES NOT COMPILE
      System.out.print(time);
}
```

**`null` and the `instanceof` operator**
Calling instanceof on the null literal or a null reference always returns false. Example,
```java
System.out.print(null instanceof Object);           // false
 
String noObjectHere = null;
System.out.print(noObjectHere instanceof String);   // false

System.out.print(null instanceof null);             // DOES NOT COMPILE
```

**Logical & Bitwise Operators**
The logical operators, (&), (|), and (^), may be applied to both numeric and boolean data types. 
When they’re applied to boolean data types, they’re referred to as logical operators. 
Alternatively, when they’re applied to numeric data types, they’re referred to as bitwise operators, as they perform bitwise comparisons of the bits that compose the number.

**Rules of Bitwise Operations**
1. Original number is returned if both operands are the same.
```java
int number = 70;
System.out.println(number);            // 70
System.out.println(number & number);   // 70
System.out.println(number | number);   // 70
```

2. Bitwise operations of a number and its negation, which returns 0 for bitwise AND (&), and -1 for bitwise OR (|).
```java
int negated = ~number;
System.out.println(negated);           // -71
 
System.out.println(number & negated);  // 0
System.out.println(number | negated);  // -1
```

1. The binary exclusive OR operator (^) returns 0 if both numbers are the same (bits are all 0s), and -1 (bits are all 1s) for a value with its negation.
```java
System.out.println(number ^ number);   // 0
System.out.println(number ^ negated);  // -1
```

**Short-circuit Evaluation** 
The conditional operators, often called short-circuit operators.
Logical AND (&) operator evaluates both sides of the expression.
Conditional AND (&&) operator does short-circuit evaluation.

In Java, logical operators && (AND) and || (OR) are short-circuit operators.
Java evaluates expressions left to right and stops early if the final result is already known.

Short-circuit evaluation is the process where a boolean expression stops being evaluated as soon as the overall outcome is certain, without checking all conditions. This is done to improve efficiency and prevent potential runtime errors like `NullPointerException`. 

**Benefits of Short-Circuit Evaluation**
1. Prevents runtime exceptions / Null Checks: A common use case is to prevent NullPointerException errors by checking if an object is null before attempting to access its methods or fields.
2. Performance optimization / Avoiding Expensive Operations: You can place computationally expensive method calls on the right side of a short-circuit operator, ensuring they only run if necessary.
3. Cleaner and safer code: Eliminates nested if statements. Encourages defensive programming.

**Unperformed Side Effect**
An "unperformed side effect" due to short-circuit evaluation occurs when an expression containing a side effect (such as a variable assignment, method call, or resource allocation) is skipped because the overall boolean result can be determined from the first operand alone. 
```java
int rabbit = 6;
boolean bunny = (rabbit>= 6) || (++rabbit <= 7);
System.out.println(rabbit); // prints 6, not 7
```

**Prvention from Unperformed Side Effect**
1. Avoid side effects in boolean expressions: The best practice is to keep boolean expressions clean and move any operations with side effects to separate statements or method calls outside the conditional logic.
2. Use non-short-circuit operators (if necessary): Java provides the bitwise operators & (AND) and | (OR) which always evaluate both operands. However, using these with booleans for this purpose is generally discouraged as it can make the code less readable and appear as a typo to other developers. 

**Unperformed Side Effects with Ternary Expression**
As we saw with the conditional operators, a ternary expression can contain an unperformed side effect, as only one of the expressions on the right side will be evaluated at runtime. 