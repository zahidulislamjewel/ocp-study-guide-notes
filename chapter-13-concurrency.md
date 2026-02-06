# Java Concurrency API - Comprehensive Guide

## Table of Contents
1. [Introduction to Concurrency](#introduction-to-concurrency)
2. [Threads and Processes](#threads-and-processes)
3. [ExecutorService Framework](#executorservice-framework)
4. [Callable and Future](#callable-and-future)
5. [ScheduledExecutorService](#scheduledexecutorservice)
6. [Thread Safety](#thread-safety)
7. [Synchronization Mechanisms](#synchronization-mechanisms)
8. [Concurrent Collections](#concurrent-collections)
9. [Threading Problems](#threading-problems)
10. [Parallel Streams](#parallel-streams)
11. [Best Practices](#best-practices)

---

## Introduction to Concurrency

### Key Terminology

**Thread**: A lightweight unit of execution within a process. Multiple threads can run concurrently within a single process.

**Process**: An independent program running in its own memory space. Processes do not share memory with other processes.

**Task**: A unit of work that can be executed by a thread.

**Concurrency**: The ability of multiple tasks to make progress by sharing system resources, even if they don't execute simultaneously.

**Shared Environment**: When multiple threads share the same memory space and can access the same variables and objects.

**Asynchronous vs Synchronous**:
- Synchronous: Tasks execute one after another, blocking until completion
- Asynchronous: Tasks can execute independently without waiting for others

### Thread Types

1. **User-Defined Threads**: Created by application code
2. **System Threads**: Created by the JVM (e.g., garbage collection thread)
3. **Daemon Threads**: Background threads that don't prevent JVM shutdown

### Important Note on Daemon Threads

The JVM waits for all user-defined threads to complete before exiting, but it does NOT wait for daemon threads.

Example:

```java
public class Zoo {
    public static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
        System.out.println("Thread finished");
    }

    public static void main(String[] args) {
        var job = new Thread(() -> pause(10_000));
        job.setDaemon(true);  // Setting as daemon thread
        job.start();
        System.out.println("Main method finished");
    }
}
```

In this example, the main method finishes immediately, and the JVM exits without waiting for the daemon thread to complete its 10-second sleep.

---

## Threads and Processes

### Thread Scheduler

The thread scheduler determines which threads should execute and for how long. It uses various strategies:

**Round-Robin Scheduling**: Each thread gets a time slice (quantum) to execute before the scheduler switches to another thread.

**Context Switch**: The process of saving the state of a currently executing thread and restoring the state of the next thread to execute.

**Thread Priority**: Threads can have priority levels that hint to the scheduler which threads are more important (but this is not guaranteed).

### Thread States

A thread can be in one of six states during its lifecycle:

1. **NEW**: Thread has been created but not started
2. **RUNNABLE**: Thread is executing or ready to execute
3. **BLOCKED**: Thread is waiting to acquire a lock
4. **WAITING**: Thread is waiting indefinitely for another thread to perform an action
5. **TIMED_WAITING**: Thread is waiting for a specified period
6. **TERMINATED**: Thread has completed execution

### Polling vs Interrupt

**Polling**: A thread repeatedly checks a condition in a loop.
- The thread actively waits by continuously checking a variable or state
- Consumes CPU resources even while waiting
- Less efficient

**Interrupt**: A signal sent to a thread to break it out of a blocking operation.
- The thread passively waits (blocked) and is notified when something happens
- More efficient - the thread is awakened only when needed
- Reduces CPU usage

Example of Polling:

```java
public class CheckResults {
    public static int counter = 0;
    public static final int UPPER_LIMIT = 10_000_000;
    
    public static void main(String[] args) {
        var job = new Thread(() -> {
            for (int i = 0; i < UPPER_LIMIT; i++) {
                counter++;
            }
        });
        job.start();

        // POLLING: Main thread repeatedly checks counter
        while(counter < UPPER_LIMIT) {
            System.out.println("Not done yet: " + counter);
            try {
                Thread.sleep(1_000);    
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted");
            }
        }
        System.out.println("Done: " + counter);
    }
}
```

Example of Interrupt:

```java
public class CheckResultsWithInterrupt {
    public static int counter = 0;
    public static final int UPPER_LIMIT = 10_000_000;
    
    public static void main(String[] args) {
        final var mainThread = Thread.currentThread();

        var job = new Thread(() -> {
            for (int i = 0; i < UPPER_LIMIT; i++) {
                counter++;
            }
            // INTERRUPT: Worker thread signals main thread when done
            mainThread.interrupt();
        });
        job.start();

        while(counter < UPPER_LIMIT) {
            System.out.println("Not done yet: " + counter);
            try {
                Thread.sleep(1_000);    
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted");
            }
        }
        System.out.println("Done: " + counter);
    }
}
```

---

## ExecutorService Framework

The `ExecutorService` interface provides a higher-level abstraction for managing threads through a **thread pool**. This is preferred over manually creating `Thread` objects.

### ExecutorService Lifecycle

An ExecutorService has three states:

1. **Active**: Accepting new tasks and executing them
2. **Shutting Down**: Not accepting new tasks, completing existing tasks
3. **Shutdown**: All tasks completed, no longer executing

### Creating ExecutorService

Common factory methods from the `Executors` class:

```java
// Single thread executor - executes tasks sequentially
ExecutorService single = Executors.newSingleThreadExecutor();

// Fixed thread pool - fixed number of threads
ExecutorService fixed = Executors.newFixedThreadPool(10);

// Cached thread pool - creates threads as needed, reuses idle threads
ExecutorService cached = Executors.newCachedThreadPool();

// Scheduled executor - for delayed/periodic tasks
ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(5);
```

### Basic Example

```java
public class SingleThreadExecutorTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            System.out.println("begin");
            
            // Tasks execute sequentially in submission order
            executorService.execute(() -> System.out.println("Running in executor"));
            executorService.execute(() -> {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Count: " + (i+1));
                }
            });
            executorService.execute(() -> System.out.println("Done"));
            
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            System.out.println("finally shutting down executor");
        }
    }
}
```

### Shutting Down ExecutorService

```java
executorService.shutdown();  // Initiates orderly shutdown, no new tasks accepted

// Wait for termination
executorService.awaitTermination(1, TimeUnit.MINUTES);

if (executorService.isTerminated()) {
    System.out.println("All tasks are finished");
} else {
    System.out.println("Some tasks are still running");
}

// Force shutdown immediately
executorService.shutdownNow();  // Attempts to stop all executing tasks
```

---

## Callable and Future

### Runnable vs Callable

**Runnable**: 
- Functional interface with `void run()` method
- Cannot return a value
- Cannot throw checked exceptions

**Callable**:
- Functional interface with `V call()` method
- Can return a value
- Can throw checked exceptions

### Future Interface

The `Future` interface represents the result of an asynchronous computation. It provides methods to:
- Check if the computation is complete
- Wait for completion and retrieve the result
- Cancel the computation

Key methods:
- `V get()`: Waits indefinitely for result
- `V get(long timeout, TimeUnit unit)`: Waits with timeout
- `boolean isDone()`: Checks if computation is complete
- `boolean isCancelled()`: Checks if computation was cancelled
- `boolean cancel(boolean mayInterruptIfRunning)`: Attempts to cancel

Example:

```java
public class CallableTest {
    public static void main(String[] args) throws InterruptedException {
        var executorService = Executors.newSingleThreadExecutor();
        try {
            Future<Integer> futureResult = executorService.submit(() -> new Random().nextInt(100));
            
            System.out.println("Future result: " + futureResult.get());
            System.out.println("Is cancelled: " + futureResult.isCancelled());
            System.out.println("Is done: " + futureResult.isDone());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        executorService.awaitTermination(1, TimeUnit.MINUTES);
        
        if (executorService.isTerminated()) {
            System.out.println("All tasks are finished");
        }
    }
}
```

### Future with Timeout

```java
public class FutureTest {
    private static final int UPPER_LIMIT = 1_000_000_000;
    private static int counter = 0;
    
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            Future<?> futureResult = executorService.submit(() -> {
                for (int i = 0; i < UPPER_LIMIT; i++) {
                    counter++;
                }
            });
            
            // Wait with timeout
            futureResult.get(10, TimeUnit.SECONDS);
            System.out.println("Final counter value: " + counter);
            
        } catch (TimeoutException e) {
            System.out.println("Task timed out before completion");
        } finally {
            executorService.shutdown();
        }
    }
}
```

---

## ScheduledExecutorService

The `ScheduledExecutorService` extends `ExecutorService` to support delayed and periodic task execution.

### Key Methods

```java
// Execute once after delay
ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit);
ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit);

// Execute periodically with fixed rate
ScheduledFuture<?> scheduleAtFixedRate(Runnable command, 
                                       long initialDelay, 
                                       long period, 
                                       TimeUnit unit);

// Execute periodically with fixed delay between executions
ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, 
                                          long initialDelay, 
                                          long delay, 
                                          TimeUnit unit);
```

### scheduleAtFixedRate vs scheduleWithFixedDelay

**scheduleAtFixedRate**: Executes at fixed intervals, regardless of execution time
- If a task takes longer than the period, the next execution starts immediately

**scheduleWithFixedDelay**: Waits for the specified delay AFTER the previous execution completes
- Guarantees a minimum delay between executions

### Example

```java
public class ScheduledExecutorTest {
    public static void main(String[] args) throws InterruptedException {
        var executorService = Executors.newSingleThreadScheduledExecutor();
        try {
            Runnable task = () -> System.out.println("Task executed after delay");
            Callable<Integer> callableTask = () -> new Random().nextInt(100);
            Runnable periodicTask = () -> System.out.println("Periodic task: " + new Random().nextInt(100));
            
            // Schedule once after 10 seconds
            ScheduledFuture<?> future1 = executorService.schedule(task, 10, TimeUnit.SECONDS);
            
            // Schedule once after 1 minute
            ScheduledFuture<Integer> future2 = executorService.schedule(callableTask, 1, TimeUnit.MINUTES);
            
            // Schedule at fixed rate: initial delay 1s, then every 1s
            ScheduledFuture<?> future3 = executorService.scheduleAtFixedRate(
                periodicTask, 1, 1, TimeUnit.SECONDS
            );

            System.out.println("Scheduled task result: " + future1.get());
            System.out.println("Get delay: " + future2.getDelay(TimeUnit.SECONDS) + " seconds");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
```

---

## Thread Safety

**Thread-safe code** is code that can be safely executed by multiple threads concurrently without producing incorrect results or entering an invalid state.

### Race Condition

A **race condition** occurs when two or more threads access shared data concurrently, and at least one thread modifies the data, leading to unpredictable results. The outcome depends on the timing of thread execution.

### Memory Consistency Error

A **memory consistency error** occurs when different threads have inconsistent views of the same data. This happens because changes made by one thread might not be immediately visible to other threads.

### Thread Safety Mechanisms

Java provides several mechanisms to ensure thread safety:

1. **Atomic Classes** (`AtomicInteger`, `AtomicBoolean`, `AtomicLong`)
2. **synchronized blocks**
3. **Lock framework** (`ReentrantLock`)
4. **CyclicBarrier**
5. **Concurrent Collections**

---

## Synchronization Mechanisms

### 1. The Problem: Unsynchronized Access

```java
// This code has a race condition!
public class SheepManagerUnsafe {
    private int sheepCount = 0;
    
    public void incrementAndReport() {
        // Two operations: read and write - not atomic!
        System.out.print((++sheepCount) + " ");
    }
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        SheepManagerUnsafe manager = new SheepManagerUnsafe();
        for(int i=0; i<20; i++) {
            executorService.submit(() -> manager.incrementAndReport());
        }
        executorService.shutdown();
    }
}
// Output may show duplicates or missing numbers!
```

### 2. Volatile Keyword

The `volatile` keyword ensures that changes to a variable are immediately visible to all threads. However, it does NOT make compound operations atomic.

```java
public class SheepManagerVolatile {
    private volatile int sheepCount = 0;
    
    public void incrementAndReport() {
        // Still has race condition! 
        // ++sheepCount is two operations: read and write
        System.out.print((++sheepCount) + " ");
    }
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        SheepManagerVolatile manager = new SheepManagerVolatile();
        for(int i=0; i<20; i++) {
            executorService.submit(() -> manager.incrementAndReport());
        }
        executorService.shutdown();
    }
}
// Still produces incorrect results!
```

**Note**: `volatile` alone is insufficient for thread safety when multiple operations are involved. It only guarantees visibility, not atomicity.

### 3. Atomic Classes

Atomic classes provide lock-free thread-safe operations on single variables.

```java
public class SheepManagerAtomic {
    private AtomicInteger sheepCount = new AtomicInteger(0);
    
    public void incrementAndReport() {
        // incrementAndGet() is atomic
        System.out.print((sheepCount.incrementAndGet()) + " ");
    }
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        SheepManagerAtomic manager = new SheepManagerAtomic();
        for(int i=0; i<20; i++) {
            executorService.submit(() -> manager.incrementAndReport());
        }
        executorService.shutdown();
    }
}
// Produces correct results!
```

**Common Atomic Classes**:
- `AtomicBoolean`
- `AtomicInteger`
- `AtomicLong`
- `AtomicReference`

**Common Methods**:
- `get()`: Returns current value
- `set(newValue)`: Sets new value
- `incrementAndGet()`: Equivalent to ++value
- `getAndIncrement()`: Equivalent to value++
- `compareAndSet(expect, update)`: Sets value if current value equals expected

### 4. Synchronized Blocks

A **synchronized block** uses a monitor (lock) to ensure that only one thread can execute the critical section at a time. This is called **mutual exclusion** (mutex).

```java
public class SheepManagerSynchronized {
    private int sheepCount = 0;
    private final Object lock = new Object();
    
    public void incrementAndReport() {
        synchronized(lock) {
            // Critical section - only one thread at a time
            System.out.print((++sheepCount) + " ");
        }
    }
    
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        SheepManagerSynchronized manager = new SheepManagerSynchronized();
        for(int i=0; i<20; i++) {
            executorService.submit(() -> manager.incrementAndReport());
        }
        executorService.shutdown();
    }
}
// Produces correct results!
```

**Key Points**:
- The code inside `synchronized(lock)` is called a **critical section**
- Only one thread can hold the lock at a time
- Other threads must wait until the lock is released
- Any object can be used as a lock
- Can also synchronize on `this` or the class object

### 5. Lock Framework

The `Lock` interface provides more flexible locking operations than synchronized blocks.

```java
public class LockExample {
    public static void printHello(Lock lock) {
        lock.lock();  // Acquire lock
        try {
            System.out.println("Hello, World!");
        } finally {
            lock.unlock();  // Always unlock in finally block!
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        
        for (int i = 0; i < 100; i++) {
            new Thread(() -> printHello(lock)).start();
        }

        // tryLock attempts to acquire without waiting
        if(lock.tryLock()) {
            try {
                System.out.println("Acquired lock immediately");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Could not acquire lock");
        }

        // tryLock with timeout
        if(lock.tryLock(5, TimeUnit.SECONDS)) {
            try {
                System.out.println("Acquired lock with timeout");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Timeout expired");
        }
    }
}
```

**Lock Methods**:
- `lock()`: Acquires lock, waits if necessary
- `unlock()`: Releases lock
- `tryLock()`: Attempts to acquire lock immediately, returns boolean
- `tryLock(long time, TimeUnit unit)`: Attempts to acquire with timeout

**Critical**: Always release a lock the same number of times it is acquired. Use try-finally blocks to ensure locks are released even if exceptions occur.

**ReentrantLock**: The most common `Lock` implementation. Called "reentrant" because a thread can acquire the same lock multiple times.

### 6. CyclicBarrier

`CyclicBarrier` is a synchronization mechanism that allows a fixed number of threads to wait for each other at a common barrier point before continuing execution.

**Key Characteristics**:
- **Coordination**: Ensures all threads complete a phase before any thread moves to the next phase
- **Thread-Safety**: Part of Java's concurrency utilities for safe multithreading
- **Blocking**: Threads that reach the barrier first will wait (block) until all threads arrive
- **Barrier Action**: Optional task that runs exactly once when all threads meet at the barrier
- **Cyclic**: The barrier is reusable - after all threads pass through, it automatically resets

**Use Case**: Perfect for scenarios where work must be done in synchronized phases.

```java
public class LionPenManager {
    public void removeLions() {
        System.out.println("Removing lions from the pen.");
    }

    private void cleanPen() {
        System.out.println("Cleaning the lion pen.");
    }

    public void addLions() {
        System.out.println("Adding lions to the pen.");
    }

    public void performTask(CyclicBarrier c1, CyclicBarrier c2) {
        try {
            removeLions();
            c1.await();  // Wait for all 4 threads
            cleanPen();
            c2.await();  // Wait for all 4 threads
            addLions();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var executorService = Executors.newFixedThreadPool(4);
        try {
            var manager = new LionPenManager();
            var c1 = new CyclicBarrier(4);
            var c2 = new CyclicBarrier(4, () -> System.out.println("*** Pen Cleaned!"));
            
            for (int i = 0; i < 4; i++) {
                executorService.submit(() -> manager.performTask(c1, c2));
            }
        } finally {
            executorService.shutdown();
        }
    }
}
```

**Comparison**:
- **Different from locks**: Locks prevent concurrent access; CyclicBarrier coordinates synchronized progress
- **Different from polling**: No CPU waste - threads sleep until all arrive

---

## Concurrent Collections

When multiple threads try to modify the same non-concurrent collection, the JVM may throw a `ConcurrentModificationException` at runtime.

**Important**: If a collection is immutable (and contains immutable objects), concurrent collections are not necessary. Immutable objects can be accessed by any number of threads without synchronization.

### Common Concurrent Collections

1. **ConcurrentHashMap**: Thread-safe HashMap
2. **ConcurrentLinkedQueue**: Thread-safe Queue
3. **ConcurrentSkipListMap**: Thread-safe sorted Map
4. **ConcurrentSkipListSet**: Thread-safe sorted Set
5. **CopyOnWriteArrayList**: Thread-safe List for read-heavy scenarios
6. **CopyOnWriteArraySet**: Thread-safe Set for read-heavy scenarios
7. **LinkedBlockingQueue**: Thread-safe blocking Queue

### ConcurrentHashMap Example

```java
public class ConcurrentCollectionExample {
    public static void main(String[] args) {
        // HashMap would throw ConcurrentModificationException
        // var foodData = new HashMap<String, Integer>();
        
        var foodData = new ConcurrentHashMap<String, Integer>();
        foodData.put("lion", 5);
        foodData.put("tiger", 3);
        foodData.put("penguin", 1);
        foodData.put("flamingo", 2);

        for (var entry : foodData.entrySet()) {
            System.out.println(entry.getKey() + " has " + entry.getValue() + " pieces of food.");
            foodData.remove(entry.getKey());  // Safe with ConcurrentHashMap
        }
    }
}
```

### CopyOnWriteArrayList

`CopyOnWriteArrayList` creates a new copy of the underlying array for every write operation. This makes it ideal for scenarios with many reads and few writes.

```java
public class CopyOnWriteTest {
    public static void main(String[] args) {
        // ArrayList would throw ConcurrentModificationException
        // List<Integer> favoriteNumbers = new ArrayList<>(List.of(4, 3, 32));
        
        List<Integer> favoriteNumbers = new CopyOnWriteArrayList<>(List.of(4, 3, 32));
        
        for (var num : favoriteNumbers) {
            System.out.print(num + " ");
            favoriteNumbers.add(num + 1);  // Safe with CopyOnWriteArrayList
        }
        System.out.println();
        System.out.println(favoriteNumbers);
        System.out.println(favoriteNumbers.size());
    }
}
```

**Note**: Modifications during iteration don't affect the current iteration. The iterator operates on a snapshot of the collection.

---

## Threading Problems

### 1. Deadlock

**Deadlock** occurs when two or more threads are blocked forever, waiting for each other to release locks.

**Deadlock Conditions** (all must be present):
1. **Mutual Exclusion**: Only one thread can hold a resource at a time
2. **Hold and Wait**: Thread holds at least one resource while waiting for another
3. **No Preemption**: Resources cannot be forcibly taken from threads
4. **Circular Wait**: Circular chain of threads waiting for resources held by the next thread

**Preemption** in multithreading is an operating system mechanism where the scheduler interrupts a running thread to allow another thread to execute.

Example of Deadlock:

```java
class Food {}
class Water {}

class Fox {
    private String name;

    public Fox(String name) {
        this.name = name;
    }

    public void eatAndDrink(Food food, Water water) {
        synchronized (food) {
            System.err.println(this.name + " Got Food!");
            move();
            synchronized (water) {  // Waiting for water
                System.err.println(this.name + " Got Water!");
            }
        }
    }

    public void drinkAndEat(Food food, Water water) {
        synchronized (water) {
            System.err.println(this.name + " Got Water!");
            move();
            synchronized (food) {  // Waiting for food
                System.err.println(this.name + " Got Food!");
            }
        }
    }

    public void move() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
    }
}

public class DeadLockTest {
    public static void main(String[] args) {
        var foxy = new Fox("Foxy");
        var tails = new Fox("Tails");
        var food = new Food();
        var water = new Water();

        var service = Executors.newScheduledThreadPool(10);
        try {
            // Foxy: locks food, waits for water
            service.submit(() -> foxy.eatAndDrink(food, water));
            
            // Tails: locks water, waits for food
            service.submit(() -> tails.drinkAndEat(food, water));
            // DEADLOCK!
        } finally {
            service.shutdown();
        }
    }
}
```

**Deadlock Prevention and Detection**:
1. **Lock Reordering**: Always acquire locks in the same order
2. **Timeout Backoff**: Use `tryLock()` with timeout and retry
3. **Deadlock Detection**: Monitor and detect circular waits

### 2. Starvation

**Starvation** occurs when a thread is perpetually denied access to a shared resource, preventing it from making progress. This typically happens when:
- Higher-priority threads continuously monopolize resources
- Unfair lock scheduling

### 3. Livelock

**Livelock** occurs when threads are actively responding to each other but making no progress. Unlike deadlock, threads are not blocked but are stuck in a loop of state changes.

Example: Two people in a hallway repeatedly stepping aside in the same direction, blocking each other.

---

## Parallel Streams

Java 8 introduced parallel streams to leverage multi-core processors for concurrent data processing.

### Creating Parallel Streams

```java
// From existing stream
stream.parallel()

// Directly from collection
collection.parallelStream()
```

### Performance Comparison

```java
public class ParallelStreamTest {
    public static final int MAX = 10_000_000;

    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }
    
    public static void testSequentialStream() {
        var start = System.currentTimeMillis();
        var count = IntStream.range(1, MAX)
                .filter(ParallelStreamTest::isPrime)
                .count();
        System.out.println("Sequential - Primes found: " + count);
        var end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");
    }

    public static void testParallelStream() {
        var start = System.currentTimeMillis();
        var count = IntStream.range(1, MAX)
                .parallel()
                .filter(ParallelStreamTest::isPrime)
                .count();
        System.out.println("Parallel - Primes found: " + count);
        var end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}
```

### Parallel Streams with Latency

```java
public class ParallelStreamWithSleepTest {
    public static int doWork(int n) {
        try {
            Thread.sleep(5_000);  // Simulate expensive operation
        } catch (InterruptedException e) {}
        return n;
    }
    
    public static void testSequentialStream() {
        var start = System.currentTimeMillis();
        List.of(4, 9, 2, 3, 5).stream()
                .map(w -> doWork(w))
                .forEach(n -> System.out.print(n + " "));
        var end = System.currentTimeMillis();
        System.out.println("\nTime: " + (end - start) + " ms");
        // Takes ~25 seconds (5 elements * 5 seconds each)
    }

    public static void testParallelStream() {
        var start = System.currentTimeMillis();
        List.of(4, 9, 2, 3, 5).parallelStream()
                .map(w -> doWork(w))
                .forEach(n -> System.out.print(n + " "));
        var end = System.currentTimeMillis();
        System.out.println("\nTime: " + (end - start) + " ms");
        // Takes ~5 seconds (all elements processed concurrently)
    }

    public static void testParallelStreamOrdered() {
        var start = System.currentTimeMillis();
        List.of(4, 9, 2, 3, 5).parallelStream()
                .map(w -> doWork(w))
                .forEachOrdered(n -> System.out.print(n + " "));
        var end = System.currentTimeMillis();
        System.out.println("\nTime: " + (end - start) + " ms");
        // Takes ~5 seconds but maintains order
    }
}
```

**Note**: `forEach()` on parallel streams doesn't guarantee order, but `forEachOrdered()` does (though it may reduce performance benefits).

### Parallel Reduction

Reduction operations combine elements to produce a single result. For parallel streams, the reduction operation must be:
- **Associative**: `(a op b) op c = a op (b op c)`
- **Non-interfering**: Doesn't modify the stream source
- **Stateless**: Result doesn't depend on state that might change

```java
public class ParallelReductionTest {
    public static void main(String[] args) {
        var numbers = List.of(4, 9, 2, 3, 5, 7, 8, 1, 6, 0);

        // Safe: Addition is associative
        numbers.parallelStream()
                .reduce((a, b) -> a + b)
                .ifPresent(result -> System.out.println("Associative (Safe): " + result));

        // Unsafe: Subtraction is not associative
        numbers.parallelStream()
                .reduce((a, b) -> a - b)
                .ifPresent(result -> System.out.println("Non-Associative (Unsafe): " + result));
        // Result is unpredictable!
    }
}
```

### Parallel Reduction with Identity and Combiner

```java
var chars = List.of("a", "b", "c", "d", "e");

// Sequential: identity used once
var sequential = chars.stream()
        .reduce("X", String::concat);
System.out.println(sequential);  // Xabcde

// Parallel: identity used for EACH thread!
var parallel = chars.parallelStream()
        .reduce("X", String::concat);
System.out.println(parallel);  // XXXXXabcde (unpredictable)

// Parallel with combiner: correct result
var parallelWithCombiner = chars.parallelStream()
        .reduce("X", 
                String::concat,           // accumulator
                (u, v) -> u + "|" + v);  // combiner
System.out.println(parallelWithCombiner);  // X|X|X|X|Xa|b|c|d|e
```

### Parallel Reduction Requirements

For efficient parallel reduction with `collect()`:
1. The stream should be parallel
2. Parameter of `collect()` should have `Characteristics.CONCURRENT` characteristic
3. Stream should be unordered OR collector should have `Characteristics.UNORDERED` characteristic

### Collecting to Concurrent Collections

```java
var letters = List.of("t", "a", "s", "b", "u", "c", "d", "e", "v", "p");

// Collect to StringBuilder
var result = letters.stream().parallel()
        .collect(
            StringBuilder::new,      // supplier
            StringBuilder::append,   // accumulator
            StringBuilder::append    // combiner
        ).toString();

// Collect to concurrent sorted set
var sortedSet = letters.stream().parallel()
        .collect(
            ConcurrentSkipListSet::new,  // supplier
            Set::add,                     // accumulator
            Set::addAll                   // combiner
        );
System.out.println(sortedSet);  // [a, b, c, d, e, p, s, t, u, v]
```

### Concurrent Collectors

```java
var animals = List.of("lions", "tigers", "bears", "wolves", "foxes", "eagles");

// Concurrent map with merge function
ConcurrentMap<Integer, String> animalMap = animals.stream()
        .collect(Collectors.toConcurrentMap(
            String::length,              // key
            k -> k,                      // value
            (s1, s2) -> s1 + "," + s2   // merge function
        ));
System.out.println(animalMap);  // {5=lions,bears,foxes, 6=tigers,wolves,eagles}

// Concurrent grouping
ConcurrentMap<Integer, List<String>> grouped = animals.stream()
        .collect(Collectors.groupingByConcurrent(String::length));
System.out.println(grouped);  // {5=[lions, bears, foxes], 6=[tigers, wolves, eagles]}
```

### Stateful Lambda Expressions

A **stateful lambda expression** is one whose result depends on any state that might change during pipeline execution. Side effects can appear in parallel streams if lambda expressions are stateful.

Unsafe Example:

```java
public class StatefulLambdaTest {
    public static List<Integer> addValues(IntStream source) {
        var data = Collections.synchronizedList(new ArrayList<Integer>());
        source.filter(s -> s % 2 == 0)
                .forEach(n -> data.add(n));  // Side effect!
        return data;
    }

    public static void main(String[] args) {
        // Sequential: works fine
        var list = addValues(IntStream.range(0, 20));
        System.out.println(list);  // [0, 2, 4, 6, 8, 10, 12, 14, 16, 18]

        // Parallel: unpredictable order!
        var parallelList = addValues(IntStream.range(0, 20).parallel());
        System.out.println(parallelList);  // Order may vary: [0, 4, 2, 6, 8, ...]
    }
}
```

Safe Example:

```java
public static List<Integer> addValuesSafely(IntStream source) {
    // No side effects - uses terminal operation
    return source.filter(s -> s % 2 == 0)
            .boxed()
            .collect(Collectors.toList());
}

public static void main(String[] args) {
    var safeList = addValuesSafely(IntStream.range(0, 20).parallel());
    System.out.println(safeList);  // Always correct
}
```

**Key Takeaway**: Avoid stateful lambda expressions in parallel streams. Use built-in collectors instead of manually accumulating results.

---

## Best Practices

### 1. Prefer ExecutorService over Thread

```java
// Bad
new Thread(() -> doWork()).start();

// Good
ExecutorService executor = Executors.newSingleThreadExecutor();
executor.submit(() -> doWork());
executor.shutdown();
```

### 2. Always Shutdown ExecutorService

```java
ExecutorService executor = Executors.newFixedThreadPool(10);
try {
    // Submit tasks
} finally {
    executor.shutdown();  // Always shutdown!
}
```

### 3. Release Locks in Finally Blocks

```java
Lock lock = new ReentrantLock();
lock.lock();
try {
    // Critical section
} finally {
    lock.unlock();  // Always unlock in finally!
}
```

### 4. Use Appropriate Collections

- Use concurrent collections when multiple threads access the same collection
- Use immutable collections when possible (no synchronization needed)
- Choose the right concurrent collection for your use case:
  - High-concurrency reads/writes: `ConcurrentHashMap`
  - Many reads, few writes: `CopyOnWriteArrayList`
  - Blocking operations: `LinkedBlockingQueue`

### 5. Avoid Deadlocks

- Always acquire locks in the same order
- Use timeouts with `tryLock()`
- Keep synchronized blocks small
- Avoid calling external methods while holding locks

### 6. Use Parallel Streams Wisely

Parallel streams are beneficial when:
- Processing large datasets
- Operations are CPU-intensive
- Operations are independent

Avoid parallel streams when:
- Dataset is small (overhead outweighs benefits)
- Operations are I/O-bound
- Operations have side effects
- Order matters significantly

### 7. Ensure Thread Safety with Atomic Operations

```java
// Bad: Not thread-safe
private int counter = 0;
counter++;

// Good: Thread-safe
private AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();

// Also good: Synchronized
private int counter = 0;
synchronized(lock) {
    counter++;
}
```

### 8. Use Immutable Objects

Immutable objects are inherently thread-safe and don't require synchronization.

```java
// Immutable class
public final class ImmutablePoint {
    private final int x;
    private final int y;
    
    public ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
}
```

### 9. Understand Parallel Stream Requirements

For parallel reductions:
- Accumulator must be associative
- Combiner must be compatible with accumulator
- Identity value must be a true identity for the accumulator
- Operations must be stateless and non-interfering

### 10. Monitor Thread Pool Sizes

- Too few threads: Underutilized resources
- Too many threads: Excessive context switching overhead

Common formulas:
- CPU-bound tasks: `Runtime.getRuntime().availableProcessors()`
- I/O-bound tasks: Higher thread count (e.g., 2x or more of CPU count)

---

## Summary

Java's concurrency API provides powerful tools for writing multi-threaded applications:

1. **ExecutorService**: Manages thread pools and task execution
2. **Callable and Future**: Support for tasks that return results
3. **ScheduledExecutorService**: Delayed and periodic task execution
4. **Thread Safety Mechanisms**: Atomic classes, synchronized blocks, locks
5. **Concurrent Collections**: Thread-safe data structures
6. **CyclicBarrier**: Coordinate multiple threads at synchronization points
7. **Parallel Streams**: Leverage multi-core processors for data processing

Understanding these concepts and applying best practices will help you write efficient, correct, and maintainable concurrent Java applications.

---

## Key Takeaways

- Always properly shutdown ExecutorService instances
- Release locks in finally blocks to prevent deadlocks
- Use concurrent collections when multiple threads access shared data
- Prefer atomic operations and high-level concurrency utilities over low-level synchronization
- Be cautious with parallel streams - ensure operations are stateless, non-interfering, and associative
- Understand the differences between polling and interrupts
- Avoid stateful lambda expressions in parallel streams
- Choose the right tool for your concurrency needs based on your specific requirements