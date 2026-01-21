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