# OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER:

## Handling Date, Time, Text, Numeric and Boolean Values
- Use primitives and wrapper classes. 
- Evaluate arithmetic and boolean expressions, using the Math API and by applying precedence rules, type conversions, and casting.
- Manipulate text, including text blocks, using String and StringBuilder classes.
- Manipulate date, time, duration, period, instant and time-zone objects including daylight saving time using Date-Time API.
  
# Working with Arrays and Collections
- Create arrays, List, Set, Map and Deque collections, and add, remove, update, retrieve and sort their elements.

In the context of an application programming interface (API), an interface refers to a group of classes or Java interface definitions giving you access to functionality.

**Strings Manipulation**

**String Concatenation Rules**
1. If both operands are numeric, + means numeric addition.
2. If either operand is a String, + means concatenation.
3. The expression is evaluated left to right.


**Code Points**

- Java strings use **Unicode (UTF-16)**
- A `char` is a 16-bit UTF-16 code unit, not always a full character
- Some Unicode characters (for example, smart quotes or certain symbols) require two `char` values (surrogate pairs)
- A code point is the numeric Unicode value (`int`) that represents one complete logical character
- Use code point–based methods when working with non-ASCII text

Examples:

```java
String s = "W’€";
System.out.println(s.charAt(1));       // returns a char (UTF-16 unit)
System.out.println(s.codePointAt(1));  // 8217
```

> `char` counts UTF-16 code units, while code points (`int`) represent actual Unicode characters.


`equals(Object obj)` takes an Object rather than a String. This is because the method is the same for all objects. If an non-string (object) is passed in, it will just return false. 
By contrast, the `equalsIgnoreCase(String str)` method applies only to String objects, so it can take the more specific type as the parameter.

**Overriding `toString()`, `equals(Object)`, and `hashCode()`**

- All three methods are defined in `java.lang.Object` and have default implementations.
- `toString()` is called when an object is printed or concatenated with a `String`; it is commonly overridden to return a meaningful description using instance fields.
- `equals(Object)` is used to compare objects for logical equality; the default implementation uses `==`, so override it when equality depends on object state.
- `hashCode()` must be overridden whenever `equals(Object)` is overridden to maintain consistency.
- Contract rule: if `a.equals(b)` is `true`, then `a.hashCode() == b.hashCode()` must also be `true`.
- Inconsistent `equals()` and `hashCode()` can cause incorrect behavior in hash-based collections like `HashMap` and `HashSet`.

> Override `toString()` for readability, override `equals()` for logical equality, and always override `hashCode()` together with `equals()`.

The strip() method does everything that trim() does, but it supports Unicode.

**`indent()` and Whitespace Normalization**

`indent()` normalizes whitespace:
1. Ensures the string ends with a line break.
2. Converts all line breaks to `\n`, regardless of OS (`\r\n` on Windows, `\n` on Mac/Unix).

**Checking for Empty or Blank Strings**

**Difference Between `isEmpty()` and `isBlank()`**

`isEmpty()` returns `true` only if the string has length 0.
`isBlank()` returns `true` if the string is empty or contains only whitespace.

```java
"".isEmpty();   // true
" ".isEmpty();  // false
"".isBlank();   // true
" ".isBlank();  // true
```

> `isEmpty()` checks length, `isBlank()` checks for non-whitespace characters.

**String formatting**
By default, %f displays exactly six digits past the decimal. 
If you want to display only one digit after the decimal, you can use %.1f instead of %f. 
The format() method relies on rounding rather than truncating when shortening numbers. 
For example, 90.250000 will be displayed as 90.3 (not 90.2) when passed to format() with %.1f.

```java
var name = "James";
var score = 90.25;
var total = 100;
System.out.println("%s:%n   Score: %.2f out of %d".formatted(name, score, total));

System.out.format("[%f]", pi); // [3.141593]
System.out.format("[%12.8f]", pi); // [  3.14159265]
System.out.format("[%012f]", pi); // [00003.141593]
System.out.format("[%12.2f]", pi); // [        3.14]
System.out.format("[%.3f]", pi); // [3.142]
```

**Method Chaining**
```java
String result = "AniMaL   ".trim().toLowerCase().replace('a', 'A');
System.out.println(result);
```

**StringBuilder vs StringBuffer**
```java
StringBuilder sb = new StringBuilder();
for (char ch = 'a'; ch <= 'z'; ch++) {
    sb.append(ch);
}
System.out.println(sb);
System.out.println(sb.length());
System.out.println(sb.toString());
```
**Comparing `equals()` and `==`**
The authors of the String class implemented a standard method called equals() to check the values inside the String rather than the string reference itself. If a class doesn’t have an equals() method, Java determines whether the references point to the same object, which is exactly what == does.

The authors of StringBuilder did not implement equals(). If you call equals() on two StringBuilder instances, it will check reference equality. You can call toString() on StringBuilder to get a String to check for equality instead.

**The String Pool**

- A special area in the JVM that stores literal strings and constants to save memory.
- Reuses identical literals, so multiple variables can point to the same reference.
- Strings created at runtime (via `new String()` or runtime operations) are not automatically pooled.

Examples of pooled literals:

```java
String x = "Hello";
String y = "Hello";
System.out.println(x == y);  // true, same reference
```

Runtime or constructor-created strings:

```java
String z = " Hello".trim();
System.out.println(x == z);  // false, new object
String w = new String("Hello");
System.out.println(x == w);  // false
```

Using `intern()` to force pooling:

```java
String a = "Hello";
String b = new String("Hello").intern();
System.out.println(a == b);  // true
```

Compile-time constants are pooled:

```java
String s1 = "rat" + 1;               // pooled
String s2 = "r" + "a" + "t" + "1";   // pooled
System.out.println(s1 == s2);         // true
```

Non-compile-time expressions are not pooled unless interned:

```java
String s3 = "r" + "a" + "t" + new String("1");
System.out.println(s1 == s3);         // false
System.out.println(s1 == s3.intern());// true
```

Key points for OCP exam:

1. `==` compares references, not content.
2. `equals()` compares actual string content, so always use this for string comparison.
3. String literals and compile-time constants are pooled.
4. `new String()` and runtime expressions create new heap objects.
5. `intern()` forces a string to use the pool reference if available.

> Understand string pooling and equality rules; never rely on `==` for content comparison, only `equals()`.

> Literals and constants are automatically pooled; runtime-generated strings are not, unless explicitly interned. This reduces memory usage by reusing identical strings.


**Understanding Arrays**
An array is an area of memory on the heap with space for a designated number of elements. An array is an ordered list. It can contain duplicates.

Array creation,
```java
int[] numbers = new int[3];

int[] moreNumbers = new int[] {42, 55, 99};

int[] moreNumbers = {42, 55, 99}; // anonymous array because type and size not specified but inferred
```

**Important Array Methods**
```java
Arrays.sort(arr)
Arrays.binarySearch(arr, key)
Arrays.equals(arr1, arr2)
Arrays.compare(arr1, arr2)
Arrays.mismatch(arr1, arr2)
```

`null` is smaller than any other value.

variable argument i.e. varargs is supported when passed a String array to a methods. Exmaple,
`processArray(String... args)`

Java supports asymmetric array.
```java
String[][] asymmetricArray = {
        { "A1", "A2", "A3" },
        { "B1", "B2" },
        { "C1", "C2", "C3", "C4" }
};
```        

**Math API Core Methods**
```java
Math.min(x, y)
Math.max(x, y)
Math.round(x.z)
Math.ceil(x.d)
Math.floor(x.d)
Math.pow(x,z)
Math.random()
```

**Using BigInteger and BigDecimal**
Chaining with BigInteger
```java
var bigNum = BigInteger.valueOf(5)
        .pow(2)
        .add(BigInteger.valueOf(5))
        .multiply(BigInteger.valueOf(30))
        .divide(BigInteger.TEN)
        .max(BigInteger.valueOf(60))
        .min(BigInteger.valueOf(80));
System.out.println(bigNum);
```

**When to use `BigInteger` and `BigDecimal`**

- `BigInteger` is used for integer values that exceed the range of primitive types like `int` and `long`.
- Large integer literals that do not fit in `long` will cause a compilation error, but `BigInteger` can represent them safely.

Example:

```java
BigInteger bigNumber = new BigInteger("12345123451234512345");
System.out.println(bigNumber);


long n = 12345123451234512345L; // This does not compile because the value is too large for long
```

- `BigDecimal` is used for decimal values where precision is critical.
- `float` and `double` may introduce floating-point errors due to binary representation of decimal numbers.
- This is especially problematic for financial calculations.
- Using `BigDecimal` avoids this precision issue and produces the expected result.

```java
// floating-point precision error
double amountInCents1 = 64.1 * 100;
System.out.println(amountInCents1); // 6409.999999999999

BigDecimal amountInCents2 = BigDecimal.valueOf(64.1)
        .multiply(BigDecimal.valueOf(100));
System.out.println(amountInCents2); // 6410.0
```

> use `BigInteger` for very large whole numbers and `BigDecimal` for accurate decimal calculations, especially when dealing with money.

**Working with Dates and Times (`java.time`)**

- Modern date/time API is in `java.time` (`java.util.Date` is not on the exam)
- “Date” in exam questions may mean full calendar date or day of month; read carefully
- `LocalDate` represents date only (year, month, day), no time, no zone. Example: `LocalDate.of(2025, 1, 20)`
- `LocalTime` represents time only (hour, minute, second, nanos), no date, no zone. Example: `LocalTime.of(6, 15)`
- `LocalDateTime` represents date + time, no zone. Example: `LocalDateTime.of(date, time)`
- `ZonedDateTime` represents date + time + time zone. Example: `ZonedDateTime.of(dateTime, ZoneId.of("Asia/Dhaka"))`
- All date/time classes have `now()` to get current values
- Time zones are represented by `ZoneId`, backed by the IANA time zone database
- GMT and UTC both represent time zone zero
- Time zone offsets may appear as `+02:00`, `GMT+2`, or `UTC+2` (same meaning)
- Months in `java.time` start from 1, not 0
- Date/time objects are created using static factory methods (`of()`, `now()`)
- Constructors are private; `new LocalDate()` does not compile
- Passing invalid values to `of()` throws `DateTimeException`
- All date/time classes are immutable
- Methods like `plusDays()`, `minusHours()` return new objects and must be reassigned
- Ignoring the return value means no change occurs
- `LocalDate` cannot add time units like minutes or seconds
- `LocalTime` cannot add date units like days or months
- Chaining date/time methods is allowed and common
- Leap year rule: divisible by 4, not by 100 unless divisible by 400

**Exam Tricks to Remember**

- Forgetting to reassign after `plus()` or `minus()`
- Using time methods on `LocalDate`
- Using date methods on `LocalTime`
- Assuming months are zero-based
- Trying to use constructors instead of factory methods
- Confusing `LocalDateTime` with `ZonedDateTime`
- Missing that DST and offsets apply only with time zones

Examples,
```java
// Date
LocalDate date = LocalDate.of(2025, 12, 27);
LocalDate parsedDate = LocalDate.parse("2023-06-15");

// Time
LocalTime time = LocalTime.of(14, 30);
LocalTime parsedTime = LocalTime.parse("09:45:30");

// Date + Time
LocalDateTime dateTime = LocalDateTime.of(date, time);

// Zoned Date-Time
ZonedDateTime zoned = ZonedDateTime.of(dateTime, ZoneId.of("Asia/Dhaka"));

// Offset Date-Time
OffsetDateTime offset = OffsetDateTime.now(ZoneOffset.of("+06:00"));

// Immutability
LocalDate future = date.plusDays(10);
```

There are few conversion methods in `LocalDate`, `LocalTime`, `LocalDateTime`
```java
var date = LocalDate.of(2025, Month.FEBRUARY, 20);  // 2025-02-20
var differentDay = date.withDayOfMonth(15);         // 2025-02-15
var differentMonth = date.withDayOfYear(3);         // 2025-01-03
var allChanged = date.withYear(2026)
                     .withMonth(4)
                     .withDayOfMonth(10);           // 2026-04-10

// methods to convert from one type to another
var date = LocalDate.of(2025, Month.MARCH, 3);
var withTime = date.atTime(5, 30);  // 2025-03-03T05:30
var start = date.atStartOfDay();    // 2025-03-03T00:00
```

**Working with Periods**
January 1, 1970, referred to as the epoch, Unix started using this date for date standards, so Java reused it.

Creating Period instance,
```java
var annually = Period.ofYears(1);            // every 1 year
var quarterly = Period.ofMonths(3);          // every 3 months
var everyThreeWeeks = Period.ofWeeks(-3);    // every 3 weeks going backwards
var everyOtherDay = Period.ofDays(2);        // every 2 days
var everyYearAndAWeek = Period.of(1, 0, 7);  // every year plus 1 week
```

**Working with Duration**

Create a Duration using a number of different granularities:
```java
var daily = Duration.ofDays(1);               // PT24H
var hourly = Duration.ofHours(1);             // PT1H
var everyMinute = Duration.ofMinutes(1);      // PT1M
var everyTenSeconds = Duration.ofSeconds(10); // PT10S
var everyMilli = Duration.ofMillis(1);        // PT0.001S
var everyNano = Duration.ofNanos(1);          // PT0.000000001S
```

**Period vs Duration**

Here is a **slightly more descriptive but still exam-note friendly explanation**, without overusing bullet points.

---

### Period vs Duration (java.time)

`Period` and `Duration` both represent amounts of time, but they are **not interchangeable**.

- A Period is date-based. It works in terms of years, months, and days and is designed for calendar calculations. Because of this, `Period` can be applied to date-based classes like `LocalDate`. When you add a `Period` to a date, Java adjusts the calendar date correctly.

- A Duration is time-based. It works in terms of seconds and nanoseconds, even when it is created using `ofDays()`. Internally, a duration is always measured as an exact number of seconds. This makes it suitable only for classes that contain a time component.

This difference explains the key exam behavior:

```java
LocalDate date = LocalDate.of(2025, 5, 25);

date.plus(Period.ofDays(1));    // works fine, outputs 2025-05-26
date.plus(Duration.ofDays(1));  // runtime exception
```

`LocalDate` has no concept of seconds or time, so adding a `Duration` (which is time-based) is not allowed and fails at runtime.

> use Period when working with dates, and use Duration when working with time. 
> If a class contains both date and time (such as `LocalDateTime` or `ZonedDateTime`), both Period and Duration can be used.

*Period follows the calendar, Duration follows the clock.*

**Working with Instants `java.time.Instant`**
The `Instant` class represents a specific moment in time in the GMT time zone. 

An `Instant` is a point in time. `LocalDateTime` cannot bed converted to an `Instant`.
A `LocalDateTime` does not contain a time zone, and it is therefore not universally recognized around the world as the same moment in time.

Here’s a **concise, descriptive OCP-note style explanation** for `Instant`:

The `Instant` class represents a specific moment in time in GMT (UTC). It is commonly used to measure elapsed time or to mark a point in time precisely, independent of any time zone.

We can use `Instant` to measure durations:

```java
Instant start = Instant.now();
// perform some operation
Instant end = Instant.now();

Duration duration = Duration.between(start, end);
System.out.println(duration.toMillis()); // milliseconds elapsed
```

> `Instant` cannot be created directly from a `LocalDateTime` because it lacks a time zone, and therefore it does not represent a globally recognized instant.
> Use `Instant` for precise time measurements, comparisons, or timestamps in GMT.

*Instant = exact point in GMT; ZonedDateTime = local time + zone.*

**Daylight Saving Time**

Some countries observe daylight saving time. This is where the clocks are adjusted by an hour twice a year to make better use of the sunlight. 

**U.S. daylight saving**

Forward(+): When we change our clocks in March, time springs forward from 1:59 a.m. to 3:00 a.m.
Backward(-): When we change our clocks in November, time falls back from 1.59 a.m to 1.00 am (thus we experience the hour from 1:00 a.m. to 1:59 a.m. twice)

Children learn this as “Spring forward in the spring, and fall back in the fall.”