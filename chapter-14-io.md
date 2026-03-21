# Chapter 14: I/O

## OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER:

### Using Java I/O API

- Read and write console and file data using I/O streams.
- Serialize and de-serialize Java objects.
- Construct, traverse, create, read, and write Path objects and their properties using the java.nio.file API.

---

**Introduction**

The preferred approach for working with files and directories with newer software applications is to use NIO.2 rather than I/O where possible. 

NIO stands for non-blocking input/output API and is sometimes referred to as new I/O. The exam covers NIO version 2. 

**Referencing Files and Directories**

Two relevant class and interface,

- `File` class 
- `Path` interface 

**Conceptualizing the File System**

Data is stored on persistent storage devices, such as hard disk drives and memory cards. A file within the storage device holds data.

Files are organized into hierarchies using directories. A directory is a location that can contain files as well as other directories.

The file system is in charge of reading and writing data within a computer.

The JVM will automatically connect to the local file system (Windows or Unix), allowing us to perform the same operations across multiple platforms.

The root directory is the topmost directory in the file system, from which all files and directories inherit. In Windows, it is denoted with a drive letter such as `C:\`, while on Linux, it is denoted with a single forward slash, `/`.

A path is a representation of a file or directory within a file system. 

Each file system defines its own path separator character that is used between directory entries.

Unix-based systems use the forward slash, `/`, for paths, whereas Windows-based systems use the backslash, `\`, character. Java offers a system property to retrieve the local separator character for the current environment:

```java
System.getProperty("file.separator");

File.separator;
```

**Absolute vs. Relative Paths**

`normalize()` method can be used to remove redundancy in path symbols.

A symbolic link (often referred to as `symlink`) is a special file within a file system that serves as a reference or pointer to another file or directory. 
symbolic links are transparent to the user, as the operating system takes care of resolving the reference to the actual file

The I/O APIs do not support symbolic links, NIO.2 includes full support for creating, detecting, and navigating symbolic links within the file system.

**Creating a File or Path**

The `java.io.File` class and `java.nio.file.Path` interface cannot read or write data within a file, although they are passed as a reference to other classes.

File or Path can represent a file or a directory.

Few ways for creating file,

```java
File file1 = new File("/home/user/file.txt");
```

The most common way to create a Path is the following:

```java
public static Path of(String first, String… more)
Files.exists(pathReference)
```

Few other ways to create a Path

```java
Path path1 = Path.of("/home/user/file.txt");
Path path2 = FileSystems.getDefault().getPath("/home/user/stripes.txt");
Path path3 = Path.of(URI.create("file://www.selikoff.net"));
Path path3 = Path.of(URI.create("file:///home/user/test.txt"));
```