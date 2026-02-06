# Chapter 9: Collections and Generics

## OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER

### Working with Arrays and Collections

- Create arrays, List, Set, Map and Deque collections, and add, remove, update, retrieve and sort their elements.

---

**Using Common Collection APIs**

A collection is a group of objects contained in a single object.

The Java Collections Framework is a set of classes in java.util for storing collections.

There are four main interfaces in the Java Collections Framework.

1. List - A list is an ordered collection of elements that allows duplicate entries. Elements in a list can be accessed by an int index.
2. Set - A set is a collection that does not allow duplicate entries.
3. Queue - A queue is a collection that orders its elements in a specific order for processing. A Deque is a subinterface of Queue that allows access at both ends.
4. Map - A map is a collection that maps keys to values, with no duplicate keys allowed. The elements in a map are key/value pairs.

**Understanding Generic Types**

In Java, generics is just a way of saying parameterized type. For example, a `List<Integer>` is a list of numbers, while `Set<String>` is a set of strings.

**Shortening Generics Code**

```java
Map<Long,List<Integer>> mapOfLists = new HashMap<Long,List<Integer>>();

Map<Long,List<Integer>> mapOfLists = new HashMap<>();

var mapOfLists = new HashMap<Long,List<Integer>>(); 
```

The diamond operator (`<>`) infers the type from the left side of the declaration.

`var` infers the type from the right side of the declaration

If there isn't enough information to infer, we get `Object` as generic type. Example,

```java
var map = new HashMap<>();

HashMap<Object, Object> map = new HashMap<Object, Object>();
```

**Removing with Conditions**

The `removeIf()` method removes all elements that match a condition. We can specify what should be deleted using a block of code or even a method reference.

The method signature looks like the following.

```java
public boolean removeIf(Predicate<? super E> filter)
```

It uses a Predicate, which takes one parameter and returns a boolean. Example,

```java
var list = new ArrayList<String>();
list.add("Magician");
list.add("Assistant");
System.out.println(list);     // [Magician, Assistant]

list.removeIf(s -> s.startsWith("A"));
System.out.println(list);     // [Magician]
```

**Iterating on a Collection**

There’s a forEach() method that you can call on a Collection instead of writing a loop. The method signature is as follows:

```java
public void forEach(Consumer<? super T> action)
```

It uses a Consumer that takes a single parameter and doesn’t return anything. Example,

```java
Collection<String> cats = List.of("Annie", "Ripley");
cats.forEach(System.out::println);
cats.forEach(c -> System.out.println(c));   // with lambda expression
cats.forEach(System.out::println);          // with method reference
```

**Unboxing nulls**

Try to unbox that `null` to an any primitive type is not possible, can be unboxed to reference type though. When java tries to get the primitive value of null, it throws `NullPointerException`.

```java
var heights = new ArrayList<Integer>();
heights.add(null);
int h = heights.get(0);     // NullPointerException
var x = heights.get(0);     // Compiles fine
Integer x = heights.get(0); // Compiles fine
```

**Using the List Interface**

The main thing all `List` implementations (`ArrayList`, `LinkedList`) have in common is that they are ordered and allow duplicates.

`ArrayList` and `LinkedList` both implement `List`. `ArrayList` is a resizable array, and default choice when unsure. `LinkedList` implements both `List` and `Deque`.

`ArrayList` suitable for:

- Constant-time random access (`get`)
- Slower add/remove operations
- Best when reads ≥ writes

`LinkedList` suitable for:

- Constant-time add/remove at head and tail
- Linear-time access by index
- Best when used as a `Deque`

**Creating a List with a Factory**

`Arrays.asList(varargs)`

- Returns a fixed-size list backed by an array (passed through constructor)
- Cannot add or delete elements
- Can replace (modify) elements

`List.of(varargs)`

- Returns an immutable list
- Cannot add, delete or repalce (modify) elements
- Throws `UnsupportedOperationException` when attempted to modify

`List.copyOf(collection)`

- Returns an immutable list copying the original collection’s values
- Cannot add, delete or replace (modify) elements
- Throws `UnsupportedOperationException` when attempted to modify

Example,

```java
String[] array = new String[] {"a", "b", "c"};

List<String> asList = Arrays.asList(array); // [a, b, c]
List<String> of = List.of(array);           // [a, b, c]
List<String> copy = List.copyOf(asList);    // [a, b, c]
```

**Creating a List with a Constructor**

Most Collections have two constructors that you need to know for the exam. Example,

```java
var linked1 = new LinkedList<String>();
var linked2 = new LinkedList<String>(linked1);

var list1 = new ArrayList<String>();
var list2 = new ArrayList<String>(list1);
var list3 = new ArrayList<String>(10);  // initial capacity
```

**Working with List Methods**

The `replaceAll()` method replaces each element in list with the result of operator. Method signature,

```java
default void replaceAll(UnaryOperator<E> op)
```

Example,

```java
var numbers = List.of(1, 2, 3);         // Immutable, throws java.lang.UnsupportedOperationException in replaceAll method call
var numbers = Arrays.asList(1, 2, 3);   // Elements can be replaced, mutable
numbers.replaceAll(x -> x * x);
System.out.println(numbers);            // [1, 4, 9]
```

**Overloaded `remove()` Methods**

There are two overloaded `remove()` method for list.

The one from `Collection` removes an object that matches the parameter.

The other one from `List` removes an element at a specified index.

If the passed value is a primitive, the `boolean remove(int index)` method is called.

If the passed value is a wrapper i.e. Object type, the `boolean remove(Object obj)` method is called.

Example,

```java
var list = new LinkedList<Integer>();
list.add(3);
list.add(2);
list.add(1);
list.remove(2);                   // removes value at index 2
list.remove(Integer.valueOf(2));  // removes Integer object 2
System.out.println(list);         // [3]
```

The `remove()` method that takes an element i.e. object will return false if the element is not found.

Contrast this with the `remove()` method that takes an int, which throws an exception if the element is not found, or the element that is being removed if found.

**Converting from `List` to an `Array`**

```java
List<String> birdList = new ArrayList<>();
birdList.add("hawk");
birdList.add("robin");

Object[] objectArray = birdList.toArray();              // if nothing paseed, then unpacked to Object array
String[] stringArray = (String[])birdList.toArray();    // throws java.lang.IndexOutOfBoundsException
String[] stringArray = birdList.toArray(new String[0]); // Compiles fine
```

**Comparing Set Implementations**

**`HashSet`**

- Stores elements in a hash table
- Uses `hashCode()` for efficient lookup
- No guaranteed iteration order
- Fast add, remove, and lookup on average

**`LinkedHashSet`**

- HashSet with linked list maintaining encounter order (preserves insertion order)
- Iteration order is predictable, usually insertion order
- Slightly slower than HashSet due to ordering overhead
- Allows reordering by adding/removing at front or back

**`TreeSet`**

- Stores elements in a sorted tree structure
- Always maintains sorted order (sorts elements)
- Slower add and remove compared to HashSet
- Useful when sorted traversal is required
- Rquires elements to be comparable (element that implements Comparable interface)

**Working with Set Methods**

Create immutable `Set` in one line or making a copy of an existing one,

```java
Set<Character> letters = Set.of('c', 'a', 't');
Set<Character> copy = Set.copyOf(letters);
```

**How HashSet Stores and Retrieves Elements (Hashing and Collision Resolution)**

In a `HashSet`, when a value is inserted, Java first calls `hashCode()` to decide which bucket the element should go into. If the bucket is empty, the value is stored directly; if there’s already one or more elements in that bucket (a hash collision), Java uses `equals()` to check whether the new element is equal to an existing one. If it is, the value is not added, and if not, it’s stored alongside the others.

When fetching or checking for a value, the same process happens: `hashCode()` narrows the search to a specific bucket, and `equals()` is used to find the exact match within that bucket. Ideally, unique hash codes mean very few `equals()` calls, while poor hash code implementations force Java to compare against many elements, reducing performance.

**How `TreeSet` is able to sort the elements automatically?**

TreeSet relies on comparison, not hashing, to organize elements. Since Java’s `Number` wrapper classes like Integer implement the Comparable interface, TreeSet can compare elements and keep them in their natural order (for Integer, ascending numeric order).

**Comparing Deque Implementations**

**LinkedList**

- Implements both `List` and `Deque`
- Supports index-based access and deque operations
- Higher memory and performance overhead due to linked structure
- Useful when `List` and `Deque` features are both required

**ArrayDeque**

- Implements `Deque` only
- Array-based and more memory-efficient
- Faster add and remove operations at both ends
- Preferred when only deque behavior is needed

**Working with `Queue` and `Deque` Methods**

**`Queue` Methods**

1. Add element to the back of the queue. Method signatures,

    ```java
    public boolean add(E);
    public boolean offer(E);
    ```

2. Read element from the front of the queue. Method signatures,

    ```java
    public E element(); // throws NoSuchElementException
    public E peek();
    ```

3. Get and remove element from the front. Method signatures,

    ```java
    public E remove();  // throws NoSuchElementException
    public E poll();
    ```

**`Dequeue` Methods**

1. Add element to the front of the queue. Method signatures,

    ```java
    public boolean addFirst(E);
    public boolean offerFirst(E);
    ```

2. Add element to the back of the queue. Method signatures,

    ```java
    public boolean addLast(E);
    public boolean offerLast(E);
    ```

3. Read element from the front of the queue. Method signatures,

    ```java
    public E getFirst();
    public E peekFirst();
    ```

4. Read element from the back of the queue. Method signatures,

    ```java
    public E getLast();
    public E peekLast();
    ```

5. Get and remove element from the front of the queue. Method signatures,

    ```java
    public E removeFirst();
    public E pollFirst();
    ```

6. Get and remove element from the back of the queue. Method signatures,

    ```java
    public E removeLast();
    public E pollLast();
    ```

**`Deque` can be used as Stack**

1. Add to the top of the stack

    ```java
    public void push(E);
    ```

2. Remove from the top of the stack

    ```java
    public E pop();
    ```

3. Get first element of the stack

    ```java
    public E peek();
    ```

**Using the `Map` Interface**

`LinkedHashMap` is ordered (insertion order maintained) and `TreeMap` is sorted (natural order).

Factory Methods:

1. `Map.of()`
2. `Map.copyOf()`
3. `Map.ofEntries()`

**Comparing Map Implementations**

**`HashMap`**

- Stores keys in a hash table
- Uses `hashCode()` for fast key lookup
- No guaranteed iteration order
- Fast put, get, and remove on average

**`LinkedHashMap`**

- HashMap with a linked list to maintain order
- Iteration order is predictable, usually insertion order
- Slightly slower than HashMap due to ordering
- Supports adding/removing entries at front or back

**`TreeMap`**

- Stores keys in a sorted tree structure
- Always keeps keys in sorted order
- Slower put and lookup compared to HashMap as size grows
- Useful when sorted keys are required

**Working with Map Methods**

The `get()` method returns `null` if the requested key is not in the map.

The `getOrDefault()` method returns default (user provided) value if the requested key is not in the map.

The `putIfAbsent()` method sets a value in the map but skips it if the value is already set to a non-null value.

**Merging Data**

The `merge()` method combines an existing value and a new value using a BiFunction, mapping function decides which value to keep when both values exist, and returns the final value associated with the key. The mapping `BiFunction` is caalled only if the key exists and its current value is non-null.

The merge() method also has logic for what happens if `null` values or missing keys are involved. In this case, it doesn’t call the `BiFunction` at all, and it simply uses the new value.

If the `BiFunction` were called, then we’d have a `NullPointerException`. The mapping function is used only when there are two actual values to decide between.

The final thing to know about `merge()` is what happens when the mapping `BiFunction` is called and returns `null`. The key is removed from the map when this happens.

*If `merge()` calls the mapping function and it returns null, the key is deleted from the map.*

Example,

```java
// BiFunction that decides to remove the key
BiFunction<Integer, Integer, Integer> mapper = (oldVal, newVal) -> null;

Map<String, Integer> scores = new HashMap<>();
scores.put("Alice", 80);            // {Alice=80}
scores.merge("Alice", 90, mapper);  // {}
```

**Why Java does this**

It gives `merge()` a powerful meaning:

- Mapping function returns a value, `merge()` keep/update the entry
- Mapping function returns `null`, explicitly say “this key should no longer exist”

**Summary**

- If key exists with a `null` value, the mapping function is not called, and the value is replaced with the new value
- If key exists with a non-null value and the mapping function returns `null`, the key is removed from the map
- If key exists with a non-null value and the mapping function returns a non-null value, the value is updated to the function result
- If key does not exist, the mapping function is not called, and the key is added with the new value

So, the mapping `BiFunction` is called only if the key exists with a non-null value.

**Sorting Data**

We use `Collections.sort()` in many of these examples. It returns void because the method parameter is what gets sorted.

Java provides an interface called `java.lang.Comparable`. If a class implements Comparable, it can be used in data structures that require comparison.
`String`, `StringBuilder`, `BigDecimal`, `BigInteger` and the primitive wrapper classes include `Comparable` interface implementation.

There is also another interface called `java.util.Comparator`, which is used to specify that we want to use a different order than the object itself provides.

**Designing a `compareTo()` method for `Comparable` class**

When writing a `compareTo()` method, the most important part is the return value. The following rules should apply to the return type of the `compareTo()` method:

- The number 0 is returned when the current object is equivalent to the argument to compareTo().
- A negative number (less than 0) is returned when the current object is smaller than the argument to compareTo().
- A positive number (greater than 0) is returned when the current object is larger than the argument to compareTo().

**`compareTo()` Summary**

- `compareTo()` does not move or swap elements; it only provides an ordering signal
- If the return value is **less than 0**, it means the current object should come before the other object
- If the return value is **equal to 0**, it means both objects are considered equal in ordering
- If the return value is **greater than 0**, it means the current object should come after the other object
- `Collections.sort()` (or any sorting algorithm) **reorders or swaps elements when it receives a positive value**
- The actual number returned does not matter (`1`, `2`, `100` behave the same); only whether it is negative, zero, or positive matters

*If `compareTo()` returns a positive value, the sorting algorithm treats the order as incorrect and fixes it*

**why and how `compareTo()` and `equals()` must be consistent**:

- `compareTo()` and `equals()` both define *equality*, but in different ways
- Objects are **consistent** if `compareTo()` returns `0` **whenever** `equals()` returns `true`
- If `compareTo()` returns `0` but `equals()` returns `false`, collections may behave unpredictably
- Sorted collections rely on `compareTo()` to detect duplicates and ordering
- Inconsistency can cause lost elements, duplicates, or incorrect lookups
- The safest rule is to base **both methods on the same fields**
- If you need multiple sorting strategies, keep `compareTo()` consistent with `equals()` and use a separate `Comparator` for alternate orderings

Example of `compareTo()` override that is consistent with `equals()`

```java
public record Product(int id, String name) implements Comparable<Product> {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product other) {
            // Equality based only on id
            return this.id == other.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // Hash code based only on id
        return Integer.hashCode(id);
    }

    @Override
    public int compareTo(Product other) {
        // Natural ordering based on id, consistent with equals
        return Integer.compare(this.id, other.id);
    }
}
```

**Mental model:**

> `equals()` answers “are these objects the same?”
> `compareTo()` answers “should these objects be treated as the same position in a sorted structure?”

**Comparing Data with a Comparator**

A class can define only one natural ordering using `compareTo()`, but sometimes we might need multiple sort orders. Here comes the usage of `Comparator`
  
- `Comparator` allows sorting by different criteria without modifying the class itself
- A `Comparator` can be defined using an anonymous class, a lambda expression, or a method reference
- Sorting with `Collections.sort(list)` uses the class’s `compareTo()` natural order
- Sorting with `Collections.sort(list, comparator)` uses the external `Comparator` to determine order
- `Comparator` is a functional interface with a single abstract `compare()` method
- `Comparator.comparing()` is a convenient static method to create a `Comparator` from a lambda or method reference

Examples of using `Comparator`,

```java
record Duck(int id, String name, int weight) implements Comparable<Duck> {
    @Override
    public int compareTo(Duck other) {
        return Integer.compare(this.id, other.id);
    }
}

// anonymous inner class comparator
Comparator<Duck> compareByName = new Comparator<Duck>() {
    @Override
    public int compare(Duck d1, Duck d2) {
        return d1.name.compareTo(d2.name);
    }
};

// lambda comparator
Comparator<Duck> compareByName = (d1, d2) -> d1.name.compareTo(d2.name);

// method reference comparator
var compareByName = Comparator.comparing(Duck::name);

Collections.sort(ducks);                // sort by id, relies on compareTo
Collections.sort(ducks, compareByName); // sort by name, relies on Comparator
```

**Is Comparable a Functional Interface?**

`Comparator` is a functional interface because it has a single abstract method. `Comparable` is also a functional interface since it also has a single abstract method.

However, using a lambda for `Comparable` would be silly. The point of `Comparable` is to implement it inside the object being compared.

The `Comaparble` make a particular class, record comparable so that the instances can be compared to each other.

**Comparing `Comparable` and `Comparator`**

- **Comparable**: implemented by the class itself; defines the object’s *natural/default ordering*
- **Comparator**: separate class, lambda, or method reference; defines a *custom or alternate ordering*
- **Package**: Comparable is in `java.lang`; Comparator is in `java.util`
- **Interface requirement**: Comparable must be implemented by the class being compared; Comparator does not need to be implemented by the class
- **Method name**: Comparable uses `compareTo()`; Comparator uses `compare()`
- **Number of parameters**: Comparable takes 1 parameter (the object to compare to); Comparator takes 2 parameters (the two objects to compare)
- **Lambda usage**: Comparable usually cannot be declared with a lambda; Comparator can easily be declared with a lambda or method reference
- **Key idea**: Comparable defines how the object orders itself; Comparator defines how you want to order objects externally

Complex comparators can be created using method reference and chaining multiple simple comparator. Example,

```java
public record Squirrel(int weight, String species) {}

var c = Comparator.comparing(Squirrel::species)
                  .thenComparingInt(Squirrel::weight);

var c = Comparator.comparing(Squirrel::species).reversed();
```

**Sorting and Searching**

`java.util.Collections` has the following methods for sorting and searching,

```java
// T should implement Comparable
public static <T> void sort(List<T>);     

// T not required to implement Comparable
public static <T> void sort(List<T>, Comparator<? super T>);    

// T should implement Comparable, list should be sorted
public static <T> int binarySearch(List<T>, T);                 

// T not required to implement Comparable, list should be sorted
public static <T> int binarySearch(List<T>, T, Comparator<T>);  
```

The result of calling `binarySearch()` on an improperly sorted list is undefined.

`java.util.TreeSet` needs the type of the object to be `Comparable` or the constructor needs a `Comparator`. Otherwise `java.lang.ClassCastException` is thrown. Example,

```java
record Rabbit(int id) {}

Set<Rabbit> rabbits = new TreeSet<>();
rabbits.add(new Rabbit(1));  // ClassCastException, as Rabbit is not `Comparable`

Set<Rabbit> rabbits = new TreeSet<>((r1, r2) -> r1.id - r2.id);
rabbits.add(new Rabbit(1));  // Compiles fine, as Comparator is passed to the constractor
```

**Introducing Sequenced Collections**

New to Java 21 are sequenced collections, which includes the three interfaces,

1. SequencedCollection
2. SequencedSet
3. SequencedMap

A sequenced collection is a collection in which the encounter order is well-defined. By encounter order, it means all of the elements can be read in a repeatable way. While the elements of the collection may be sorted, it is not required.

**What “sequenced collection” actually means**

A sequenced collection is simply a collection where the order of elements is predictable and repeatable.

If you iterate over it today, tomorrow, or five minutes later, you get elements in the same order, as long as the collection hasn’t changed.

That predictable order is called the encounter order.

Sequenced does not mean sorted.

*A sequenced collection is one where iteration order is guaranteed and repeatable, whether that order is insertion-based or sorted.*

`java.util.SequencedCollection` methods,

1. `addFirst(E e)` - Adds element as the first element in the collection
2. `addLast(E e)` - Adds element as the last element in the collection
3. `getFirst()` - Retrieves the first element in the collection
4. `getLast()` - Retrieves the last element in the collection
5. `removeFirst()` - Removes the first element in the collection
6. `removeLast()` - Removes the last element in the collection
7. `reversed()` - Returns a reverse-ordered view of the collection

`addLast()` call fails at runtime for `TreeSet<T>` because we cannot insert an item at the end of a sorted structure. Doing so could violate the comparator within the `TreeSet`. Method signature,

```java
public void addFirst(E e) {
    throw new UnsupportedOperationException();
}

public void addLast(E e) {
    throw new UnsupportedOperationException();
}
```

A `SequencedSet` is a subtype of `SequencedCollection`; therefore, it inherits all its methods. It only applies to `SequencedCollection` classes that also implement `Set`, such as `LinkedHashSet` and `TreeSet`.

**`SequencedCollection` vs. `SortedCollection`**

- `SequencedCollection` defines a collection with a well-defined and repeatable encounter order
- `SequencedCollection` does not require elements to be sorted
- `SequencedCollection` order can be insertion order or any other fixed order
- `SortedCollection` defines a collection whose elements are always maintained in sorted order
- `SortedCollection` requires elements to be sorted using natural ordering or a `Comparator`
- `SequencedCollection` focuses on *order predictability*, not ordering rules
- `SortedCollection` focuses on *ordering rules*, not just predictability
- All sorted collections are sequenced, but not all sequenced collections are sorted

**Working with SequencedMap**

Common SequencedMap Methods,

1. `firstEntry()` - Retrieves the first key/value pair in the map
2. `lastEntry()` - Retrieves the last key/value pair in the map
3. `pollFirstEntry()` - Removes and retrieves the first key/value pair in the map
4. `pollLastEntry()` - Removes and retrieves the last key/value pair in the map
5. `putFirst(K k, V v)` - Adds the key/value pair as the first element in the map
6. `putLast(K k, V v)` - Adds the key/value pair as the last element in the map
7. `reversed()` - Returns a reverse-ordered view of the map

Like `HashSet` earlier, a `HashMap` does not have an ordering, so it cannot be used as a SequencedMap. 

**Using Unmodifiable Wrapper Views**

An unmodifiable view is a wrapper object around a collection that cannot be modified through the view itself. Any attempt to modify the view (compiles!) will throw an `UnsupportedOperationException` at runtime.

While the view object cannot be modified, the underlying data can still be modified. 

However, since it is a view, nothing prevents us from changing the original values.

Example,

```java
Collection<String> coll = Collections.unmodifiableCollection(List.of("brown"));
List<String> list       = Collections.unmodifiableList(List.of("orange"));
Set<String> set         = Collections.unmodifiableSet(Set.of("green"));
Map<String,Integer> map = Collections.unmodifiableMap(Map.of("red", 1));
```

**Summary**

- `List` allows duplicates and maintains order by index
- `Queue` allows duplicates and enforces a specific add/remove order
- `Set` does not allow duplicates and does not guarantee order
- `Map` stores key–value pairs, keys are unique, values may duplicate
- `ArrayList`, `LinkedList`, `ArrayDeque are` ordered but not sorted and do not use hashing or comparison
- `HashSet, HashMap` are unordered and rely on `hashCode()`
- `LinkedHashSet`, `LinkedHashMap` preserve insertion order but are not sorted
- `TreeSet`, `TreeMap` are ordered and sorted and rely on `compareTo()` or `Comparator`
- Sorted collections do not allow `null` values (`TreeSet`) or `null` keys (`TreeMap`)
- On the exam, first identify whether you need a `List`, `Set`, `Queue`, or `Map`, then choose based on ordering, sorting, and uniqueness requirements

**Older Collections**

There are a few collections that are no longer on the exam but that you might come across in older code. All three were early Java data structures you could use with threads.

1. `Vector`: Implements List
2. `Hashtable`: Implements Map
3. `Stack`: Implements List

**Working with Generics**

The syntax for introducing a generic is to declare a formal type parameter in angle brackets. For example, the following class named Crate has a generic type variable declared after the name of the class,

```java
public class Crate<T> {
    private T contents;  

    public T lookInCrate() {      
        return contents;   
    }   

    public void packCrate(T contents) {      
        this.contents = contents;   
    }
}
```

**Naming Conventions for Generics**

The convention is to use single uppercase letters to make it obvious that they aren’t real class names. The following are common letters to use,

- E for an element
- K for a map key
- V for a map value
- N for a number
- T for a generic data type
- S, U, V, and so forth for multiple generic types

Example usage of generic,

```java
Elephant elephant = new Elephant();
Crate<Elephant> crateForElephant = new Crate<>();
rateForElephant.packCrate(elephant);
Elephant inNewHome = crateForElephant.lookInCrate();

Zebra zebra = new Zebra();
Crate<Zebra> crateForZebra = new Crate<>();

Robot joeBot = new Robot();
Crate<Robot> robotCrate = new Crate<>();
robotCrate.packCrate(joeBot);
// ship to Houston
Robot atDestination = robotCrate.lookInCrate();
```

Before generics, we would have needed to use Object class for passing around instance variable to behave in generic way, which would have put the burden on the caller to cast the object it receives to retrieve the actual instance.

**Understanding Type Erasure**

- Type erasure is the process where generic type information is removed after compilation
- Generics exist only at compile time to provide type safety
- At runtime, generic type parameters (like `T`) are replaced with `Object`
- Only one class file exists, not separate versions for each generic type
- The compiler automatically inserts casts where needed
- Type erasure allows backward compatibility with pre-generics Java code
- Because of type erasure, generic type information is not available at runtime

So, for example, the above `Crate` class becomes as following at compile time,

```java
public class Crate {       
    private Object contents;  
         
    public Object lookInCrate() {          
        return contents;       
    }       

    public void packCrate(Object contents) {          
        this.contents = contents;       
    }    
}
```

**Mental model (one line):**

*Type erasure means generics exist only for compile-time safety; at runtime, all generic types are erased to `Object` and handled using compiler-inserted casts.*

**Returning Generic Types**

When working with overridden methods that return generics, the return values must be covariant. 

In terms of generics, this means the return type of the class or interface declared in the overriding method must be a subtype of the class defined in the parent class. 

The generic parameter type must match its parent’s type exactly.

So, the return type must be covariant, whereas generic type must match.

*Overridden methods may narrow the return type, but generic type parameters must stay the same.*

Example usage,

```java
class Parent {
    List<Number> getValues() {
        return List.of(1, 2.5);
    }
}

class Child extends Parent {
    @Override
    ArrayList<Number> getValues() {
        return new ArrayList<>(List.of(1, 2.5, 3));
    }
}

class BrokenChild extends Parent {
    @Override
    List<Integer> getValues() {   // generic type does not match
        return List.of(1, 2, 3);
    }
}
```

`Child` class method compiles. Because,

- `ArrayList<Number>` is a subtype of `List<Number>`, thus covariant return type
- Generic type `Number` matches exactly which is required

`BrokenChild` class method doesn not compile. Because,

- `List<Integer>` is not a subtype of `List<Number>`, not covariant
- Generic types are invariant, so this fails

**Exam Notes**

- For the exam, it might be helpful to apply type erasure (mentally) to questions involving generics to ensure that they compile properly. 
- Once it is determined which methods are overridden and which are being overloaded, work backward, making sure the generic types match for overridden methods
- And remember, generic methods cannot be overloaded by changing the generic parameter type only.

**Implementing Generic Interfaces**

An interface can declare a formal type parameter. There are three ways a class can approach implementing a generic interface. Example, 

```java
public interface Shippable<T> {   
    void ship(T t);
}

// 1. Implmenting generic type with specific type
class ShippableRobotCrate implements Shippable<Robot> {   
    public void ship(Robot t) {}
}

// 2. Implementing generic type with another generic type
class ShippableAbstractCrate<U> implements Shippable<U> {  
    public void ship(U t) {}
}

// 3. Implementing generic type with non-generic type
class ShippableCrate implements Shippable {   
    public void ship(Object t) {}
}
```

**Generic Limitations**

Most of the limitations are due to type erasure. Oracle refers to a type whose information is fully available at runtime as a reifiable type. Reifiable types can do anything that Java allows. Non-reifiable types have some limitations.

Here are the things that you can’t do with generics,

1. **Call a constructor:** Writing new T() is not allowed because at runtime, it would be new Object(). At runtime, T becomes Object (type erasure). Java has no idea which constructor to call. Example,
   
    ```java
    class Box<T> {
        T value = new T();   // DOES NOT COMPILE
    }
    ```

2. **Create an array of that generic type:** This one is the most annoying, but it makes sense because you’d be creating an array of Object values. Arrays are reifiable; generics are not. At runtime this would be an Object[]. Example,
   
    ```java
    class Store<T> {
        T[] items = new T[10];   // DOES NOT COMPILE
    }

    class Store<T> {
        T[] items;

        Store(T[] items) {
            this.items = items; // Compiles fine
        }
    }
    ```

3. **Call `instanceof`:** This is not allowed because at runtime `List<Integer>` and `List<String>` look the same to Java, thanks to type erasure. `List<String>` and `List<Integer>` both erase to `List` at runtime. `instanceof` works only with reifiable types; `List<?>` is reifiable, but `List<T>` and `List<String>` are not.

    ```java
    if (list instanceof List<String>) {}  // DOES NOT COMPILE

    if (list instanceof List<?>) {}   // OK
    ```

4. **Use a primitive type as a generic type parameter:** This isn’t a big deal because you can use the wrapper class instead. If you want a type of int, just use Integer. Generics work only with reference types.


    ```java
    List<int> numbers = new ArrayList<>();   // DOES NOT COMPILE

    List<Integer> numbers = new ArrayList<>();
    ```


5. **Create a static variable as a generic type parameter:** This is not allowed because the type is linked to the instance of the class. static belongs to the class, but T belongs to the instance.


    ```java
    class Cache<T> {
        static T value;   // DOES NOT COMPILE
    }

    class Cache<T> {
        T value;          // instance variable, compiles fine OK
    }

    class Cache {
        static Object value;   // Compiles fine
    }
    ```

6. **Catch an exception of type T:** Even if T extends Exception, it cannot be used in a catch block since the precise type is not known. At runtime, Java doesn’t know what T actually is.


    ```java
    class Processor<T extends Exception> {
        void process() {
            try { perform(); } 
            catch (T e) {}          // DOES NOT COMPILE
        }
        void perform() throws T {}  /// OK declaring, not catching
    }

    class Processor<T extends Exception> {
        void process() throws T {} // OK declaring, not catching
    }

    class FileProcessor extends Processor<java.io.IOException> {

        void run() throws java.io.IOException {
            process();   // compiler knows T = IOException
        }
    }
    ```

**Exam Notes**

If Java needs the exact type at runtime, generics will probably fail. The followings are invalid,

- `new T()` 
- `new T[]`
- `instanceof List<String>`
- `List<int>`
- `static T`
- `catch (T e)`

**Writing Generic Methods**

Both static and non-static methods can accept or return value of generic types. Example,

```java
public static <T> void prepare(T t) {}   

public static <T> Crate<T> ship(T t) {
    return new Crate<T>();
}
```

Formal type parameter `<T>` is written before the return type.

`<T>` is called a formal type parameter list. This means, “This method is generic, and T is a placeholder type that will be supplied when the method is called.”

`<T>` is written before the return type, because it belongs to the method declaration, not the return type.

Unless a method is obtaining the generic formal type parameter from the class/interface, it is specified immediately before the return type of the method with format type pararmeter list `<T>`. Example, 

```java
public class More {
    public static <T> void sink(T t) { }
    public static <T> T identity(T t) { return t; }
    public static T noGood(T t) { return t; } // DOES NOT COMPILE, <T> missing
}
```

Some valid genric using optional syntax for invoking generic method,

```java
Box.<String>ship("cargo");  // optional generic type of string    
Box.<String[]>ship(args);   // optional generic type of array of string

Box.ship("cargo");  // omitting genric type parameter while calling    
Box.ship(args);     // omitting genric type parameter while calling   
```

When you have a method declare a generic parameter type, it is independent of the class generics. Example,

```java
// Class type <T> and method parameter <T> are independent of each other
public class TrickyCrate<T> {
    public <T> T tricky(T t) {
        return t;
    }
}

// Usage
public static String crateName() {
    TrickyCrate<Robot> crate = new TrickyCrate<>(); // Compiles fine
    return crate.tricky("bot");                     // Compiles fine
}
```

**Creating a Generic Record**

Generics can also be used with records to create immutable generic type record. Example,

```java
public record CrateRecord<T>(T contents) {}

Robot robot = new Robot();
CrateRecord<Robot> record = new CrateRecord<>(robot);
```

**Bounding Generic Types**


**Upper-bounded and Lower-bounded Wildcard**

A bounded parameter type is a generic type that specifies a bound for the generic.

A wildcard generic type is an unknown generic type represented with a question mark (?). ou can use generic wildcards in three ways,

1. Unbounded wildcard. Syntax `<?>`. Example, 
    
    ```java
    List<?> a = new ArrayList<String>();

1. Wildcard with upper bound. Syntax `<? extends type>`. Example, 
    
    ```java
    List<? extends Exception> a = new ArrayList<RuntimeException>();
    ````

1. Wildcard with lower bound. Syntax `<? super type>`. Example, 
    
    ```java
    List<? super Exception> a = new ArrayList<Object>();
    ````

In other words,

- `List<T extends Number>` - T must be `Number` or a subclass (`Integer`, `Double`, etc.)
- `List<? extends Number>` - List of `Number` or any subclass
- `List<? super Integer>` - List of `Integer` or any superclass (`Number`, `Object`, etc.)


**Creating Unbounded Wildcards**

Java generics are invariant.

```java
public static void printList(List<Object> list) {}  // DOES NOT COMPILE
public static void printList(List<?> list) {}       // Compiles fine

List<String> keywords = new ArrayList<>();

keywords.add("java");   
printList(keywords);
```

`List<String>` is NOT a subtype of `List<Object>`. If `List<String>` were a subtype of `List<Object>`, you could pass a `List<String>` to a method expecting `List<Object>` and that method could insert an Integer (or any Object) into it, breaking type safety.

Some interesting generic declaration,

```java
List<?> x1 = new ArrayList<>(); // x1 can be assigned to a List<?> i.e. list of any type
var x2 = new ArrayList<>(); // x2 can only be assigned to a List<Object>
```

**Creating Upper-Bounded Wildcards**

Generic type can’t just use a subclass instead can use a wildcard.

```java
ArrayList<Number> list = new ArrayList<Integer>();      // DOES NOT COMPILE
List<? extends Number> list = new ArrayList<Integer>(); // Compiles fine
```

Something interesting happens when we work with upper bounds or unbounded wildcards. The list becomes logically immutable and therefore cannot be modified. 

```java
static class Bird {}
static class Sparrow extends Bird {}

List<? extends Bird> birds = new ArrayList<Bird>();
birds.add(new Sparrow());   // DOES NOT COMPILE, can’t add a Sparrow to List<? extends Bird>
birds.add(new Bird());      // DOES NOT COMPILE, can’t add a Bird to List<Sparrow>
```

**Creating Lower-Bounded Wildcards**

With a lower bound, we are telling Java that the list will be a list of String objects or a list of some objects that are a superclass of String. 

```java
public static void addSound(List<? super String> list) {   
    list.add("quack");
}
```

Another example for understanding generic super types,

```java
// List can be List<IOException> or List<Exception> or List<Object>
List<? super IOException> exceptions = new ArrayList<Exception>();

exceptions.add(new Exception()); // DOES NOT COMPILE
exceptions.add(new IOException());
exceptions.add(new FileNotFoundException());
```

The first addition of `Excetion` instacne does not compile, because we could have a `List<IOException>` reference, and an `Exception` object wouldn’t fit in there (super type cannot be assigned to sub type).

The second additon of `IOException` instance compiles fine, because it can be added to any of those types.

The third addition of `FileNotFoundException` instance compiles fine, cause it is a subclass of `IOException`.

Few more examples to demonstrate generic declarations,

```java
class A {}
class B extends A {}
class C extends B {}

// Allowed(right): ArrayList<any>()
List<?> list1 = new ArrayList<A>();

// Allowed(right): ArrayList<A>(), ArrayList<B>(), ArrayList<C>()
List<? extends A> list2 = new ArrayList<A>();

// Allowed(right): ArrayList<A>(), ArrayList<Object>()
List<? super A> list3 = new ArrayList<A>();

// Allowed(right): ArrayList<B>(), ArrayList<C>()
List<? extends B> list4 = new ArrayList<A>(); // DOES NOT COMPILE

// Allowed(right): ArrayList<A>(), ArrayList<B>()
List<? super B> list5 = new ArrayList<A>();

// Allowed(right): ArrayList<any>(), but the right should have specific type, not bound (upper-lower)
List<?> list6 = new ArrayList<? extends A>(); // DOES NOT COMPILE
```

**Exam Notes**

1. Object instantiation cannot be upper/lower/unbounded wildcard declaration like the following, that should be scpecific.

    ```java
    List<?> list = new ArrayList<? extends Number>();
    ```

2. Return type cannot be upper/lower/unbounded wildcard declaration like the following. That should be specific,

    ```java
    <T> <? extends T> second(List<? extends T> list) {}
    ```


