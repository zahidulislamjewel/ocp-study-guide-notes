# Chapter 14: Java I/O and NIO.2

---

## Table of Contents

1. [Why This Chapter Matters](#1-why-this-chapter-matters)
2. [Big Picture: I/O vs NIO.2](#2-big-picture-io-vs-nio2)
3. [File System Concepts](#3-file-system-concepts)
4. [The `File` Class](#4-the-file-class)
5. [The `Path` Interface](#5-the-path-interface)
6. [Creating `Path` Objects](#6-creating-path-objects)
7. [Converting Between `File` and `Path`](#7-converting-between-file-and-path)
8. [Viewing `Path` Components](#8-viewing-path-components)
9. [Path Transformations: resolve, relativize, normalize](#9-path-transformations-resolve-relativize-normalize)
10. [More `Path` Methods](#10-more-path-methods)
11. [The `Files` Utility Class](#11-the-files-utility-class)
12. [Reading and Writing File Content with `Files`](#12-reading-and-writing-file-content-with-files)
13. [I/O Stream Fundamentals](#13-io-stream-fundamentals)
14. [Byte Streams vs Character Streams](#14-byte-streams-vs-character-streams)
15. [Low-Level vs High-Level Streams](#15-low-level-vs-high-level-streams)
16. [Core I/O Stream Classes](#16-core-io-stream-classes)
17. [Reading and Writing with Streams](#17-reading-and-writing-with-streams)
18. [Buffered Streams](#18-buffered-streams)
19. [Data Streams](#19-data-streams)
20. [`PrintStream` and `PrintWriter`](#20-printstream-and-printwriter)
21. [`InputStreamReader` and `OutputStreamWriter`](#21-inputstreamreader-and-outputstreamwriter)
22. [`Console` and System Streams](#22-console-and-system-streams)
23. [Serialization and Deserialization](#23-serialization-and-deserialization)
24. [Deserialization Mechanics](#24-deserialization-mechanics)
25. [Custom Serialization with `writeObject`/`readObject`](#25-custom-serialization-with-writeobjectreadobject)
26. [File Attributes with NIO.2](#26-file-attributes-with-nio2)
27. [Traversing Directories with NIO.2 Streams](#27-traversing-directories-with-nio2-streams)
28. [NIO.2 Options and Enums](#28-nio2-options-and-enums)
29. [I/O Exceptions: Which Methods Throw and Why](#29-io-exceptions-which-methods-throw-and-why)
30. [Most Important Exam Traps](#30-most-important-exam-traps)
31. [What Happens If... Scenarios](#31-what-happens-if-scenarios)
32. [Decision Framework for Exam Questions](#32-decision-framework-for-exam-questions)
33. [Quick Reference Cheat Sheet](#33-quick-reference-cheat-sheet)

---

## 1. Why This Chapter Matters

This chapter covers how Java programs interact with data outside the JVM:
- reading and writing data from files
- interacting with files and directories
- working with path objects and transformations
- serializing and deserializing Java objects
- reading user input from the console
- querying file metadata

For the exam, this topic spans two related but distinct APIs:
- classic `java.io` — the original I/O API
- `java.nio.file` (NIO.2) — the modern file system API added in Java 7

### Exam mindset

Stop thinking "memorize all classes." Think in categories:
- file location: `File` or `Path`?
- content access: bytes or characters?
- stream layer: low-level or high-level?
- path manipulation: resolve, relativize, normalize?
- serialization rules: what gets skipped?
- which methods need `IOException` handling?

> **Exam Tip:** In modern Java, prefer `Path` and `Files` over `File`. The exam tests both.

---

## 2. Big Picture: I/O vs NIO.2

### `java.io` — Classic I/O

The original Java I/O package.

Key concepts:
- `File` represents a location in the file system
- stream-based reading and writing
- byte streams (`InputStream`/`OutputStream`)
- character streams (`Reader`/`Writer`)
- serialization with `ObjectInputStream`/`ObjectOutputStream`
- user input via `Console`

### `java.nio.file` — NIO.2

The modern file system API introduced in Java 7.

Key concepts:
- `Path` is an interface representing a file system location
- `Files` has all the static helper methods
- more powerful than `File` for real work
- lazy stream-based directory traversal
- attribute views and POSIX support
- full symbolic link support

### Practical exam rule

Use `File` because the exam includes it.
Use `Path`/`Files` as the preferred model for anything new.

> **Exam Tip:** NIO here means **NIO.2**. The older channel/selector NIO is not the exam focus.

---

## 3. File System Concepts

### File

A file holds data stored on persistent storage (hard disk, SSD, etc.).

### Directory

A directory is a container that can hold files and other directories.

### Root directory

The topmost directory in a file system:
- Unix/Linux: `/`
- Windows: `C:\`

### Path

A path represents the location of a file or directory within a file system.

```java
// absolute paths
Path.of("/zoo/animals.txt");
Path.of("C:\\zoo\\animals.txt");

// relative paths
Path.of("zoo/animals.txt");
Path.of("./animals/bear.txt");
Path.of("../bear.txt");
```

An **absolute path** starts from the root.
A **relative path** is interpreted relative to the current working directory.

### Special path symbols

- `.` means the current directory
- `..` means the parent directory

### Path separator

Each file system uses a separator character between path elements:
- Unix: `/`
- Windows: `\`

Java provides:
```java
System.getProperty("file.separator"); // "/" on Unix, "\\" on Windows
File.separator;                        // same value
```

### Symbolic links

A symbolic link (symlink) is a special file that points to another file or directory.
- `java.io` does not support symbolic links
- `java.nio.file` has full support for creating, detecting, and navigating them

---

## 4. The `File` Class

`java.io.File` is the classic way to represent a file system location.

### Creating `File` objects

```java
File f1 = new File("/home/user/file.txt");
File f2 = new File("/weather/winter", "snow.dat");
File f3 = new File(new File("/weather/winter"), "snow.dat");
```

All three forms are valid. The two-argument constructors let you split parent directory and filename.

### Important: creating the object is not creating the file

```java
File f = new File("/ghost/file.txt"); // no exception; no file created on disk
```

The object just represents a location. Nothing on disk happens until you call an operation.

### Common `File` instance methods

```java
boolean exists()
boolean isFile()
boolean isDirectory()
boolean isAbsolute()
boolean canRead()
boolean canWrite()
boolean canExecute()
long length()
String getAbsolutePath()
String getCanonicalPath()    // resolves symlinks; throws IOException
String getName()
String getParent()
File   getParentFile()
String[] list()              // child names as strings
File[]   listFiles()         // child File objects
boolean mkdir()              // create one directory
boolean mkdirs()             // create directory and any missing parents
boolean delete()
boolean renameTo(File dest)
long lastModified()
```

> **Exam Tip:** `File` methods return `boolean` or `null` on failure rather than throwing exceptions. NIO.2 `Files` methods throw `IOException` instead.

---

## 5. The `Path` Interface

`java.nio.file.Path` is the NIO.2 representation of a file system location.

Key facts:
- it is an **interface**, not a class
- it is **immutable** — every transformation returns a new `Path`
- it can represent files, directories, or paths that do not exist yet
- `toString()` is the only method that returns a `String`

```java
Path path = Path.of("/data/zoo.txt");
```

Immutability in action:

```java
Path p = Path.of("whale");
p.resolve("krill");          // returns new Path; p is unchanged
System.out.println(p);       // whale
```

If you want the result, store it:

```java
Path result = p.resolve("krill");
System.out.println(result);  // whale/krill
```

---

## 6. Creating `Path` Objects

### Most common factory methods

```java
Path p1 = Path.of("/zoo/animals.txt");     // added in Java 11
Path p2 = Paths.get("/zoo/animals.txt");   // pre-Java 11, still valid on exam
```

Both are common on the exam. Know both.

### Varargs form

`Path.of` accepts multiple segments and joins them:

```java
Path p = Path.of("/zoo", "animals", "bear.txt");
// /zoo/animals/bear.txt
```

### From `FileSystem`

```java
Path p = FileSystems.getDefault().getPath("/home/user/file.txt");
```

`FileSystems.getDefault()` returns the platform's default `FileSystem`.

### From `URI`

```java
Path p = Path.of(URI.create("file:///home/user/test.txt"));
```

Use three slashes for an absolute path on Unix: `file:///path`.

### From `File`

```java
File file = new File("/zoo/animals.txt");
Path path = file.toPath();
```

### Reminder

Creating a `Path` or `File` object never creates anything in the file system.

```java
Path p = Path.of("/ghost/file.txt"); // just a Java object; no exception
```

---

## 7. Converting Between `File` and `Path`

From `File` to `Path`:

```java
File file = new File("rabbit");
Path path = file.toPath();
```

From `Path` to `File`:

```java
Path path = Path.of("rabbit");
File file = path.toFile();
```

You often need this when an older API requires a `File` but you are working with NIO.2.

---

## 8. Viewing `Path` Components

```java
Path path = Path.of("/land/hippo/harry.happy");

System.out.println(path.toString());          // /land/hippo/harry.happy
System.out.println(path.getNameCount());      // 3
System.out.println(path.getName(0));          // land
System.out.println(path.getName(1));          // hippo
System.out.println(path.getName(2));          // harry.happy
System.out.println(path.getFileName());       // harry.happy
System.out.println(path.getParent());         // /land/hippo
System.out.println(path.getRoot());           // /
```

For a relative path:

```java
Path rel = Path.of("land/hippo/harry.happy");

System.out.println(rel.getRoot());    // null
System.out.println(rel.getParent());  // land/hippo
```

### Rules to remember

- `getNameCount()` does not count the root
- `getRoot()` returns `null` for relative paths
- `getParent()` returns `null` for root or a single-segment relative path like `Path.of("file.txt")`
- `toString()` is the only method that returns `String` — everything else returns `Path`
- path element indexing starts at 0 and does not include root

### Iterating over elements

```java
for (Path element : Path.of("/land/hippo/harry")) {
    System.out.println(element);
}
// land
// hippo
// harry
```

---

## 9. Path Transformations: resolve, relativize, normalize

This is the highest-value exam section for `Path`.

### 9.1 `resolve()` — path concatenation

Think of `resolve` as appending one path to another.

```java
Path base  = Path.of("/cats");
Path child = Path.of("food");
System.out.println(base.resolve(child));
// /cats/food
```

The critical rule: **if the argument is absolute, it replaces everything**.

```java
System.out.println(Path.of("/turkey/food").resolve("/tiger/cage"));
// /tiger/cage
```

`resolve()` does not clean up `.` or `..`:

```java
Path p = Path.of("/cats/../panther");
System.out.println(p.resolve("food"));
// /cats/../panther/food
```

Rules:
- relative argument: appended to the base
- absolute argument: returned as-is, base is ignored
- no normalization occurs

> **Exam Tip:** When you see `resolve()`, think concatenation, not normalization.

---

### 9.2 `relativize()` — compute relative route

`relativize()` answers: "how do I get from this path to that path?"

```java
var path1 = Path.of("fish.txt");
var path2 = Path.of("friendly/birds.txt");

System.out.println(path1.relativize(path2));   // ../friendly/birds.txt
System.out.println(path2.relativize(path1));   // ../../fish.txt
```

With absolute paths:

```java
System.out.println(
    Path.of("/primate/chimpanzee").relativize(Path.of("/primate/monkey"))
);
// ../monkey
```

**The mixing rule**: both paths must be absolute, or both must be relative.

```java
Path.of("/primate/chimpanzee").relativize(Path.of("bananas.txt"));
// IllegalArgumentException
```

On Windows, both absolute paths must share the same drive letter.

> **Exam Tip:** Mixed absolute and relative in `relativize()` always throws `IllegalArgumentException`.

---

### 9.3 `normalize()` — clean up redundant segments

`normalize()` removes `.` and reducible `..` entries.

```java
System.out.println(Path.of("./armadillo/../shells.txt").normalize());
// shells.txt

System.out.println(Path.of("/cats/../panther/food").normalize());
// /panther/food

System.out.println(Path.of("../../fish.txt").normalize());
// ../../fish.txt  (cannot reduce further)
```

Rules:
- does not access the file system
- only removes what can be reduced
- does not turn a relative path into an absolute one
- returns a new `Path`

---

### 9.4 Combining `resolve()` and `normalize()`

A common pattern:

```java
Path base  = Path.of("/zoo");
Path messy = Path.of("../animals/../fish");
Path clean = base.resolve(messy).normalize();
// /fish
```

---

### 9.5 `toAbsolutePath()`

Converts a relative path to an absolute path by prepending the current working directory.

```java
Path abs = Path.of("zoo.txt").toAbsolutePath();
// e.g. /home/user/zoo.txt
```

Does not access the file system to verify existence.

### 9.6 `toRealPath()`

Resolves the path against the actual file system:
- requires the file to exist
- resolves symbolic links
- normalizes the result
- throws `IOException`

```java
try {
    Path real = Path.of("./zoo/../zoo.txt").toRealPath();
    System.out.println(real);
} catch (IOException e) {
    System.err.println("File does not exist: " + e.getMessage());
}
```

---

## 10. More `Path` Methods

### `subpath(int begin, int end)`

Returns a portion of the path by index range.

```java
Path p = Path.of("/zoo/animals/bear/koala/food.txt");

System.out.println(p.subpath(1, 3)); // animals/bear
System.out.println(p.subpath(0, 1)); // zoo
System.out.println(p.subpath(0, 5)); // zoo/animals/bear/koala/food.txt
```

Rules:
- `begin` is inclusive
- `end` is exclusive
- does not include root
- invalid range throws `IllegalArgumentException`

### `startsWith(Path)` and `endsWith(Path)`

Path-aware prefix and suffix checks — not plain string comparison.

```java
Path path = Path.of("/zoo/animals/bear");

System.out.println(path.startsWith("/zoo"));            // true
System.out.println(path.startsWith("/zoo/animals"));    // true
System.out.println(path.startsWith("/z"));              // false (not a full element)

System.out.println(path.endsWith("bear"));              // true
System.out.println(path.endsWith("animals/bear"));      // true
System.out.println(path.endsWith("s/bear"));            // false — "s" is not a path element
```

`endsWith("s/bear")` is `false` because `"s"` is not a complete path element. `"animals"` is.

---

## 11. The `Files` Utility Class

`java.nio.file.Files` provides static methods for almost every file system operation.

### Existence and type checks (no `IOException`)

```java
Files.exists(path)
Files.notExists(path)
Files.isDirectory(path)
Files.isRegularFile(path)
Files.isSymbolicLink(path)
Files.isReadable(path)
Files.isWritable(path)
Files.isExecutable(path)
```

Note: `exists()` and `notExists()` are not strict opposites. Both can return `false` when the answer is indeterminate (e.g., access denied).

### Size and timestamps (throw `IOException`)

```java
long     size = Files.size(path);
FileTime time = Files.getLastModifiedTime(path);
```

### Creating directories

```java
Files.createDirectory(path);   // single directory; parent must exist
Files.createDirectories(path); // creates directory and any missing parents
```

`createDirectory()` throws `IOException` if the parent does not exist.
`createDirectories()` is idempotent — no error if the directory already exists.

### Temporary files and directories

```java
Path tempFile = Files.createTempFile(null, ".txt");
Path tempDir  = Files.createTempDirectory("prefix");
```

Useful for tests or staging operations.

### Copy

```java
Files.copy(source, target);
Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
```

Copy from an `InputStream` to a file:

```java
Files.copy(new FileInputStream("src.dat"), Path.of("dest.dat"));
```

Copy from a file to an `OutputStream`:

```java
Files.copy(Path.of("src.dat"), System.out);
```

Without `REPLACE_EXISTING`, copy throws `FileAlreadyExistsException` if the target exists.

### Move

```java
Files.move(source, target);
Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
```

`ATOMIC_MOVE` guarantees the move either completes fully or does not happen at all.

### Delete

```java
Files.delete(path);           // throws NoSuchFileException if missing
Files.deleteIfExists(path);   // returns false if missing
```

Neither can delete a non-empty directory. Both throw `DirectoryNotEmptyException` if you try.

### Comparing files

```java
Files.isSameFile(path1, path2);  // true if both point to same actual file
Files.mismatch(path1, path2);    // -1 if identical; else byte position of first difference
```

`isSameFile()` uses the file system to compare. Two paths pointing to the same file via a symlink return `true`.
`Path.equals()` is purely an object-level comparison.

> **Exam Tip:** `Path.equals()` compares path representations. `Files.isSameFile()` compares actual file system targets.

---

## 12. Reading and Writing File Content with `Files`

### Read everything at once

```java
byte[]        bytes = Files.readAllBytes(path);
String        text  = Files.readString(path);
List<String>  lines = Files.readAllLines(path);
```

These load everything into memory. Fine for small files; avoid for large ones.

### Write everything at once

```java
Files.write(path, byteArray);
Files.writeString(path, "content");
Files.write(path, List.of("line1", "line2"));
```

With options:

```java
Files.writeString(path, "appended text", StandardOpenOption.APPEND);
Files.writeString(path, "new content",   StandardOpenOption.WRITE,
                                         StandardOpenOption.TRUNCATE_EXISTING);
```

### Lazy line stream

```java
try (Stream<String> lines = Files.lines(path)) {
    lines.filter(s -> s.startsWith("WARN"))
         .forEach(System.out::println);
}
```

Use this for large files. The stream reads on demand and **must be closed**.

### `readAllLines()` vs `lines()` — exam favorite

```java
List<String>   byList   = Files.readAllLines(path);  // loads all into memory
Stream<String> byStream = Files.lines(path);         // lazy; must be closed
```

The classic trap:

```java
Files.readAllLines(path).filter(s -> s.length() > 5); // DOES NOT COMPILE
```

`List<String>` has no `filter()` method. `Stream<String>` does.

```java
Files.lines(path).filter(s -> s.length() > 5).forEach(...); // correct
```

### Buffered reader/writer via `Files`

```java
try (var reader = Files.newBufferedReader(input);
     var writer = Files.newBufferedWriter(output)) {
    String line;
    while ((line = reader.readLine()) != null) {
        writer.write(line);
        writer.newLine();
    }
}
```

### `InputStream`/`OutputStream` from `Files`

```java
try (InputStream  in  = Files.newInputStream(path, StandardOpenOption.READ);
     OutputStream out = Files.newOutputStream(dest, StandardOpenOption.CREATE)) {
    in.transferTo(out);  // Java 9+: copies all bytes
}
```

---

## 13. I/O Stream Fundamentals

A stream in the I/O sense is a sequence of data flowing in one direction.

### Four abstract base classes

```java
InputStream   // input bytes
OutputStream  // output bytes
Reader        // input characters
Writer        // output characters
```

These are all abstract. Concrete subclasses provide implementations.

### Core read/write loop

```java
int value;
while ((value = in.read()) != -1) {
    out.write(value);
}
```

Why `int` instead of `byte` or `char`?

`read()` must return `-1` to signal end of stream. A `byte` ranges from -128 to 127, and a `char` from 0 to 65535. Neither can carry -1 as a sentinel without ambiguity. `int` can hold all valid byte/char values plus -1.

> **Exam Tip:** `read()` always returns `int`. It returns a value 0–255 for bytes, 0–65535 for chars, or `-1` for end of stream.

### Stream lifecycle

```java
flush();  // force buffered bytes to the destination
close();  // release the underlying resource
```

Always close streams. Prefer try-with-resources.

---

## 14. Byte Streams vs Character Streams

### Byte streams

Work with raw binary data. Class names end in `InputStream` or `OutputStream`.

```java
FileInputStream
FileOutputStream
BufferedInputStream
BufferedOutputStream
ObjectInputStream
ObjectOutputStream
DataInputStream
DataOutputStream
PrintStream
```

### Character streams

Work with text (Unicode characters). Class names end in `Reader` or `Writer`.

```java
FileReader
FileWriter
BufferedReader
BufferedWriter
PrintWriter
InputStreamReader
OutputStreamWriter
StringReader
StringWriter
```

### Rule of thumb

- binary content: use byte streams
- text content: use character streams

### Character encoding

Character streams handle encoding automatically:

```java
Charset utf8  = Charset.forName("UTF-8");
Charset ascii = Charset.forName("US-ASCII");

new FileReader("file.txt", utf8);
new FileWriter("file.txt", utf8);
```

When no charset is specified, the platform default is used.

> **Exam Tip:** `Reader` and `Writer` are still "streams" in the I/O sense even though the class name does not include "Stream."

---

## 15. Low-Level vs High-Level Streams

### Low-level stream

Connects directly to a resource (file, network socket, byte array):

```java
FileInputStream
FileOutputStream
FileReader
FileWriter
```

### High-level stream

Wraps another stream and adds functionality:

```java
BufferedInputStream   // adds buffering
BufferedOutputStream  // adds buffering
BufferedReader        // adds buffering + readLine()
BufferedWriter        // adds buffering + newLine()
ObjectInputStream     // adds object deserialization
ObjectOutputStream    // adds object serialization
DataInputStream       // adds primitive type reading
DataOutputStream      // adds primitive type writing
PrintStream           // adds formatted output
PrintWriter           // adds formatted output
InputStreamReader     // converts bytes to chars (encoding bridge)
OutputStreamWriter    // converts chars to bytes (encoding bridge)
```

### Typical stream chain

```java
var in = new ObjectInputStream(
             new BufferedInputStream(
                 new FileInputStream(file)));
```

Pattern: start with the low-level file stream, wrap with buffering, optionally wrap with a higher-level layer.

This is the **decorator pattern** applied to streams.

---

## 16. Core I/O Stream Classes

### `FileInputStream` and `FileOutputStream`

Low-level byte streams for files.

```java
try (var in  = new FileInputStream("source.bin");
     var out = new FileOutputStream("dest.bin")) {
    int b;
    while ((b = in.read()) != -1) {
        out.write(b);
    }
}
```

### `FileReader` and `FileWriter`

Low-level character streams for files.

```java
try (var reader = new FileReader("source.txt");
     var writer = new FileWriter("dest.txt")) {
    int c;
    while ((c = reader.read()) != -1) {
        writer.write(c);
    }
}
```

`FileWriter` constructor with append flag:

```java
new FileWriter("file.txt", true);  // open for append
```

### `BufferedReader` and `BufferedWriter`

Character streams with line-level methods.

```java
try (var reader = new BufferedReader(new FileReader("src.txt"));
     var writer = new BufferedWriter(new FileWriter("dst.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        writer.write(line);
        writer.newLine();
    }
}
```

- `readLine()` belongs to `BufferedReader`, not plain `Reader`
- `newLine()` belongs to `BufferedWriter`, not plain `Writer`

### `ObjectInputStream` and `ObjectOutputStream`

Read and write serialized Java objects.

```java
out.writeObject(gorilla);
Object obj = in.readObject(); // throws ClassNotFoundException too
```

### `DataInputStream` and `DataOutputStream`

Read and write Java primitive types in a portable binary format. See section 19.

### `PrintStream`

Byte output with formatted output methods. `System.out` and `System.err` are `PrintStream`.

### `PrintWriter`

Character output with formatted output methods. No matching `PrintReader` exists.

### `InputStreamReader` / `OutputStreamWriter`

Encoding bridges. See section 21.

> **Exam Tip:** `PrintWriter` and `PrintStream` are output only. There is no matching input variant.

---

## 17. Reading and Writing with Streams

### One byte/char at a time

```java
void copyBytes(InputStream in, OutputStream out) throws IOException {
    int b;
    while ((b = in.read()) != -1) {
        out.write(b);
    }
}

void copyChars(Reader in, Writer out) throws IOException {
    int c;
    while ((c = in.read()) != -1) {
        out.write(c);
    }
}
```

### Array (block) read/write — more efficient

```java
void copyWithBuffer(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[1024];
    int    lengthRead;

    while ((lengthRead = in.read(buffer, 0, buffer.length)) != -1) {
        out.write(buffer, 0, lengthRead);
        out.flush();
    }
}

void copyWithCharBuffer(Reader in, Writer out) throws IOException {
    char[] buffer = new char[1024];
    int    lengthRead;

    while ((lengthRead = in.read(buffer)) != -1) {
        out.write(buffer, 0, lengthRead);
    }
}
```

`in.read(buffer)` returns the number of bytes/chars actually read, not the buffer size.

### `transferTo()` shorthand (Java 9+)

```java
in.transferTo(out); // copies all bytes from in to out
```

### Common input methods

```java
int  read()
int  read(byte[] b)
int  read(byte[] b, int off, int len)
int  read(char[] c)
int  read(char[] c, int off, int len)
long skip(long n)
int  available()   // estimated bytes available; NOT reliable for EOF detection
```

### Common output methods

```java
void write(int b)
void write(byte[] b)
void write(byte[] b, int off, int len)
void write(char[] c)
void write(char[] c, int off, int len)
void flush()
void close()
```

> **Exam Tip:** Do not use `available()` to check for end-of-stream. It tells you how many bytes are currently available without blocking, not how many remain in total.

---

## 18. Buffered Streams

### Why buffering matters

Each call to `read()` or `write()` on a `FileInputStream` may result in a disk access.
Wrapping with a `BufferedInputStream` lets the JVM read a chunk at a time and serve subsequent calls from memory, reducing system call overhead.

### Character buffered example

```java
try (var reader = new BufferedReader(new FileReader(src));
     var writer = new BufferedWriter(new FileWriter(dest))) {

    String line;
    while ((line = reader.readLine()) != null) {
        writer.write(line);
        writer.newLine();
    }
}
```

### Key points

- `readLine()` is only on `BufferedReader`, not on plain `Reader`
- `newLine()` is only on `BufferedWriter`, not on plain `Writer`
- `readLine()` strips the line terminator
- `newLine()` writes the platform-specific line separator
- `readLine()` returns `null` at end of file (not `-1`)

### `FileNotFoundException`

If the source file does not exist:

```java
new FileInputStream("missing.txt")  // throws FileNotFoundException
new FileReader("missing.txt")       // throws FileNotFoundException
```

`FileNotFoundException` is a subclass of `IOException`.

---

## 19. Data Streams

`DataOutputStream` and `DataInputStream` write and read Java primitives in a fixed binary format.

### Writing

```java
try (var dos = new DataOutputStream(
                   new BufferedOutputStream(
                       new FileOutputStream("data.bin")))) {
    dos.writeBoolean(true);
    dos.writeByte(100);
    dos.writeShort(1000);
    dos.writeInt(1_000_000);
    dos.writeLong(9_999_999_999L);
    dos.writeFloat(3.14f);
    dos.writeDouble(3.14159265);
    dos.writeChar('A');
    dos.writeUTF("hello");
}
```

### Reading

```java
try (var dis = new DataInputStream(
                   new BufferedInputStream(
                       new FileInputStream("data.bin")))) {
    boolean b  = dis.readBoolean();
    byte    by = dis.readByte();
    short   s  = dis.readShort();
    int     i  = dis.readInt();
    long    l  = dis.readLong();
    float   f  = dis.readFloat();
    double  d  = dis.readDouble();
    char    c  = dis.readChar();
    String  st = dis.readUTF();
}
```

**Read order must exactly match write order.** Reading with the wrong type or in the wrong order produces garbage values or exceptions.

---

## 20. `PrintStream` and `PrintWriter`

### Purpose

Formatted output — strings, numbers, objects — with convenient print/println/printf methods.

### `PrintStream` — byte output

```java
System.out.println("Hello");
System.err.println("Error");
System.out.printf("Name: %s, Age: %d%n", "Alice", 30);
System.out.format("Pi: %.4f%n", Math.PI);
```

### `PrintWriter` — character output

```java
try (var pw = new PrintWriter(new FileWriter("output.txt"))) {
    pw.println("Hello");
    pw.printf("Value: %d%n", 42);
}

// PrintWriter can also wrap an OutputStream directly
try (var pw = new PrintWriter(new FileOutputStream("output.txt"))) {
    pw.println("hello");
}
```

### No checked exceptions from print methods

`print()`, `println()`, `printf()`, and `format()` do not declare `IOException`.
That is why `System.out.println()` never needs a try/catch.
Silent failures can be detected with `checkError()`.

### Key differences

```
PrintStream  — wraps OutputStream, byte-based
PrintWriter  — wraps Writer or OutputStream, character-based
```

There is no `PrintReader` or matching input class.

### Line separator

```java
System.lineSeparator();               // platform-specific
System.getProperty("line.separator"); // same
```

Use `%n` in format strings for the platform-specific newline. Do not hardcode `\n`.

> **Exam Tip:** `System.out` and `System.err` are `PrintStream`. `System.in` is `InputStream`.

---

## 21. `InputStreamReader` and `OutputStreamWriter`

### Purpose

Bridge between byte streams and character streams. Handle character encoding.

### `InputStreamReader`

Wraps an `InputStream`, decodes bytes as characters using a specified charset.

```java
Reader reader = new InputStreamReader(System.in);
Reader utf8   = new InputStreamReader(System.in, Charset.forName("UTF-8"));
```

### `OutputStreamWriter`

Wraps an `OutputStream`, encodes characters as bytes.

```java
Writer writer = new OutputStreamWriter(System.out);
Writer utf8   = new OutputStreamWriter(new FileOutputStream("out.txt"), "UTF-8");
```

### Typical use with `System.in`

```java
var reader = new BufferedReader(new InputStreamReader(System.in));
String line = reader.readLine();
```

`System.in` is an `InputStream`. `BufferedReader` needs a `Reader`. `InputStreamReader` bridges the two.

---

## 22. `Console` and System Streams

### System streams

```java
System.in    // InputStream — raw bytes from standard input
System.out   // PrintStream — standard output
System.err   // PrintStream — standard error
```

### `Console`

The `Console` class provides a higher-level interface for interactive command-line programs.

Obtaining it:

```java
Console console = System.console(); // may return null
```

`Console` cannot be instantiated directly:

```java
Console c = new Console(); // DOES NOT COMPILE
```

Always null-check before using:

```java
Console console = System.console();
if (console != null) {
    String name     = console.readLine("Enter name: ");
    char[] password = console.readPassword("Enter password: ");
    console.writer().println("Hello, " + name);
} else {
    System.err.println("No console available");
}
```

### When `Console` is `null`

`System.console()` returns `null` when the JVM is not attached to a terminal — inside an IDE, when piping input/output, or in automated tests.

### `readPassword()`

Returns `char[]` instead of `String`. This is intentional — a `char[]` can be zeroed after use. A `String` would linger in the string pool.

### Other `Console` methods

```java
console.readLine()
console.readLine(String fmt, Object... args)   // with prompt
console.readPassword()
console.readPassword(String fmt, Object... args)
console.writer()   // returns PrintWriter
console.reader()   // returns Reader
console.format(String fmt, Object... args)
console.printf(String fmt, Object... args)
console.flush()
```

### Closing system streams

Do not close `System.in`, `System.out`, or `System.err`.

```java
try (var out = System.out) {}
System.out.println("Hello"); // silently fails — nothing printed
```

```java
var reader = new BufferedReader(new InputStreamReader(System.in));
try (reader) {}
reader.readLine(); // IOException
```

> **Exam Tip:** Closing `System.out` silently swallows subsequent output. Closing `System.in` causes `IOException` on the next read.

---

## 23. Serialization and Deserialization

### What it is

**Serialization**: converting a live Java object in memory into a sequence of bytes that can be saved to a file or sent over a network.

**Deserialization**: reconstructing a Java object from those bytes.

### The marker interface

A class must implement `java.io.Serializable` to be serializable.

```java
import java.io.Serializable;

public class Gorilla implements Serializable {
    private static final long serialVersionUID = 1L;

    private String  name;
    private int     age;
    private Boolean friendly;
    private transient String favoriteFood;  // not serialized
}
```

`Serializable` has no methods — it is a marker interface.

### Rules for a serializable class

1. The class must implement `Serializable`.
2. Every non-transient, non-static instance field must also be serializable (or be `null` at serialization time).
3. If a field is not serializable and not `transient`, serialization throws `NotSerializableException`.

### `transient`

Marks a field as excluded from serialization.

On deserialization, transient fields are restored to their Java default values:
- `null` for object references
- `0` for numeric types
- `false` for `boolean`
- `'\u0000'` for `char`

### `static`

Static fields are not part of the object's state, so they are not serialized.
On deserialization, static fields hold whatever value the class currently has in the JVM.

### `serialVersionUID`

Declare it explicitly:

```java
private static final long serialVersionUID = 1L;
```

If the class structure changes and the `serialVersionUID` no longer matches what was stored, Java throws `InvalidClassException` during deserialization.

### Records and serialization

```java
record Point(int x, int y) {}                        // NOT serializable
record Point(int x, int y) implements Serializable {} // serializable
```

A record follows the same serialization rules as a regular class.

> **Exam Tip:** A record is not automatically serializable. It needs `implements Serializable`.

### Writing objects

```java
void save(List<Gorilla> gorillas, File file) throws IOException {
    try (var out = new ObjectOutputStream(
                       new BufferedOutputStream(
                           new FileOutputStream(file)))) {
        for (Gorilla g : gorillas) {
            out.writeObject(g);
        }
    }
}
```

### Reading objects

```java
List<Gorilla> load(File file) throws IOException, ClassNotFoundException {
    var result = new ArrayList<Gorilla>();

    try (var in = new ObjectInputStream(
                      new BufferedInputStream(
                          new FileInputStream(file)))) {
        while (true) {
            Object obj = in.readObject();
            if (obj instanceof Gorilla g) {
                result.add(g);
            }
        }
    } catch (EOFException e) {
        // normal end of object stream — this is the correct way to detect it
    }

    return result;
}
```

`readObject()` throws both `IOException` and `ClassNotFoundException`.
The loop ends when `EOFException` is thrown — this is the standard pattern.

### Serialization exceptions

| Exception | When |
|---|---|
| `NotSerializableException` | Class does not implement `Serializable` |
| `InvalidClassException` | `serialVersionUID` mismatch or class incompatibility |
| `EOFException` | Unexpected end of data |
| `ClassNotFoundException` | Class not available during deserialization |

> **Exam Tip:** Do not use `available()` to detect EOF on `ObjectInputStream`. Use `EOFException`.

---

## 24. Deserialization Mechanics

This is heavily tested on the exam.

### What happens during deserialization

When a serializable object is deserialized:

1. The constructor of the serialized class is **not called**.
2. Instance initializers of the serialized class are **not run**.
3. Java calls the **no-arg constructor of the first non-serializable ancestor class**.

### Example

```java
class Animal {
    String sound = "generic";

    Animal() {
        System.out.println("Animal() called");
        sound = "roar";
    }
}

class Monkey extends Animal implements Serializable {
    private static final long serialVersionUID = 1L;
    int bananas;

    Monkey(int bananas) {
        this.bananas = bananas;
    }
}
```

When deserializing a `Monkey` with `bananas = 5`:
- `Monkey(int)` is **not** called
- `Animal()` **is** called — it is the first non-serializable parent with a no-arg constructor
- The deserialized `Monkey` has `bananas = 5` (restored from stream)
- `sound` gets `"roar"` because `Animal()` ran

### What if the non-serializable parent has no no-arg constructor?

Java throws `InvalidClassException` during deserialization.

### Field values after deserialization

- Fields stored in stream: restored to their serialized values
- `transient` fields: set to Java default values
- `static` fields: unchanged — they hold whatever value is currently in the JVM

---

## 25. Custom Serialization with `writeObject`/`readObject`

You can customize serialization by implementing these private methods in the class:

```java
private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();  // write all non-transient, non-static fields normally
    out.writeInt(computedValue);  // write extra data
}

private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {
    in.defaultReadObject();    // read all non-transient, non-static fields normally
    this.cache = buildCache(); // reconstruct derived state
}
```

When these methods are present, Java calls them instead of the default mechanism.

### Use cases

- Encrypting sensitive fields before writing
- Compressing data
- Reconstructing derived fields after reading
- Validating data after reading

### `readObjectNoData()`

Called if no data was present for the class (e.g., deserializing from an older version):

```java
private void readObjectNoData() throws ObjectStreamException {
    this.name = "default";
}
```

### `Externalizable`

`Externalizable` extends `Serializable` and gives full control over the serialization format.

```java
public class Config implements Externalizable {
    private String host;
    private int    port;

    public Config() {} // required: public no-arg constructor

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(host);
        out.writeInt(port);
    }

    @Override
    public void readExternal(ObjectInput in)
            throws IOException, ClassNotFoundException {
        host = (String) in.readObject();
        port = in.readInt();
    }
}
```

Key differences from `Serializable`:
- must explicitly implement `writeExternal` and `readExternal`
- must have a public no-arg constructor
- `transient` has no effect
- gives complete control over the serialized format

---

## 26. File Attributes with NIO.2

NIO.2 can read file metadata (attributes) in several ways.

### Single attribute methods

```java
long          size        = Files.size(path);                // throws IOException
boolean       isDir       = Files.isDirectory(path);
boolean       isFile      = Files.isRegularFile(path);
boolean       isSymlink   = Files.isSymbolicLink(path);
boolean       isHidden    = Files.isHidden(path);            // throws IOException
FileTime      modified    = Files.getLastModifiedTime(path); // throws IOException
UserPrincipal owner       = Files.getOwner(path);            // throws IOException
String        contentType = Files.probeContentType(path);    // throws IOException
```

### Bulk reading with `BasicFileAttributes`

More efficient when you need multiple attributes at once:

```java
BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

System.out.println(attrs.size());
System.out.println(attrs.creationTime());
System.out.println(attrs.lastModifiedTime());
System.out.println(attrs.lastAccessTime());
System.out.println(attrs.isDirectory());
System.out.println(attrs.isRegularFile());
System.out.println(attrs.isSymbolicLink());
System.out.println(attrs.isOther());
```

### String-based attribute reading

```java
Map<String, Object> map = Files.readAttributes(path, "basic:size,lastModifiedTime");
```

### Attribute views (updatable)

```java
BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);
view.setTimes(newModified, null, null);
```

### POSIX attributes (Unix/Linux)

```java
PosixFileAttributes posix = Files.readAttributes(path, PosixFileAttributes.class);

System.out.println(PosixFilePermissions.toString(posix.permissions()));
// e.g. rwxr-xr--

Set<PosixFilePermission> perms = posix.permissions();
perms.add(PosixFilePermission.OWNER_EXECUTE);
Files.setPosixFilePermissions(path, perms);
```

### DOS attributes (Windows)

```java
DosFileAttributes dos = Files.readAttributes(path, DosFileAttributes.class);
System.out.println(dos.isReadOnly());
System.out.println(dos.isHidden());
System.out.println(dos.isArchive());
System.out.println(dos.isSystem());
```

> **Exam Tip:** Bulk attribute reads can be more efficient than repeatedly calling single-attribute methods.

---

## 27. Traversing Directories with NIO.2 Streams

Modern NIO.2 uses the Stream API for directory traversal.

### List immediate children only

```java
try (Stream<Path> stream = Files.list(path)) {
    stream.forEach(System.out::println);
}
```

Does not descend into subdirectories.

### Walk entire directory tree

```java
try (Stream<Path> stream = Files.walk(path)) {
    stream.forEach(System.out::println);
}
```

With a depth limit:

```java
try (Stream<Path> stream = Files.walk(path, 2)) {
    stream.forEach(System.out::println);
}
```

Depth 1 is equivalent to `Files.list()`.

### Filter while walking with `Files.find()`

```java
try (Stream<Path> stream = Files.find(path, Integer.MAX_VALUE,
        (p, attrs) -> attrs.isRegularFile() && p.toString().endsWith(".java"))) {
    stream.forEach(System.out::println);
}
```

Parameters: starting path, max depth, `BiPredicate<Path, BasicFileAttributes>`.

### Lazy line reading from a file

```java
try (Stream<String> lines = Files.lines(path)) {
    lines.filter(s -> s.contains("ERROR"))
         .forEach(System.out::println);
}
```

### Search behavior

`Files.walk()` and `Files.find()` use depth-first traversal.
The starting path itself is included in the stream.

### Symbolic links and cycles

By default, `walk()` does not follow symbolic links.
To follow them:

```java
try (Stream<Path> s = Files.walk(path, FileVisitOption.FOLLOW_LINKS)) { ... }
```

If following links creates a cycle, `FileSystemLoopException` is thrown.

### Resource management

File-based streams hold open directory handles and **must be closed**:

```java
try (Stream<Path> s = Files.list(path)) {
    s.forEach(System.out::println);
}
```

In-memory streams from `Collection.stream()` do not hold resources and do not need to be closed.

> **Exam Tip:** `Files.lines()`, `Files.list()`, `Files.walk()`, and `Files.find()` all return streams that must be closed.

### Deleting a directory tree

`Files.delete()` cannot remove non-empty directories. Walk and delete in reverse:

```java
try (Stream<Path> s = Files.walk(root)) {
    s.sorted(Comparator.reverseOrder())
     .forEach(p -> {
         try { Files.delete(p); }
         catch (IOException e) { throw new RuntimeException(e); }
     });
}
```

### Note on older APIs

`DirectoryStream` and `FileVisitor` exist but Stream-based traversal is the exam-relevant approach.

---

## 28. NIO.2 Options and Enums

### `LinkOption`

```java
LinkOption.NOFOLLOW_LINKS
```

Passed to methods to operate on the symbolic link itself rather than its target.

```java
Files.exists(path, LinkOption.NOFOLLOW_LINKS);
Files.copy(link, target, LinkOption.NOFOLLOW_LINKS);
```

### `StandardCopyOption`

```java
REPLACE_EXISTING   // overwrite if target exists
COPY_ATTRIBUTES    // copy file metadata (timestamps, permissions)
ATOMIC_MOVE        // move is all-or-nothing (for Files.move only)
```

### `StandardOpenOption`

Used with `Files.newInputStream`, `Files.newOutputStream`, `Files.write`, `Files.writeString`:

```java
APPEND            // write at end of file
CREATE            // create file if it does not exist
CREATE_NEW        // create file; fail if it already exists
READ              // open for reading
TRUNCATE_EXISTING // truncate to zero on open (used with WRITE)
WRITE             // open for writing
```

### `FileVisitOption`

```java
FOLLOW_LINKS   // follow symbolic links during traversal
```

### Rules to know

- `ATOMIC_MOVE` is meaningful only for `Files.move()`, not for `Files.copy()`
- `REPLACE_EXISTING` must be specified explicitly; without it, overwriting throws an exception
- `CREATE_NEW` fails if the file already exists
- `APPEND` and `TRUNCATE_EXISTING` are for write mode

---

## 29. I/O Exceptions: Which Methods Throw and Why

### The rule of thumb

If a NIO.2 method actually touches the file system in a meaningful way, it throws `IOException`.
If a method is just querying metadata or doing a best-effort check, it typically does not.

Common reasons for `IOException`:
- file is missing
- cannot access the file
- cannot overwrite the target
- I/O communication failure
- invalid file system operation

### Methods that throw `IOException`

```java
Files.size(path)
Files.getLastModifiedTime(path)
Files.isHidden(path)
Files.getOwner(path)
Files.probeContentType(path)
Files.readAttributes(path, ...)
Files.list(path)
Files.walk(path)
Files.find(path, ...)
Files.lines(path)
Files.delete(path)
Files.deleteIfExists(path)
Files.copy(source, target)
Files.move(source, target)
Files.createFile(path)
Files.createDirectory(path)
Files.createDirectories(path)
Files.createTempFile(...)
Files.createTempDirectory(...)
Files.readAllBytes(path)
Files.readString(path)
Files.readAllLines(path)
Files.write(path, ...)
Files.writeString(path, ...)
Files.newBufferedReader(path)
Files.newBufferedWriter(path)
Files.newInputStream(path)
Files.newOutputStream(path)
Files.isSameFile(path1, path2)
Files.mismatch(path1, path2)
new FileInputStream(file)       // throws FileNotFoundException (subclass of IOException)
new FileOutputStream(file)
new FileReader(file)
new FileWriter(file)
path.toRealPath()
```

### Methods that do not throw `IOException` (simple checks)

```java
Files.exists(path)
Files.notExists(path)
Files.isDirectory(path)
Files.isRegularFile(path)
Files.isSymbolicLink(path)
Files.isReadable(path)
Files.isWritable(path)
Files.isExecutable(path)
Path.of(...)
Paths.get(...)
path.normalize()
path.resolve(...)
path.relativize(...)
path.toAbsolutePath()
path.getNameCount()
path.getName(i)
path.getFileName()
path.getParent()
path.getRoot()
path.subpath(...)
path.startsWith(...)
path.endsWith(...)
```

### `FileNotFoundException`

Subclass of `IOException`. Thrown by low-level stream constructors when the source file is missing:

```java
new FileInputStream("missing.txt")  // FileNotFoundException
new FileReader("missing.txt")       // FileNotFoundException
```

---

## 30. Most Important Exam Traps

1. **`Path` is immutable.** `p.resolve("x")` returns a new `Path`. If you do not store the result, nothing changes.

2. **`resolve()` does not normalize.** After `resolve()`, the path may still contain `.` and `..`.

3. **`relativize()` requires both paths to be the same type.** Both absolute or both relative — mixing throws `IllegalArgumentException`.

4. **`subpath()` does not include the root.** Index 0 is the first element after the root.

5. **`getNameCount()` does not count the root.** For `/zoo/animals`, count is 2.

6. **`getRoot()` returns `null` for relative paths.**

7. **`getParent()` returns `null` for top-level relative paths** like `Path.of("file.txt")`.

8. **`Files.readAllLines()` returns `List<String>`**, not `Stream<String>`. Calling `.filter()` on it does not compile.

9. **`Files.lines()` returns `Stream<String>`** and must be closed.

10. **`Console` may be `null`.** Always null-check `System.console()`.

11. **`Console` has no public constructor.** `new Console()` does not compile.

12. **`System.out` and `System.err` are `PrintStream`.** `System.in` is `InputStream`.

13. **Closing `System.out` silently breaks subsequent output.** Closing `System.in` causes `IOException` on the next read.

14. **`PrintWriter` has no `PrintReader` counterpart.**

15. **`read()` returns `int`**, not `byte` or `char`. End of stream is `-1`.

16. **Do not use `available()` for EOF detection on `ObjectInputStream`.**

17. **Serialization requires `implements Serializable`.** No marker interface, no serialization.

18. **`transient` and `static` fields are not serialized.** They get default values after deserialization.

19. **Constructor of the serialized class is not called during deserialization.** The no-arg constructor of the first non-serializable parent is called.

20. **Records are not automatically serializable.** They need `implements Serializable`.

21. **`InvalidClassException` is thrown** when the class has changed since serialization (`serialVersionUID` mismatch).

22. **`delete()` throws `NoSuchFileException` if the file is missing.** `deleteIfExists()` returns `false`.

23. **Neither `delete()` nor `deleteIfExists()` can remove non-empty directories.** Both throw `DirectoryNotEmptyException`.

24. **`Files.isSameFile()` is not `Path.equals()`.** `isSameFile()` checks the actual file system target. `equals()` compares path objects.

25. **Creating a `File` or `Path` object does not create the file.** It only creates a Java object.

26. **`Files.createDirectory()` requires the parent to exist.** Use `createDirectories()` to create the full chain.

27. **`copy()` without `REPLACE_EXISTING` fails if the target already exists.**

28. **`walk()` includes the starting path itself in the stream.**

29. **`Files.list()` is not recursive.** Only immediate children.

30. **`startsWith()` and `endsWith()` are path-element-aware**, not string-aware. `path.endsWith("s/bear")` is `false` even if the string representation ends with `"s/bear"`, because `"s"` is not a complete path element.

---

## 31. What Happens If... Scenarios

### Path transformation not stored

```java
Path p = Path.of("cat");
p.resolve("food");
System.out.println(p);
// Output: cat
```

`p` is unchanged. `resolve()` returns a new `Path`.

### `resolve()` with an absolute argument

```java
System.out.println(Path.of("/a/b").resolve("/x/y"));
// /x/y
```

Absolute argument wins. The base is discarded.

### `relativize()` with mixed types

```java
Path.of("/a/b").relativize(Path.of("c/d"));
// IllegalArgumentException
```

### `readAllLines()` followed by stream operation

```java
Files.readAllLines(path).filter(s -> s.length() > 5);
// Does not compile — List has no filter() method
```

### `Console` is null

```java
Console c = System.console();
c.readLine(); // NullPointerException
```

### Missing file in `FileReader`

```java
new FileReader("ghost.txt");
// FileNotFoundException at runtime
```

### Non-serializable field

```java
class Cat implements Serializable {
    Tail tail = new Tail(); // Tail does not implement Serializable
}
// NotSerializableException when serializing Cat
```

Fix: make `Tail` serializable, or mark `tail` as `transient`.

### Deserializing with transient fields

```java
class Tiger implements Serializable {
    int     stripes = 10;
    transient String name = "Raja";
}
```

After deserializing: `stripes = 10`, `name = null`.

### Deleting non-empty directory

```java
Files.delete(Path.of("/zoo/animals"));
// DirectoryNotEmptyException
```

### Closing `System.out`

```java
try (var out = System.out) { }
System.out.println("Visible?"); // silently ignored — nothing printed
```

### `subpath()` with invalid range

```java
Path.of("/a/b/c").subpath(2, 1);
// IllegalArgumentException — begin >= end
```

### `getRoot()` on a relative path

```java
System.out.println(Path.of("a/b/c").getRoot());
// null
```

### `walk()` starting point included

```java
try (Stream<Path> s = Files.walk(Path.of("/zoo"))) {
    System.out.println(s.count()); // includes /zoo itself
}
```

---

## 32. Decision Framework for Exam Questions

Work through these questions in order when reading an exam question about I/O.

### Step 1 — Location or content?

- Location (where a file is): use `File` or `Path`
- Content (what is in a file): use streams or `Files.readXxx` / `Files.writeXxx`

### Step 2 — Binary or text?

- Binary data: byte stream (`InputStream`/`OutputStream`)
- Text data: character stream (`Reader`/`Writer`) or `Files.readString()` / `readAllLines()` / `lines()`

### Step 3 — Old I/O or NIO.2?

- `File`, `FileReader`, `FileWriter`, `FileInputStream`, `FileOutputStream` — old I/O
- `Path`, `Files`, `Paths` — NIO.2

### Step 4 — Which path operation?

- Concatenate two paths: `resolve()`
- Compute how to get from A to B: `relativize()`
- Remove `.` and `..`: `normalize()`
- Make relative path absolute: `toAbsolutePath()`
- Real file system resolution: `toRealPath()`
- Check if two paths point to same file: `Files.isSameFile()`

### Step 5 — Stream class identification

Read the class name:
- `Reader`/`Writer` — characters
- `InputStream`/`OutputStream` — bytes
- `Buffered` prefix — adds buffering
- `Object` prefix — serialization
- `Data` prefix — primitive types in binary format
- `Print` prefix — formatted output
- `InputStreamReader`/`OutputStreamWriter` — encoding bridge

### Step 6 — Serialization rules?

- Class must implement `Serializable`
- `transient` and `static` fields are skipped
- Constructors are not called during deserialization
- First non-serializable parent's no-arg constructor is called
- `serialVersionUID` mismatch throws `InvalidClassException`

### Step 7 — Does the method touch the file system?

If yes, it likely throws `IOException`.

### Step 8 — What is the return type?

This is where trick questions live:
- `Files.readAllLines()` returns `List<String>` — no `filter()`, no `map()`
- `Files.lines()` returns `Stream<String>` — has `filter()`, must be closed
- `Path` methods return `Path` objects, not strings
- `getNameCount()` returns `int`

---

## 33. Quick Reference Cheat Sheet

### Core representations

```java
File           // old I/O path representation (class)
Path           // NIO.2 path representation (interface, immutable)
Files          // static utility methods for Path
FileSystem     // represents a file system; FileSystems.getDefault() for local
```

### Create paths

```java
Path.of("/a/b")
Path.of("/a", "b", "c")
Paths.get("/a/b")
file.toPath()
path.toFile()
FileSystems.getDefault().getPath("/a/b")
Path.of(URI.create("file:///a/b"))
```

### Path viewing methods

```java
toString()           // String (only method returning String)
getNameCount()       // int (excludes root)
getName(int index)   // Path
getFileName()        // Path (last element)
getParent()          // Path or null
getRoot()            // Path or null
```

### Path transformation methods

```java
resolve(Path or String)       // concatenate
relativize(Path)              // compute relative route
normalize()                   // remove . and ..
toAbsolutePath()              // prepend CWD
toRealPath()                  // real fs path; throws IOException; file must exist
subpath(int begin, int end)   // slice; begin inclusive, end exclusive; excludes root
startsWith(Path)              // path-element-aware prefix check
endsWith(Path)                // path-element-aware suffix check
```

### `Files` — checks (no IOException)

```java
exists(path)
notExists(path)
isDirectory(path)
isRegularFile(path)
isSymbolicLink(path)
isReadable(path)
isWritable(path)
isExecutable(path)
```

### `Files` — metadata (throws IOException)

```java
size(path)
isHidden(path)
getLastModifiedTime(path)
getOwner(path)
probeContentType(path)
readAttributes(path, BasicFileAttributes.class)
```

### `Files` — file operations (throw IOException)

```java
createFile(path)
createDirectory(path)          // parent must exist
createDirectories(path)        // creates missing parents
createTempFile(dir, prefix, suffix)
createTempDirectory(dir, prefix)
copy(source, target, options...)
move(source, target, options...)
delete(path)
deleteIfExists(path)
isSameFile(path1, path2)
mismatch(path1, path2)
```

### `Files` — read/write content (throw IOException)

```java
readAllBytes(path)                     // byte[]
readString(path)                       // String
readAllLines(path)                     // List<String>
lines(path)                            // Stream<String> — must close
write(path, bytes, options...)
writeString(path, text, options...)
write(path, lines, options...)
newBufferedReader(path)
newBufferedWriter(path)
newInputStream(path, options...)
newOutputStream(path, options...)
```

### `Files` — directory traversal (throw IOException, return Stream — must close)

```java
list(path)                             // immediate children only
walk(path)                             // full tree, depth-first; includes start
walk(path, int maxDepth)
find(path, maxDepth, BiPredicate)
```

### Key stream classes

```java
FileInputStream / FileOutputStream
FileReader / FileWriter
BufferedInputStream / BufferedOutputStream
BufferedReader / BufferedWriter
ObjectInputStream / ObjectOutputStream
DataInputStream / DataOutputStream
PrintStream / PrintWriter
InputStreamReader / OutputStreamWriter
StringReader / StringWriter
```

### Stream method reference

```java
read()                         // returns int (0–255 or -1)
read(byte[] / char[])          // returns count read, or -1
write(int)
write(byte[] / char[], off, len)
flush()
close()
readLine()                     // BufferedReader only; returns null at EOF
newLine()                      // BufferedWriter only
readObject()                   // ObjectInputStream; also throws ClassNotFoundException
writeObject(obj)               // ObjectOutputStream
```

### Copy and move options

```java
StandardCopyOption.REPLACE_EXISTING   // overwrite target
StandardCopyOption.COPY_ATTRIBUTES    // copy metadata
StandardCopyOption.ATOMIC_MOVE        // all-or-nothing move
LinkOption.NOFOLLOW_LINKS             // operate on link, not target
```

### Open options

```java
StandardOpenOption.READ
StandardOpenOption.WRITE
StandardOpenOption.APPEND
StandardOpenOption.CREATE
StandardOpenOption.CREATE_NEW
StandardOpenOption.TRUNCATE_EXISTING
```

### Serialization essentials

```java
implements Serializable            // required marker interface
transient                          // skip this field; default value on deserialization
private static final long serialVersionUID = 1L;
writeObject(ObjectOutputStream)   // custom write
readObject(ObjectInputStream)     // custom read
EOFException                       // end of object stream (expected)
NotSerializableException           // class not serializable
InvalidClassException              // serialVersionUID mismatch
ClassNotFoundException             // class not on classpath during read
```

### One-sentence summary

Java I/O for the exam is about choosing the right abstraction (`File` vs `Path`), the right data model (bytes vs characters), understanding stream layering, using `Files` for NIO.2 operations, mastering path transformations, and avoiding the many traps in serialization, return types, and resource management.
