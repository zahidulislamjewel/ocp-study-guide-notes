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