# Chapter 10: Streams

# OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER:

## Working with Streams and Lambda expressions

- Use Java object and primitive Streams, including lambda expressions implementing functional interfaces, to create, filter, transform, process, and sort data.
- Perform decomposition, concatenation, and reduction, and grouping and partitioning on sequential and parallel streams.

---

**Creating an Optional**

`Optional` relies on a factory pattern and does not expose any public constructors.

An `Optional<T>` can take a generic type, making it easier to retrieve values from it. 

When creating an `Optional`, it is common to want to use `Optional.empty()` when the value is `null`. Example,

```java
Optional o = (value == null) ? Optional.empty() : Optional.of(value);

// is equivalent to
Optional o = Optional.ofNullable(value);
```

Common `Optional` instance methods,

- `get()`: returns the value if present; throws an exception if empty
- `isPresent()`: returns `true` if a value exists, otherwise `false`
- `ifPresent(Consumer)`: runs the Consumer only when a value is present
- `orElse(T other)`: returns the value if present; otherwise returns `other`
- `orElseGet(Supplier)`: returns the value if present; otherwise calls the Supplier
- `orElseThrow()`: returns the value if present; otherwise throws `NoSuchElementException`
- `orElseThrow(Supplier)`: returns the value if present; otherwise throws the supplied exception


Instead of using an if statement, we can specify a `Consumer` to be run when there is a value inside the `Optional`. Example,

```java
Optional<Double> opt = average(90, 100);
if (opt.isPresent()) {
    System.out.println(opt.get()); // 95.0
}   

// shorter alternative syntax
optionalObject.ifPresent(System.out::println);
```

**Dealing with an Empty Optional**

The following methods allow us to specify what to do if a value isn’t present. The following two allow us to specify a return value either directly or using a `Supplier`. Alternatively, we can have the code throw an exception if the Optional is empty. Or else, we can have the code throw a custom exception if the Optional is empty.

```java
Optional<Double> opt = average();

System.out.println(opt.orElse(Double.NaN));

System.out.println(opt.orElseGet(() -> Math.random()));

System.out.println(opt.orElseThrow());  // java.util.NoSuchElementException

System.out.println(opt.orElseThrow(() -> new IllegalStateException()));

// The opt variable is an Optional<Double>. This means the Supplier must return a Double
System.out.println(opt.orElseGet(() -> new IllegalStateException())); // DOES NOT COMPILE
```

**Is Optional the Same as null?**

An alternative to Optional is to return `null` with few shortcomings.

Returning an `Optional` is a clear statement in the API that there might not be a value. Whereas, there isn’t a clear way to express that `null` might be a special value.

Another advantage of Optional is that we can use a functional programming style with `ifPresent()` and the other methods rather than needing an if statement. And, we can chain Optional calls.

**Using Streams**

Using StreamsA stream in Java is a sequence of data. 

A stream pipeline consists of the operations that run on a stream to produce a result. 

We can think of stream pipeline as an assembly line in a factory. 

With streams, the data isn’t generated up front—it is created when needed. 

This is an example of lazy evaluation, which delays execution until necessary.

Many things can happen in the assembly line stations along the way. In functional programming, these are called stream operations. Just like with the assembly line, operations occur in a pipeline.

Someone has to start and end the work, and there can be any number of stations in between. 

There are three parts to a stream pipeline

1. Source: Where the stream comes from.
2. Intermediate operations: Transforms the stream into another one. There can be as few or as many intermediate operations as you’d like. Since streams use lazy evaluation, the intermediate operations do not run until the terminal operation runs.
3. Terminal operation: Produces a result. Since streams can be used only once, the stream is no longer valid after a terminal operation completes.

When viewing the assembly line (stream pipeline) from the outside, we care only about what comes in and goes out. What happens in between is an implementation detail.

A factory typically has a foreperson who oversees the assembly line work. Java serves as the foreperson when working with stream pipelines.

**Creating Stream Sources**

**Creating Finite Streams**

Java also provides a convenient way of converting a Collection to a stream.

Using parallel streams is like setting up multiple tables of workers who can do the same task. 

```java
Stream<String> empty = Stream.empty(); 
Stream<Integer> singleElement = Stream.of(1);
Stream<Integer> fromArray = Stream.of(1, 2, 3);

var list = List.of("a", "b", "c");
Stream<String> fromList = list.stream();

var list = List.of("a", "b", "c");
Stream<String> fromListParallel = list.parallelStream();
```

**Creating Infinite Streams**

```java
Stream<Double> randoms = Stream.generate(Math::random);
Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);
```

**Reviewing Stream Creation Methods**

Methods for creating a source for streams, given a Collection instance coll.

- `Stream.empty()`: creates a finite stream with zero elements
- `Stream.of(...)`: creates a finite stream from the listed elements
- `coll.stream()`: creates a finite sequential stream from a collection
- `coll.parallelStream()`: creates a finite parallel stream from a collection
- `Stream.generate(Supplier)`: creates an infinite stream by repeatedly calling the Supplier
- `Stream.iterate(seed, UnaryOperator)`: creates an infinite stream starting from a seed and applying the operator
- `Stream.iterate(seed, Predicate, UnaryOperator)`: creates a finite or infinite stream that stops when the predicate becomes false

**Using Common Terminal Operations**

A terminal operation can be performed without any intermediate operations but not the other way around.

Reductions are a special type of terminal operation where all of the contents of the stream are combined into a single primitive or Object (e.g. `int` or `Collection`)

List of terminal stream operations,

- `count()`: returns the number of elements as `long`; does not terminate on infinite streams; is a reduction
- `min()` / `max()`: return the smallest or largest element as `Optional<T>`; do not terminate on infinite streams; are reductions
- `findAny()` / `findFirst()`: return an element as `Optional<T>`; terminate even on infinite streams; are not reductions
- `allMatch()` / `anyMatch()` / `noneMatch()`: return a `boolean`; may terminate early on infinite streams; are not reductions
- `forEach()`: performs an action on each element; does not terminate on infinite streams; is not a reduction
- `reduce()`: combines stream elements into a single result; does not terminate on infinite streams; is a reduction
- `collect()`: accumulates elements into a container or result; does not terminate on infinite streams; is a reduction


A stream can have only one terminal operation. Once a terminal operation has been run, the stream cannot be used again. Attempting to access an already consumed stream will throw the following exception with the message,

```java
java.lang.IllegalStateException: stream has already been operated upon or closed
```

**Counting**

The `count()` method is a reduction because it looks at each element in the stream and returns a single value. The method signature is as follows:

```java
public long count()
```

**Finding the Minimum and Maximum**

Both methods are reductions because they return a single value after looking at the entire stream. The method signatures are as follows:

```java
public Optional<T> min(Comparator<? super T> comparator)
public Optional<T> max(Comparator<? super T> comparator)
```

**Finding a Value**

A terminal operation does not always corresponds to a reduction operation. 

For example, `findAny()`, `findFirst()` methods are terminal operations but not reductions. The reason is that they sometimes return without processing all of the elements. This means that they return a value based on the stream but do not reduce the entire stream into one value.

The method signatures are as follows:

```java
public Optional<T> findAny()
public Optional<T> findFirst()
```

**Matching**

The `allMatch()`, `anyMatch()`, and `noneMatch()` methods search a stream and return information about how the stream pertains to the predicate. This methods are not reductions because they do not necessarily look at all of the elements.

The method signatures are as follows:

```java
public boolean anyMatch(Predicate <? super T> predicate)
public boolean allMatch(Predicate <? super T> predicate)
public boolean noneMatch(Predicate <? super T> predicate)
```

**Iterating**

Calling `forEach()` on an infinite stream does not terminate. Since there is no return value, it is not a reduction.

It is a termminal operation though. This is the only terminal operation with a return type of void. 

The method signature is as follows:

```java
public void forEach(Consumer<? super T> action)
```

`forEach()` can directly be called on a `Collection` or on a `Stream`. 

While `forEach()` sounds like a loop, it is really a terminal operator for streams. Streams cannot be used as the source in a for-each loop because they don’t implement the Iterable interface.

**Reducing**

The `reduce()` methods combine a stream into a single object. They are (obviously) reductions, which means they process all elements. 

The three method signatures are these:

```java
public T reduce(T identity, BinaryOperator<T> accumulator)
public Optional<T> reduce(BinaryOperator<T> accumulator)
public <U> U reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
```

The identity is the initial value of the reduction.

The accumulator combines the current result with the current value in the stream.

If you don’t specify an identity, an `Optional` is returned because there might not be any data.

There are three choices for what is in the `Optional`:

1. If the stream is empty, an empty `Optional` is returned.
2. If the stream has one element, it is returned.
3. If the stream has multiple elements, the accumulator is applied to combine them.

The three-argument `reduce()` operation is useful when working with parallel streams because it allows the stream to be decomposed and reassembled by separate threads.

`reduce()` method examples,

```java
int sum = List.of(1, 2, 3, 4).stream().reduce(0, Integer::sum); // (identity, accumulator)
System.out.println(sum);

Optional<Integer> max = List.of(3, 7, 2, 9)
        .stream()
        .reduce(Integer::max);  // // (accumulator)
max.ifPresent(System.out::println);

List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
int parallelSum = numbers.parallelStream()
                         .reduce(0, Integer::sum, Integer::sum); // (identity, accumulator, combiner), Result: 55
System.out.println(parallelSum);
```

**Collecting**

The `collect()` method is a special type of reduction called a mutable reduction. It is more efficient than a regular reduction because we use the same mutable object while accumulating. 

Common mutable objects include StringBuilder and ArrayList. 

The method signatures are as follows:

```java
public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) 
public <R,A> R collect(Collector<? super T, A, R> collector)
```

The long signature is required when we need to implement our own collector. Java also provides a class with common collectors cleverly named `Collectors`. This approach also makes the code easier to read because it is more expressive. Exampel usage of `Collectors`:

```java
TreeSet<String> set = stream.collect(Collectors.toCollection(TreeSet::new));
Set<String> set = stream.collect(Collectors.toSet());
List<String> list = stream.collect(Collectors.toList());
```

**Using Common Intermediate Operations**

Unlike a terminal operation, an intermediate operation produces a stream as its result. An intermediate operation can also deal with an infinite stream simply by returning another infinite stream. 

**Filtering**

The `filter()` method returns a Stream with elements that match a given expression. Here is the method signature:

```java
public Stream<T> filter(Predicate<? super T> predicate)
```

**Removing Duplicates**

The distinct() method returns a stream with duplicate values removed. The method signature is as follows:

```java
public Stream<T> distinct()
```

**Restricting by Position**

The `limit()` and `skip()` methods can make a Stream smaller. The `limit()` method could also make a finite stream out of an infinite stream. The method signatures are shown here:

```java
public Stream<T> limit(long maxSize)
public Stream<T> skip(long n)
```

**Mapping**

The `map()` method creates a one-to-one mapping from the elements in the stream to the elements of the next step in the stream. The map() method on streams is for transforming data. The method signature is as follows:

```java
public <R> Stream<R> map(Function<? super T, ? extends R> mapper)
```

**Using flatMap**

The `flatMap()` method takes each element in the stream and makes any elements it contains top-level elements in a single stream. This is helpful when you want to remove empty elements from a stream or combine a stream of lists. Method signature,

```java
public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)

// flatMap essentially concats multiple stream like the following
static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b)
```

The Java `Stream.flatMap()` method is used to flatten a stream of collections (or streams) into a single, unified stream. This is particularly useful when each element in the original stream can generate zero or more results, and you want all of those results in a single-level structure (a one-to-many mapping). Example usage,

```java
// A nested list structure
// Original nested list: [[1, 2], [3, 4, 5], [6]]
List<List<Integer>> nestedList = Arrays.asList(
    Arrays.asList(1, 2), 
    Arrays.asList(3, 4, 5), 
    Arrays.asList(6));

// Using flatMap to flatten the nested list into a single list
// Flattened list: [1, 2, 3, 4, 5, 6]
List<Integer> flatList = nestedList.stream()
        .flatMap(Collection::stream) // A function that returns a stream for each element
        .collect(Collectors.toList());
```

**Sorting**

The sorted() method returns a stream with the elements sorted. Just like sorting arrays, Java uses natural ordering unless we specify a comparator. The method signatures are these:

```java
public Stream<T> sorted()
public Stream<T> sorted(Comparator<? super T> comparator)
```

Example usage,

```java
Stream<Integer> numbers = Stream.of(4, 9, 2, 3, 5, 7, 8, 1, 6, 0);

numbers.sorted().forEach(System.out::print);
numbers.sorted(Comparator.naturalOrder()).forEach(System.out::print);
numbers.sorted(Comparator.reverseOrder()).forEach(System.out::print);
```

**Taking a Peek**

The `peek()` method is useful for debugging because it allows us to perform a stream operation without changing the stream. The method signature is as follows:

```java
public Stream<T> peek(Consumer<? super T> action)
```

The intermediate `peek()` operation takes the same argument as the terminal `forEach()` operation.

The most common use for `peek()` is to output the contents of the stream as it goes by. Example,

```java
var stream = Stream.of("black bear", "brown bear", "grizzly bear");
long count = stream.filter(str -> str.startsWith("g")) 
        .peek(System.out::println)  // grizzly bear
        .count();                   // 1
```

**The Danger of Changing State**

In general, it is bad practice to have side effects in a stream pipeline. For example, it is better to use a collector to create a new list than to change the elements in an existing one. 

Similarly, while trying to keep track of something, it is better to have the stream return a count than to increment an instance variable counter. 

Similarly, `peek()` is intended to perform an operation without changing the result. 

Modifying the data structure that is used in the stream is considered a bad practice, which can lead to unexpected results.

**Putting Together the Pipeline**

- Streams let you express what you want to do, not how to do it, using chained operations
- A stream pipeline has three parts: source, intermediate operations, and terminal operation
- Stream code is usually shorter, clearer, and closer to the problem description than loops
- Intermediate operations (filter, sorted, limit) are lazy and don’t run until a terminal operation is reached
- Data flows through the pipeline element by element, but some operations (like `sorted`) must see all elements first
- `filter()` removes elements that don’t match the condition
- `sorted()` may need to buffer all elements before passing anything downstream
- `limit()` can short-circuit the pipeline once enough elements are seen
- The order of operations matters, especially with infinite streams
- Using `sorted()` before `limit()` on an infinite stream causes the program to hang or run out of memory (`java.lang.OutOfMemoryError`)
- Placing `limit()` before `sorted()` can make an infinite stream terminate correctly
- If `filter` blocks all elements, `limit` can never be satisfied, causing an infinite wait
- A terminal operation starts execution and ends the pipeline
- After a terminal operation, a stream cannot be reused (one time use)
- You can chain pipelines by ending one with a terminal operation and starting a new stream from its result

**Working with Primitive Streams**

Converting `Stream<Integer>` to an `IntStream` with `mapToInt(ToIntFunction)` function. Example,

```java
Stream<Integer> stream = Stream.of(1, 2, 3);
int sum = stream.mapToInt(x -> x).sum();        // 6
```

**Creating Primitive Streams**

Here are the three types of primitive streams:

- **IntStream:** Used for the primitive types `int`, `short`, `byte`, and `char`
- **LongStream:** Used for the primitive type `long`
- **DoubleStream:** Used for the primitive types `double` and `float`

*Why doesn’t each primitive type have its own primitive stream?*

These three are the most common, so the API designers went with them.

**Common primitive stream methods**

- `OptionalDouble average()`
  Returns the arithmetic mean of elements as an `OptionalDouble`. Available on `IntStream`, `LongStream`, and `DoubleStream`. Example,

  ```java
  OptionalDouble avg = IntStream.of(10, 20, 30).average();
  int result = avg.getAsDouble();       // 20.0
  avg.ifPresent(System.out::println);   // 20.0
  ```

- `Stream<T> boxed()`
  Converts a primitive stream into a regular `Stream` of wrapper objects (`Integer`, `Long`, `Double`). Available on `IntStream`, `LongStream`, and `DoubleStream`. Example,
  ```java
  Stream<Integer> boxed = IntStream.of(1, 2, 3).boxed();
  boxed.forEach(System.out::println);   // 1 2 3
  ```

- `OptionalInt max()` / `OptionalLong max()` / `OptionalDouble max()`
  Returns the maximum element wrapped in the corresponding Optional type. Available on `IntStream`, `LongStream`, and `DoubleStream`.

  ```java
  OptionalInt max = IntStream.of(4, 9, 2).max();
  int result = max.getAsInt();          // 9
  max.ifPresent(System.out::println);   // 9
  ```

- `OptionalInt min()` / `OptionalLong min()` / `OptionalDouble min()`
  Returns the minimum element wrapped in the corresponding Optional type. Available on `IntStream`, `LongStream`, and `DoubleStream`. Example,

  ```java
  OptionalInt min = IntStream.of(4, 9, 2).min();
  int result = min.getAsInt();          // 2
  min.ifPresent(System.out::println);   // 2
  ```

- `IntStream range(int a, int b)` / `LongStream range(long a, long b)`
  Creates a primitive stream from `a` (inclusive) to `b` (exclusive). Available on `IntStream` and `LongStream`. Example,

  ```java
  IntStream.range(1, 5).forEach(System.out::print); // 1234
  ```

- `IntStream rangeClosed(int a, int b)` / `LongStream rangeClosed(long a, long b)`
  Creates a primitive stream from `a` (inclusive) to `b` (inclusive). Available on `IntStream` and `LongStream`. Example,

  ```java
  IntStream.range(1, 5).forEach(System.out::print); // 12345
  ```

- `int sum()` / `long sum()` / `double sum()`
  Returns the sum of all elements as a primitive value. Available on `IntStream`, `LongStream`, and `DoubleStream`. Example,

  ```java
  int sum = IntStream.of(1, 2, 3).sum();        // 6
  ```

- `IntSummaryStatistics` / `LongSummaryStatistics` / `DoubleSummaryStatistics summaryStatistics()`
  Returns an object containing count, min, max, sum, and average. Available on `IntStream`, `LongStream`, and `DoubleStream`. Example,

  ```java
  IntSummaryStatistics stats = IntStream.of(1, 2, 3, 4).summaryStatistics();
  System.out.println(stats.getCount());         // 4
  System.out.println(stats.getMin());           // 1
  System.out.println(stats.getMax());           // 4
  System.out.println(stats.getSum());           // 10
  System.out.println(stats.getAverage());       // 2.5
  ```

We can create an empty stream with this:

```java
DoubleStream empty = DoubleStream.empty();

DoubleStream oneValue = DoubleStream.of(3.14);
oneValue.forEach(System.out::println); 

DoubleStream varargs = DoubleStream.of(1.0, 1.1, 1.2);
varargs.forEach(System.out::println);
```

We can also use the two methods for creating infinite streams, just like we did with Stream.

```java
var random = DoubleStream.generate(Math::random);
var fractions = DoubleStream.iterate(.5, d -> d / 2);

random.limit(3).forEach(System.out::println);
fractions.limit(3).forEach(System.out::println);
```

`ints()` method from `java.util.Random` generates an infinite `IntStream` of primitives. Method signatures,

```java
ints()
ints(long streamSize)
ints(int origin, int bound) // origin is inclusive, bound is exclusive
ints(long streamSize, int origin, int bound)
```

Same pattern exists for `longs()` and `doubles()`. Example usage,

```java
Random r = new Random();
r.ints().limit(5).forEach(System.out::println);
r.ints(1, 11).limit(5).forEach(System.out::println);
r.ints(5, 11, 21).forEach(System.out::println);
```

**Mapping Streams** 

Mapping lets you convert one stream type into another by providing a mapping function. The function must return the target type.

- Source `Stream<T>` to create `Stream<R>`
  Needs to pass `Function<T, R>`. Example,

  ```java
  Stream<Integer> s = Stream.of("java", "api").map(String::length);
  ```

- Source `Stream<T>` to create `IntStream`
  Needs to pass `ToIntFunction<T>`. Example,

  ```java
  IntStream s = Stream.of("penguin", "fish").mapToInt(String::length);
  ```

- Source `Stream<T>` to create `LongStream`
  Needs to pass `ToLongFunction<T>`. Example,

  ```java
  LongStream s = Stream.of(1, 2, 3).mapToLong(Integer::longValue);
  ```

- Source `Stream<T>` to create `DoubleStream`
  Needs to pass `ToDoubleFunction<T>`. Example,

  ```java
  DoubleStream s = Stream.of("10.5", "20.0").mapToDouble(Double::parseDouble);
  ```

  ---

- Source `IntStream` to create `Stream<R>`
  Needs to pass `IntFunction<R>`. Example,

  ```java
  Stream<String> s = IntStream.of(1, 2).mapToObj(n -> "Item" + n);
  ```

- Source `IntStream` to create `IntStream`
  Needs to pass `IntUnaryOperator`. Example,

  ```java
  IntStream s = IntStream.of(2, 3).map(n -> n * n);
  ```

- Source `IntStream` to create `DoubleStream`
  Needs to pass `IntToDoubleFunction`. Example,

  ```java
  DoubleStream s = IntStream.of(3, 5).mapToDouble(n -> n / 2.0);
  ```

- Source `IntStream` to create `LongStream`
  Needs to pass `IntToLongFunction`. Example,

  ```java
  LongStream s = IntStream.of(1, 2).mapToLong(n -> n * 100L);
  ```

---

- Source `LongStream` to create `Stream<R>`
  Needs to pass `LongFunction<R>`. Example,

  ```java
  Stream<String> s = LongStream.of(10L, 20L).mapToObj(n -> "L" + n);
  ```

- Source `LongStream` to create `LongStream`
  Needs to pass `LongUnaryOperator`. Example,

  ```java
  LongStream s = LongStream.of(5L, 6L).map(n -> n * 2);
  ```

- Source `LongStream` to create `DoubleStream`
  Needs to pass `LongToDoubleFunction`. Example,

  ```java
  DoubleStream s = LongStream.of(3L, 4L).mapToDouble(n -> n + 0.5);
  ```

- Source `LongStream` to create `IntStream`
  Needs to pass `LongToIntFunction`. Example,

  ```java
  IntStream s = LongStream.of(7L, 8L).mapToInt(n -> (int) n);
  ```

---

- Source `DoubleStream` to create `Stream<R>`
  Needs to pass `DoubleFunction<R>`. Example,

  ```java
  Stream<String> s = DoubleStream.of(1.5, 2.5).mapToObj(d -> "V" + d);
  ```

- Source `DoubleStream` to create `DoubleStream`
  Needs to pass `DoubleUnaryOperator`. Example,

  ```java
  DoubleStream s = DoubleStream.of(1.0, 2.0).map(d -> d * 2);
  ```

- Source `DoubleStream` to create `IntStream`
  Needs to pass `DoubleToIntFunction`. Example,

  ```java
  IntStream s = DoubleStream.of(1.9, 3.2).mapToInt(d -> (int) d);
  ```

- Source `DoubleStream` to create `LongStream`
  Needs to pass `DoubleToLongFunction`. Example,

  ```java
  LongStream s = DoubleStream.of(2.3, 4.7).mapToLong(Math::round);
  ```

**Patterns to remember**

- Mapping to the same type you started with is just called `map()`. 
- When returning an object stream, the method is `mapToObj()`. 
- Beyond that, it’s the name of the primitive type in the map method name.

**Key Notes**

- Mapping always requires a function
- The function returns the target stream’s element type
- `map()` keeps you in `Stream<T>`
- `mapToInt`, `mapToLong`, `mapToDouble` move you to primitive streams
- `mapToObj()` moves you back to object streams
- Primitive streams avoid boxing and are more efficient

**Using flatMap()**

- `map()` transforms one element into one element
- `flatMap()` transforms one element into a stream of elements, then flattens them into a single stream

We can think of flatMap as “map + unwrap”.

**Why primitive flatMapToX() exists?**

Primitive streams (`IntStream`, `DoubleStream`, `LongStream`) cannot contain other streams.
So Java gives special methods:

- `flatMapToInt()`: flattens into a single `IntStream`
- `flatMapToDouble()`: flattens into a single `DoubleStream`
- `flatMapToLong()`: flattens into a single `LongStream`

These avoid boxing and unboxing. Example,

```java
var integerList = new ArrayList<Integer>();

IntStream ints = integerList.stream().flatMapToInt(x -> IntStream.of(x));
IntStream ints = integerList.stream().mapToInt(Integer::intValue);
```

Exmaple of `flatMap()` method where one element produces multiple primitive values,

```java
List<int[]> data = List.of(
    new int[]{1, 2},
    new int[]{3, 4, 5}
);

// IntStream(1,2) + IntStream(3,4,5), then IntStream(1,2,3,4,5)
IntStream stream = data.stream().flatMapToInt(IntStream::of);
```

**Using Optional with Primitive Streams**

- Each primitive stream has its own optional type
  `IntStream` has `OptionalInt`, `LongStream` has `OptionalLong`, `DoubleStream` has `OptionalDouble`

- To extract the value, use the matching method
  `getAsInt()`, `getAsLong()`, `getAsDouble()`

- `orElseGet()` uses a primitive supplier
  `IntSupplier`, `LongSupplier`, `DoubleSupplier`

- `min()` and `max()` return the matching optional type
  `OptionalInt`, `OptionalLong`, `OptionalDouble`

- `sum()` never returns an Optional
  Returns `int`, `long`, or `double`. Empty stream result is `0`

- `average()` always returns `OptionalDouble`
  Even for `IntStream` and `LongStream`. Reason: averages can be fractional


For the following example, we use `OptionalDouble` instead of `Optional<Double>`. The difference is that `OptionalDouble` is for a primitive and `Optional<Double>` is for the `Double` wrapper class. Example,

```java
var stream = IntStream.rangeClosed(1,10);
OptionalDouble optional = stream.average();

optional.ifPresent(System.out::println);                  // 5.5
System.out.println(optional.getAsDouble());               // 5.5
System.out.println(optional.orElseGet(() -> Double.NaN)); // 5.5
```

**Linking Streams to the Underlying Data**