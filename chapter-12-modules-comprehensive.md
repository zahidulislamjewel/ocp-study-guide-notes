# Java 21 OCP – Chapter 12: Java Platform Module System (JPMS)
### Comprehensive Exam Notes (1Z0-830)

---

## Table of Contents

1. [Why Modules Exist](#1-why-modules-exist)
2. [Module Directives (All Keywords)](#2-module-directives-all-keywords)
3. [Three Module Types](#3-three-module-types)
4. [Compiling, Packaging, and Running Modules](#4-compiling-packaging-and-running-modules)
5. [Service Provider Interface (SPI) Pattern](#5-service-provider-interface-spi-pattern)
6. [Access Control – Three Layers](#6-access-control--three-layers)
7. [Annotated Zoo Code Examples (Book Code)](#7-annotated-zoo-code-examples-book-code)
8. [Quick Reference Cheat Sheet](#8-quick-reference-cheat-sheet)

---

## 1. Why Modules Exist

### The Problem: JAR Hell

Before modules, Java had two levels of organization:
- **Package** — organizes classes
- **`public`** — makes a class visible anywhere

If a class was `public`, it was broadly accessible — even internal implementation classes that were never intended for external use. All JARs on the classpath were visible to each other. This created **JAR Hell**:
- Wrong version of a class gets loaded silently
- `ClassNotFoundException` at runtime despite compiling fine
- No enforced encapsulation of internals

### The Solution: Java Platform Module System (JPMS)

Java 9 introduced JPMS. A **module** adds a third level of organization:
- Fields and methods are grouped into classes
- Classes are grouped into packages
- Packages are grouped into modules

A module is a **named, self-describing collection of packages** with a special file called `module-info.java`.

### What a Module Controls

- Which packages are **visible** outside the module — `exports`
- Which other modules it **depends on** — `requires`
- Whether **reflection** is allowed on its packages — `opens`
- Whether it **consumes** or **provides** services — `uses` / `provides`

### Benefits

- **Better access control** — `public` alone is no longer enough; the package must also be exported
- **Clear dependency management** — all dependencies declared explicitly
- **Custom Java builds** — `jlink` can create minimal runtime images
- **Improved security** — internal JDK packages are not exposed
- **Improved performance** — smaller footprint, faster startup
- **Unique package enforcement** — same package in two modules causes a compile error

### `java.base` — The Special Module

`java.base` contains fundamental packages (`java.lang`, `java.util`, `java.io`, etc.) and is **automatically required by every module**. You never need to write `requires java.base;`.

---

## 2. Module Directives (All Keywords)

The `module-info.java` file must be placed at the **root directory** of the module. It contains the **module declaration**.

### Complete Template (All Directives)

```java
module my.module {
    // --- Dependencies ---
    requires other.module;                         // normal dependency
    requires transitive another.module;            // re-export dependency
    requires static optional.module;              // compile-time only

    // --- Visibility ---
    exports com.example.api;                       // open to all modules
    exports com.example.internal to friend.module; // open to specific module only

    // --- Reflection ---
    opens com.example.model;                       // all modules can reflect
    opens com.example.entity to framework.module;  // only framework can reflect

    // --- Services ---
    uses com.example.spi.Service;                  // I consume this service
    provides com.example.spi.Service
        with com.example.impl.ServiceImpl;         // I provide this implementation
}
```

---

### `requires`

**Meaning:** My module depends on another module.

```java
module com.shop.app {
    requires com.shop.utils;
}
```

- Code inside `com.shop.app` can use public types from packages that `com.shop.utils` exports
- This is the modular equivalent of adding a library dependency

> **Exam Tip:** `requires` only works with **named** and **automatic** modules. You cannot `requires` an unnamed module (classpath code).

---

### `requires transitive`

**Meaning:** My module depends on this, AND any module that requires me also automatically gets this dependency.

```java
module com.library {
    requires transitive com.common;   // my users also get com.common
    exports com.library;
}

module com.app {
    requires com.library;
    // com.app automatically reads com.common too — no need to declare it
}
```

**Without `requires transitive` on `care`:**
- `talks` must explicitly require both `care` and `feeding`
- `staff` must explicitly require `feeding`, `care`, and `talks`

**With `requires transitive zoo.animal.feeding` in `care`:**
- Any module that requires `care` automatically gets `feeding` too
- `talks` and `staff` no longer need to declare `requires zoo.animal.feeding` separately

**When to use:** Only when your module's **public API exposes types** from that dependency. For example, if a method's return type is `Result` from `com.common`, then callers of your library need `com.common` too.

```java
// If LibraryService.process() returns Result from com.common,
// then com.library needs: requires transitive com.common;
public class LibraryService {
    public Result process() { return new Result(); }
}
```

> **Exam Tip:** Do NOT use `requires transitive` indiscriminately — only when the dependency leaks into your public API.

---

### `requires static`

**Meaning:** This dependency is needed at **compile time only**, not necessarily at runtime.

```java
module com.myapp {
    requires static lombok;          // annotation processor
    requires static com.annotations; // compile-time annotations
}
```

Used for:
- Annotation processors (Lombok, etc.)
- Optional dependencies where code gracefully handles absence at runtime

---

### `exports`

**Meaning:** Other modules are allowed to use this package.

```java
module com.shop.utils {
    exports com.shop.utils;   // this package is visible to all modules
}
```

> **CRITICAL EXAM TRAP:** A class being `public` is NOT enough. The **package** must also be exported. Without `exports`, even `public` classes are inaccessible to other modules.

```java
// This public class is STILL inaccessible outside the module
// if the package is not exported:
module com.example {
    // no exports statement
}
package com.example.internal;
public class Helper { }  // unreachable by other modules!
```

**Memory trick:**
- `public` = visible within the package world
- `exports` = unlocks the room for outsiders

---

### `exports ... to ...` (Qualified Export)

**Meaning:** Only specific trusted modules may use this package.

```java
module com.bank.core {
    exports com.bank.api;                          // open to everyone
    exports com.bank.internal to com.bank.report;  // only com.bank.report
}
```

Use when you want one package exposed to a trusted module, but not to the world. Common for internal packages shared between sibling modules in the same codebase.

---

### `opens`

**Meaning:** Allow **runtime reflective access** to a package, but NOT normal compile-time access.

```java
module com.myapp {
    opens com.myapp.model;   // reflection allowed on this package
}
```

Used for frameworks that need reflection:
- Spring (field injection, proxies)
- Hibernate (lazy loading, entity inspection)
- Jackson (JSON serialization/deserialization)

**Key difference from `exports`:**
- `exports` — compile-time and runtime normal access
- `opens` — runtime reflective access only, no compile-time access
- A package can be `opens`-ed without being `exports`-ed

> **Memory trick:** `exports` = "people may call my code" | `opens` = "frameworks may inspect my code"

A package can be opened without being exported. This is common for entity/model classes used only by a framework.

---

### `opens ... to ...` (Qualified Opens)

**Meaning:** Only a specific module gets reflective access.

```java
module com.myapp {
    opens com.myapp.entity to org.hibernate.orm.core;       // only Hibernate reflects
    opens com.myapp.dto to com.fasterxml.jackson.databind;  // only Jackson
}
```

---

### `open module` (Open Module Declaration)

**Meaning:** ALL packages in the module are open for reflection to everyone.

```java
open module com.myapp {
    requires spring.context;
    exports com.myapp.api;   // still need to export for normal access
}
```

This is less strict — useful for Spring Boot applications where many beans need reflection. Note: `open module` makes packages reflectively accessible, but packages still need `exports` for normal compile-time access.

---

### `uses`

**Meaning:** My module wants an implementation of a service interface, but I don't know (or care) which one.

```java
module com.payment.app {
    requires com.payment.api;
    uses com.payment.api.PaymentService;   // I want some PaymentService
}
```

This is part of the **ServiceLoader** pattern. Without `uses`, the module system doesn't know that this module wants to consume the service.

---

### `provides ... with ...`

**Meaning:** My module supplies an implementation of a service interface.

```java
module com.payment.paypal {
    requires com.payment.api;
    provides com.payment.api.PaymentService
        with com.payment.paypal.PayPalPaymentService;
}
```

> **Exam Tip:** The **provider's implementation class does NOT need to be in an exported package**. The implementation can remain hidden — ServiceLoader still discovers it through the `provides` declaration.

---

## 3. Three Module Types

### Named Module

A proper, fully modularized JAR with `module-info.java`.

```java
module app.main {
    requires lib.util;
    exports com.example.api;
}
```

- Has `module-info.java`, placed on the **module path**
- Exports only what is explicitly declared
- Can only read named and automatic modules
- **CANNOT read the unnamed module (classpath)**

### Automatic Module

A non-modular JAR placed on the **module path** (not classpath). Java automatically treats it as a module.

- No `module-info.java`, placed on the **module path**
- Exports **all** its packages automatically (as if everything was exported)
- Can be required by name
- Can read named modules, other automatic modules, AND the unnamed module (classpath)
- Main purpose: a **bridge during migration** so named modules can depend on legacy JARs

**Naming rules for automatic modules:**
1. Check for `Automatic-Module-Name` in `META-INF/MANIFEST.MF` — use that if present
2. Otherwise, derive from the JAR filename:
   - Remove `.jar` extension
   - Remove version numbers (e.g., `-2.10.1`, `-8.0.11`)
   - Replace hyphens (`-`) with dots (`.`)

Examples:
- `gson-2.10.jar` becomes `gson`
- `mysql-connector-java-8.0.11.jar` becomes `mysql.connector.java`
- `spring-core-5.3.jar` becomes `spring.core`

Then a named module can do:
```java
module app.main {
    requires gson;   // gson-2.10.jar is on module path as automatic module
}
```

### Unnamed Module

Everything on the **classpath** becomes part of the unnamed module.

- No `module-info.java`; if one exists in the JAR, it is completely **ignored**
- There is only ONE unnamed module — all classpath content is in it
- Can read all exported packages of named and automatic modules
- **Named modules CANNOT read the unnamed module** — there's no name to `requires`!
- Classpath code sees the module path, but not vice versa

```java
// You cannot write this — there's no way to require the unnamed module:
module app.main {
    requires unnamed;  // ILLEGAL - doesn't exist as a name
}
```

### The Critical Access Rules

- **Named module** can read: named modules + automatic modules (NOT unnamed)
- **Automatic module** can read: named modules + automatic modules + unnamed module (classpath)
- **Unnamed module** can read: exported packages of named + automatic modules + classpath

> **Exam Tip:** The most tested rule: **Named modules cannot read the unnamed module.** If a named module needs a legacy JAR, put that JAR on the **module path** (making it automatic), NOT the classpath.

### Same Package in Two Places

If the same package appears in both a named module and the unnamed module (classpath), the **classpath version is silently ignored** — the module path takes precedence.

### Migration Strategies

#### Bottom-Up Migration

Modularize the **lowest-level dependencies first**, working up the dependency tree.

Suppose the dependency chain is: `A` depends on `B`, `B` depends on `C`.
1. Modularize `C` first — add `module-info.java`, make it a named module
2. Then modularize `B` — now it can `requires C` properly
3. Finally modularize `A`

- **End result:** Everything ends up on the module path, nothing on classpath
- **Best for:** When you own all the code and can modularize it systematically

#### Top-Down Migration

Modularize the **top-level application first**, leave dependencies as automatic modules temporarily.

Suppose:
- `app.main` is your app — make it a named module
- `reports.jar` is a direct dependency — put it on the module path (becomes automatic)
- `pdf.jar` is used only by `reports.jar` — leave it on the classpath

This works because:
- Named `app.main` can `requires reports` (automatic module)
- Automatic `reports` can still read `pdf.jar` from the classpath

- **End result:** Mix of named, automatic, and unnamed during transition
- **Best for:** Practical migration when you don't control all dependencies

---

## 4. Compiling, Packaging, and Running Modules

### Compiling with `javac`

```bash
# Full flags
javac --module-path mods -d feeding feeding/zoo/animal/feeding/*.java feeding/module-info.java

# Shorthand (-p = --module-path)
javac -p mods -d feeding feeding/zoo/animal/feeding/*.java feeding/*.java
```

Key flags:
- `--module-path` / `-p` — where to find **required** named/automatic modules (not where your source is)
- `-d` — **destination directory** for compiled `.class` files
- `--class-path` / `-cp` / `-classpath` — classpath for non-modular / pre-module style

> **Key point:** `--module-path` is for modules you **depend on**. It is the modular equivalent of `--class-path`. The source files including `module-info.java` must be listed explicitly at the end of the command.

### Packaging with `jar`

```bash
jar -cvf mods/zoo.animal.feeding.jar -C feeding/ .
```

Flags:
- `-c` — create a new JAR
- `-v` — verbose output
- `-f` — next argument is the output JAR filename
- `-C feeding/ .` — change to the `feeding/` directory, then package everything (`.`) inside it

A **modular JAR** is just a regular JAR that contains `module-info.class`. There is nothing module-specific in the `jar` command itself.

### Running with `java`

```bash
# Full flags
java --module-path mods --module zoo.animal.feeding/zoo.animal.feeding.Task

# Shorthand (-p = --module-path, -m = --module)
java -p mods -m zoo.animal.feeding/zoo.animal.feeding.Task
```

Key flags:
- `--module-path` / `-p` — where to find modules
- `--module` / `-m` — specifies `<module-name>/<main-class>` to run

The main class must have `public static void main(String[] args)`.

### Module Discovery Commands

Describing a module (shows its directives):
```bash
java -p mods -d zoo.animal.feeding
java -p mods --describe-module zoo.animal.feeding
jar -f mods/zoo.animal.feeding.jar -d
jar --file mods/zoo.animal.feeding.jar --describe-module
```

Listing all available modules:
```bash
java --list-modules              # JDK modules only
java -p mods --list-modules      # JDK + your modules
```

Showing module resolution at startup:
```bash
java --show-module-resolution -p feeding -m zoo.animal.feeding/zoo.animal.feeding.Task
```

### Analyzing Dependencies with `jdeps`

`jdeps` analyzes a JAR's dependencies and shows which packages/modules it uses. Used to understand a legacy JAR before modularizing it.

```bash
# Compile and package a non-modular JAR
javac zoo/dinos/*.java
jar -cvf zoo.dino.jar .

# Full dependency analysis
jdeps zoo.dino.jar

# Summary only
jdeps -s zoo.dino.jar
jdeps -summary zoo.dino.jar

# List internal JDK APIs used (flagged as illegal/forbidden in future releases)
jdeps --jdk-internals zoo.dino.jar
```

If the output shows `sun.misc.Unsafe` or other internal APIs, that code uses JDK internals that may break in future Java versions.

### Creating Runtime Images with `jlink`

`jlink` creates a **custom minimal JRE** containing only the modules your app needs.

```bash
jlink --module-path mods --add-modules zoo.animal.talks --output zooApp
```

Key flags:
- `--module-path` — where to find modules
- `--add-modules` — module(s) to include (dependencies resolved transitively)
- `--output` — output directory for the runtime image

The resulting `zooApp/` directory contains a complete JRE runnable without a full JDK install.

### Packaging as an Installable App with `jpackage`

```bash
jpackage --name feedingTask --module-path mods --module zoo.animal.feeding/zoo.animal.feeding.Task
```

Creates a platform-native installer (`.dmg`, `.exe`, `.deb`, etc.).

> **Note:** `jmod` exists but is **not tested on this exam**.

---

## 5. Service Provider Interface (SPI) Pattern

### The Core Idea

Instead of a consumer directly depending on a concrete implementation, all parties agree on an **interface** (the service contract). Implementations are discovered at runtime through `ServiceLoader`. This achieves **loose coupling** — swap implementations with zero changes to the consumer.

### The Four Roles

There are four roles in a fully modular service setup:
1. **Service Interface** — defines the contract (the interface)
2. **Service Provider** — provides a concrete implementation
3. **Service Locator** — houses the `ServiceLoader` code; discovers and returns providers
4. **Consumer** — uses the service through the locator, knows nothing about the provider

### Role 1: Service Interface Module (API)

Defines the **service contract** — the interface everyone agrees on.

```java
// zoo.tours.api/module-info.java
module zoo.tours.api {
    exports zoo.tours.api;    // export the interface package
}
```

```java
// zoo/tours/api/Tour.java
package zoo.tours.api;
public interface Tour {
    String name();
    int length();
    Souvenir getSouvenir();
}

public record Souvenir(String description) { }
```

### Role 2: Service Provider Module

Provides a **concrete implementation** of the service interface.

```java
// zoo.tours.agency/module-info.java
module zoo.tours.agency {
    requires zoo.tours.api;
    provides zoo.tours.api.Tour with zoo.tours.agency.TourImpl;
    // NOTE: zoo.tours.agency package is NOT exported — provider stays hidden
}
```

```java
// zoo/tours/agency/TourImpl.java
package zoo.tours.agency;
import zoo.tours.api.*;
public class TourImpl implements Tour {
    public String name() { return "Behind the Scenes"; }
    public int length() { return 120; }
    public Souvenir getSouvenir() { return new Souvenir("stuffed animal"); }
}
```

> **Exam Tip:** The provider module does NOT export the implementation package. The implementation is discovered by `ServiceLoader` through `module-info.java`, not through direct access.

### Role 3: Service Locator Module

Contains the `ServiceLoader` code that **discovers and returns implementations**.

```java
// zoo.tours.reservations/module-info.java
module zoo.tours.reservations {
    requires zoo.tours.api;
    uses zoo.tours.api.Tour;          // declares intent to consume the service
    exports zoo.tours.reservations;   // export so consumer can call our finder
}
```

```java
// zoo/tours/reservations/TourFinder.java
package zoo.tours.reservations;
import java.util.*;
import zoo.tours.api.*;

public class TourFinder {
    public static Tour findSingleTour() {
        ServiceLoader<Tour> loader = ServiceLoader.load(Tour.class);
        for (Tour tour : loader) {
            return tour;   // returns first found
        }
        return null;
    }

    public static List<Tour> findAllTours() {
        List<Tour> tours = new ArrayList<>();
        ServiceLoader<Tour> loader = ServiceLoader.load(Tour.class);
        for (Tour tour : loader) {
            tours.add(tour);
        }
        return tours;
    }
}
```

### Role 4: Consumer Module

Uses the service through the locator, knowing nothing about the provider.

```java
// zoo.visitor/module-info.java
module zoo.visitor {
    requires zoo.tours.api;          // needs the interface
    requires zoo.tours.reservations; // needs the locator
    // Does NOT require zoo.tours.agency — consumer is decoupled from provider!
}
```

```java
// zoo/visitor/Tourist.java
package zoo.visitor;
import java.util.*;
import zoo.tours.api.*;
import zoo.tours.reservations.*;

public class Tourist {
    public static void main(String[] args) {
        Tour tour = TourFinder.findSingleTour();
        System.out.println("Single tour: " + tour);

        List<Tour> tours = TourFinder.findAllTours();
        System.out.println("# tours: " + tours.size());
    }
}
```

### Module Directives by Role

**Service Interface module:**
- `exports <api-package>`

**Service Provider module:**
- `requires <api-module>`
- `provides <Interface> with <Impl>`

**Service Locator module:**
- `requires <api-module>`
- `uses <Interface>`
- `exports <locator-package>`

**Consumer module:**
- `requires <api-module>`
- `requires <locator-module>`

### Running the Service Example

```bash
# 1. Compile and package the API module (no dependencies)
javac -d serviceProviderInterfaceModule serviceProviderInterfaceModule/zoo/tours/api/*.java serviceProviderInterfaceModule/module-info.java
jar -cvf mods/zoo.tours.api.jar -C serviceProviderInterfaceModule/ .

# 2. Compile and package the locator module
javac -p mods -d serviceLocatorModule serviceLocatorModule/zoo/tours/reservations/*.java serviceLocatorModule/module-info.java
jar -cvf mods/zoo.tours.reservations.jar -C serviceLocatorModule/ .

# 3. Compile and package the consumer module
javac -p mods -d consumerModule consumerModule/zoo/visitor/*.java consumerModule/module-info.java
jar -cvf mods/zoo.visitor.jar -C consumerModule/ .

# Run WITHOUT provider — findSingleTour() returns null, list size = 0
java -p mods -m zoo.visitor/zoo.visitor.Tourist

# 4. Compile and add the provider module
javac -p mods -d serviceProviderModule serviceProviderModule/zoo/tours/agency/*.java serviceProviderModule/module-info.java
jar -cvf mods/zoo.tours.agency.jar -C serviceProviderModule/ .

# Run AGAIN with provider — ServiceLoader now finds TourImpl
java -p mods -m zoo.visitor/zoo.visitor.Tourist
```

### Multiple Providers

If you add a second provider module that also declares `provides zoo.tours.api.Tour with ...`, `ServiceLoader` will load **both** implementations. The `for` loop iterates over all of them.

### ServiceLoader Key Methods

```java
ServiceLoader<Tour> loader = ServiceLoader.load(Tour.class);

// Iterate all implementations
for (Tour t : loader) { ... }

// Get first one
loader.findFirst().ifPresent(t -> t.name());

// Stream
loader.stream()
      .map(ServiceLoader.Provider::get)
      .forEach(t -> System.out.println(t.name()));
```

---

## 6. Access Control – Three Layers

Java modules add a third layer on top of traditional access modifiers.

### Layer 1: Class-Level (Traditional Access Modifiers)

- `private` — visible within the class only
- package-private (no modifier) — visible within the same package
- `protected` — visible within the package and subclasses
- `public` — visible everywhere **within the module**

### Layer 2: Package-Level (Module Exports)

Even a `public` class needs its **package to be exported** to be visible outside the module.

- `public` + exported package = accessible to other modules
- `public` + unexported package = inaccessible outside module

Use `exports` for normal API access (compile-time + runtime).
Use `exports ... to ...` for restricted/trusted-only access.

### Layer 3: Reflection-Level (Module Opens)

For frameworks that use reflection (Spring, Hibernate, Jackson), normal `exports` is not enough — they need `opens`.

`opens` = reflective access only (no compile-time or normal runtime access)

Use `opens` when a framework needs to:
- Inject fields (Spring DI)
- Inspect entity fields (Hibernate)
- Read/write fields for serialization (Jackson)

### exports vs opens Summary

- `exports com.example.api` — normal access for everyone
- `exports com.example.api to a.module` — normal access for `a.module` only
- `opens com.example.model` — reflection for everyone
- `opens com.example.model to hibernate` — reflection for `hibernate` only
- `open module com.myapp { }` — reflection on ALL packages for everyone

A package can be:
- Only exported — normal access, no reflection
- Only opened — reflection only, no normal access
- Both exported and opened
- Neither — completely hidden (the default)

---

## 7. Annotated Zoo Code Examples (Book Code)

### Module: `zoo.animal.feeding` — Simple Export

```java
// feeding/module-info.java
module zoo.animal.feeding {
    exports zoo.animal.feeding;   // allow other modules to use this package
}
```

```java
// feeding/zoo/animal/feeding/Task.java
package zoo.animal.feeding;
public class Task {
    public static void main(String... args) {
        System.out.println("All fed!");
    }
}
```

This is the simplest module: exports one package, has a main class. Used as the base dependency for other zoo modules.

---

### Module: `zoo.animal.care` — Requires + Partial Export

```java
// care/module-info.java
module zoo.animal.care {
    exports zoo.animal.care.medical;   // only medical package is public
    // zoo.animal.care.details is NOT exported — stays internal

    requires zoo.animal.feeding;
    // requires transitive zoo.animal.feeding;  ← commented out in book example
    // (uncomment this when care's public API exposes feeding types)
}
```

Key point: `zoo.animal.care.details` package (containing `HippoBirthday`) is **not exported** — it can use `zoo.animal.feeding` internally but is not visible to outsiders.

---

### Module: `zoo.animal.talks` — Multiple Exports + Qualified Export

```java
// talks/module-info.java
module zoo.animal.talks {
    exports zoo.animal.talks.content;   // or: exports zoo.animal.talks.content to zoo.staff;
    exports zoo.animal.talks.media;
    exports zoo.animal.talks.schedule;

    requires zoo.animal.feeding;
    requires zoo.animal.care;
    // requires transitive zoo.animal.care;  ← book shows both forms
}
```

The book demonstrates changing `exports zoo.animal.talks.content` to `exports zoo.animal.talks.content to zoo.staff` — making that package available only to the staff module.

---

### Module: `zoo.staff` — Consumer Only (No Exports)

```java
// staff/module-info.java
module zoo.staff {
    requires zoo.animal.feeding;
    requires zoo.animal.care;
    requires zoo.animal.talks;
    // No exports — this is a leaf module (top-level app), nothing to expose
}
```

This is a typical application module: it `requires` everything it needs but exports nothing because nothing depends on it.

---

### Effect of `requires transitive` Demo

**Before transitive (all dependencies explicit):**
```java
module zoo.staff {
    requires zoo.animal.feeding;   // needed explicitly because care doesn't expose it
    requires zoo.animal.care;
    requires zoo.animal.talks;
}
```

**After adding `requires transitive zoo.animal.feeding` in `zoo.animal.care`:**
```java
module zoo.staff {
    // requires zoo.animal.feeding;  ← can now remove this line!
    requires zoo.animal.care;        // care transitively provides feeding
    requires zoo.animal.talks;
}
```

Once `care` declares `requires transitive zoo.animal.feeding`, all modules requiring `care` automatically get access to `feeding` without declaring it themselves.

---

### Full Service Example: `zoo.tours.*`

```java
// serviceProviderInterfaceModule/module-info.java
module zoo.tours.api {
    exports zoo.tours.api;   // Tour interface and Souvenir record
}

// serviceProviderModule/module-info.java
module zoo.tours.agency {
    requires zoo.tours.api;
    provides zoo.tours.api.Tour with zoo.tours.agency.TourImpl;
    // zoo.tours.agency package NOT exported — TourImpl stays hidden
}

// serviceLocatorModule/module-info.java
module zoo.tours.reservations {
    exports zoo.tours.reservations;   // TourFinder is accessible
    requires zoo.tours.api;
    uses zoo.tours.api.Tour;          // declares intent to consume Tour
}

// consumerModule/module-info.java
module zoo.visitor {
    requires zoo.tours.api;           // needs Tour interface
    requires zoo.tours.reservations;  // needs TourFinder
    // Does NOT require zoo.tours.agency — that's the point!
}
```

---

## 8. Quick Reference Cheat Sheet

### All Directives

- `requires mod` — declare dependency on a module
- `requires transitive mod` — re-export dependency; all modules requiring me also get this
- `requires static mod` — compile-time only dependency; optional at runtime
- `exports pkg` — open package for normal access to all modules; `public` alone is NOT enough
- `exports pkg to mod` — open package for normal access to a specific module only (qualified export)
- `opens pkg` — allow reflective access to package for all modules; different from `exports`!
- `opens pkg to mod` — allow reflective access to package for a specific module only
- `open module m { }` — open ALL packages in the module for reflection; still need `exports` for normal access
- `uses Interface` — declare intent to consume a service; required for `ServiceLoader` to work
- `provides Interface with Impl` — register service implementation; `Impl` does NOT need to be exported

---

### Three Module Types

**Named module:**
- Has `module-info.java`, placed on the module path
- Exports only declared packages
- Can read named and automatic modules
- Cannot read the unnamed module

**Automatic module:**
- No `module-info.java`, placed on the module path
- Exports all packages automatically
- Can read named, automatic, and unnamed modules
- Module name derived from JAR filename or `Automatic-Module-Name` in MANIFEST
- Acts as a migration bridge — named modules can depend on it

**Unnamed module:**
- No `module-info.java` (ignored even if present), placed on the classpath
- Exports nothing to named/automatic modules
- Can read exported packages of named and automatic modules
- Cannot be required by name — named modules cannot read it

---

### CLI Commands

**Compile:**
```bash
javac -p mods -d out src/module-info.java src/**/*.java
```
- `-p` / `--module-path` — where required modules are
- `-d` — output directory for `.class` files

**Package:**
```bash
jar -cvf mods/my.module.jar -C out/ .
```
- `-c` create, `-v` verbose, `-f` filename, `-C dir` change to dir

**Run:**
```bash
java -p mods -m com.example/com.example.Main
```
- `-p` / `--module-path` — where modules are
- `-m` / `--module` — `module/class` to run

**Describe a module:**
```bash
java -p mods -d zoo.animal.feeding
jar -f mods/zoo.animal.feeding.jar -d
```

**List modules:**
```bash
java --list-modules               # JDK only
java -p mods --list-modules       # JDK + yours
```

**Show module resolution:**
```bash
java --show-module-resolution -p mods -m module/Class
```

**Analyze dependencies:**
```bash
jdeps zoo.dino.jar
jdeps -s zoo.dino.jar
jdeps --jdk-internals zoo.dino.jar
```

**Create runtime image:**
```bash
jlink --module-path mods --add-modules zoo.animal.talks --output zooApp
```

**Create native installer:**
```bash
jpackage --name myApp --module-path mods --module zoo.animal.feeding/zoo.animal.feeding.Task
```

---

### Exam Traps Checklist

- `public` does NOT mean accessible outside the module — the package must also be `exports`-ed
- Named module CANNOT read the unnamed module — put legacy JARs on the module path, not classpath
- `exports` and `opens` are different — `exports` = normal access; `opens` = reflection only
- Provider implementation package does NOT need to be exported — `provides ... with ...` works without `exports`
- `java.base` is always implicitly required — never write `requires java.base;`
- `requires transitive` is NOT for all dependencies — only when your public API exposes those types
- Same JAR behaves differently on classpath vs. module path — classpath = unnamed (invisible to named); module path = automatic (visible)
- `requires static` dependency may not be present at runtime — code must handle its absence
- Automatic module name derived from JAR filename — hyphens become dots, version number stripped
- `open module` opens all packages for reflection only — still need `exports` for normal compile-time access

---

### "What Happens If…" Scenarios

- Package not exported but class is `public` — compile error in other module; inaccessible
- JAR placed on classpath instead of module path — becomes unnamed module; named modules cannot require it
- JAR placed on module path without `module-info.java` — becomes automatic module; all packages exported automatically
- `requires` without corresponding `exports` from that module — compile error; package not accessible
- `uses` declared but no `provides` on module path — no error; `ServiceLoader` returns empty iterator
- `provides` declared but consumer has no `uses` — provider ignored for that consumer; no error
- Same package in named module and on classpath — classpath version silently ignored; module path wins
- `requires transitive` on module A declared in module B — all modules requiring B automatically read A
- `open module` without `exports` — reflection works, but normal compile-time access still fails
- `module-info.java` in a JAR on the classpath — completely ignored; treated as unnamed module

---

### Decision Framework for Exam Questions

When reading a module question, ask these in order:

1. Is this package visible outside the module? — look for `exports`
2. Does this module depend on another? — look for `requires`
3. Is that dependency passed downstream automatically? — look for `requires transitive`
4. Is reflection involved? — look for `opens`
5. Is Java loading implementations indirectly? — look for `uses` + `provides ... with ...`
6. Where is the JAR placed? — module path = named or automatic; classpath = unnamed
7. Can module X read module Y? — named cannot read unnamed; automatic and unnamed can read most things

---

### One-Sentence Summary

A Java module is a **named group of packages** with a `module-info.java` file that explicitly declares what it exposes (`exports`), what it depends on (`requires`), whether reflection is allowed (`opens`), and whether it consumes or provides services (`uses`/`provides`) — adding a third wall of access control above `public`.
