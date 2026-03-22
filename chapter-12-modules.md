# Chapter 12: Modules

## OCP EXAM OBJECTIVES COVERED IN THIS CHAPTER:

### Packaging and Deploying Java Code

- Define modules and expose module content, including that by reflection, and declare module dependencies, define services, providers, and consumers.
- Compile Java code, create modular and non-modular jars, runtime images, and implement migration to modules using unnamed and automatic modules.

**Introducing Modules**

Packages can be grouped into modules. 

A real project will consist of hundreds or thousands of classes grouped into packages. These packages are grouped into Java archive (JAR) files. A JAR is a `.zip` file with some extra information, and the extension is `.jar`.

Open source is software with the code supplied and is often free to use. Java has a vibrant open-source software (OSS) community, and those libraries are also supplied as JAR files. 

This complex chain of dependencies and minimum versions is often referred to by the community as JAR hell. Hell is an excellent way of describing the wrong version of a class being loaded or even a ClassNotFoundException at runtime.


The Java Platform Module System (JPMS) groups code at a higher level. 

The main purpose of a module is to provide groups of related packages that offer developers a particular set of functionality.

It’s like a JAR file, except a developer chooses which packages are accessible outside the module.

The Java Platform Module System includes the following:

- A format for module JAR files
- Partitioning of the JDK into modules
- Additional command-line options for Java tools

**Exploring a Module**

A module is a group of one or more packages plus a special file called `module-info.java`. This file is required to be inside all modules.

The contents of `module-info.java` file are the module declaration.

**Benefits of Modules**

- Better access control
- Clearer dependency management
- Custom Java builds
- Improved security
- Improved performance
- Unique package enforcement

**Creating and Running a Modular Program**

Module declaration rules in comparison with java class declaration:

- The module-info.java file must be in the root directory of your module. Regular Java classes should be in packages.

Here is your note refined in the same style, with repetition removed and arranged more cleanly.

**Compiling First Module**

```bash
javac --module-path mods -d {folderName} path/to/sourcefiles/Main.java path/to/modulefile/module-info.java
```

`--module-path` specifies where Java should look for required named modules.

It is not where the current source files are. It is where Java looks for compiled modules or modular JARs that the current module depends on.

We can think of `--module-path` as serving a similar purpose to `classpath` in a modular program.

`--module-path mods` means:

> Look inside the `mods` directory for required modules.

`-d` specifies the destination directory for compiled `.class` files.

`-d {folderName}` tells `javac` where to place the compiled output.

For modular compilation, this is usually the parent output folder, not the module folder itself.

The files at the end of the command are the `.java` source files to compile, including `module-info.java`.

`--module-path` and `-p` are equivalent.

**Why `--module-path` matters**

In non-modular Java, dependencies are usually resolved with `classpath`.

In modular Java, dependencies are usually resolved with `--module-path`.

So this is the modular equivalent of saying:

> Here is where the modules I depend on are stored.

Subtle point:

* `classpath` works with classes and JARs in the traditional non-modular way
* `module-path` works with named modules

So, it is not just a renaming. It is part of the module system.

**What about `classpath`?**

The `classpath` option still exists and has three forms:

* `-cp`
* `--class-path`
* `-classpath`

These are commonly used for non-modular programs.

**Running First Module**

```bash
java --module-path <module-location> --module <module-name>/<main-class>
```

This runs the specified main class from the specified module.

`--module-path` tells Java where to find compiled modules or modular JARs.

`--module` tells Java which module to run and which main class inside that module to launch.

Syntax:

```bash
<module-name>/<main-class>
```

`--module` and `-m` are equivalent.
`--module-path` and `-p` are equivalent.

Example:

```bash
java --module-path mods --module book.module/com.sybex.OCP
```

This means:

> Run `com.sybex.OCP` from module `book.module`.

The main class must contain:

```java
public static void main(String[] args)
```

**Packaging First Module**

A module is usually packaged into a JAR so it can be used from outside the folder where it was compiled.

Before packaging, create the `mods` directory.

```bash
jar -cvf mods/zoo.animal.feeding.jar -C feeding .
```

`jar` is used to package compiled files into a JAR.

`-c` means create a new JAR file.

`-v` means verbose output.

`-f` means the next argument is the JAR file name.

`mods/zoo.animal.feeding.jar` is the name and location of the generated JAR file.

`-C feeding .` means:

> Change to the `feeding` directory, then package everything inside it.

There is nothing module-specific in the `jar` command itself. It simply packages the compiled contents of the module into a JAR file.

This JAR represents how the module is exposed to other code that wants to use it.

**Running the packaged module**

```bash
java -p mods -m zoo.animal.feeding/zoo.animal.feeding.Task
```

This runs the main class `zoo.animal.feeding.Task` from the module `zoo.animal.feeding`.

Here, `mods` is now the module path containing the packaged module JAR.

This command looks almost the same as running loose compiled classes. The difference is what the module path points to:

* earlier, it pointed to the directory containing compiled module classes
* now, it points to the directory containing the packaged module JAR

Since the module path is used, Java can run the module directly from the modular JAR.

**Note**

A modular JAR is just a regular JAR that contains `module-info.class`.
