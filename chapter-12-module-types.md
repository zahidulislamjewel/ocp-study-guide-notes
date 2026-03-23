## Big picture

In Java modules, code can come from three places:

* **named modules**: real modules with `module-info.java`
* **automatic modules**: normal JARs placed on the **module path**
* **unnamed module**: everything placed on the **classpath**

The most important thing to remember is:

* **named modules cannot read the unnamed module**
* **automatic modules can read the unnamed module**
* **the unnamed module can read all named and automatic modules**

That is the core idea behind most of the rules in your note.

---

## 1. Named module vs classpath

A **named module** is a proper module with `module-info.java`.

Example:

```java
module app.main {
    requires lib.util;
}
```

This module can read only what it explicitly requires.

Now suppose you have:

* `app.main` on the module path
* `oldlib.jar` on the classpath

Even if `app.main` wants classes from `oldlib.jar`, it cannot access them, because `oldlib.jar` is part of the **unnamed module**, and a named module cannot depend on the unnamed module.

So this does **not** work:

```java
module app.main {
    // there is no way to write:
    // requires unnamed;
}
```

That is why people say:

> A named module cannot access random classes from the classpath.

---

## 2. Non-modular JAR on module path becomes automatic module

If a JAR has no `module-info.java`, but you place it on the **module path**, Java treats it as an **automatic module**.

Example:

You have:

```text
gson-2.10.jar
```

and place it on the module path.

Java may derive a module name like:

```text
gson
```

or use `Automatic-Module-Name` if present in the manifest.

Now your named module can do:

```java
module app.main {
    requires gson;
}
```

This works because `gson` is now an **automatic module**, and named modules *can* require automatic modules.

So:

* same JAR on **classpath** → part of unnamed module, not readable by named modules
* same JAR on **module path** → automatic module, readable by named modules

---

## 3. Why automatic modules are useful

Automatic modules are mainly a bridge during migration.

Suppose:

* `app.main` is already modularized
* `legacy-utils.jar` is old and non-modular

You do not want to rewrite `legacy-utils.jar` yet. So you put it on the **module path**. Java turns it into an automatic module, and now `app.main` can use it with `requires`.

That is why automatic modules are useful in gradual migration.

---

## 4. Why automatic modules can still see the classpath

This part is very important.

An **automatic module** is given extra powers:

* it reads all named modules
* it reads all other automatic modules
* it also reads the **unnamed module**

So if `legacy-utils.jar` becomes an automatic module, it can still access classes from JARs on the classpath.

Example:

* `legacy-utils.jar` on module path → automatic module
* `helper.jar` on classpath → unnamed module

Then code inside `legacy-utils.jar` can still use classes from `helper.jar`.

This is one reason the **top-down migration** approach works.

---

## 5. Unnamed module reads everything

The **unnamed module** is what the classpath becomes.

It has broad visibility:

* it can access all exported packages of named modules
* it can access automatic modules
* it can access other classpath code

So classpath code can usually call modular code, as long as the package is exported.

Example:

If `lib.util` exports `com.example.util`:

```java
module lib.util {
    exports com.example.util;
}
```

then a class on the classpath can use `com.example.util.SomeUtil`.

But the reverse is not true: named modules cannot read classes on the classpath.

---

## 6. Package exists in both named module and unnamed module

If the same package appears in both:

* a named module
* the unnamed module

then the classpath version is ignored.

Example:

Suppose both locations contain:

```text
com.example.util
```

If one copy is in named module `lib.util` and another copy is on classpath, Java ignores the classpath copy for that package.

This avoids ambiguity and preserves module-path precedence.

---

## 7. Bottom-up migration

Bottom-up means: modularize the lowest-level dependencies first.

Suppose:

* `A.jar` depends on `B.jar`
* `B.jar` depends on `C.jar`

Then order matters:

1. modularize `C.jar`
2. then modularize `B.jar`
3. then modularize `A.jar`

Because if `B` is turned into a named module, and it depends on `C`, then `C` must also be available in a modular way.

So bottom-up works best when your dependency chain is already ready to be modularized.

End result:

* everything ends up on the **module path**
* nothing remains on the classpath

That is the “fully modularized” state.

---

## 8. Top-down migration

Top-down means: modularize the top-level application first, without fully modularizing every dependency immediately.

Example:

* `A.jar` = your app
* `B.jar` = direct dependency
* `C.jar` = dependency used only by `B.jar`

You can do this:

* convert `A.jar` into a named module
* put `B.jar` on module path as an automatic module
* leave `C.jar` on classpath

Why can this work?

Because `A` can read `B` since `B` is automatic, and `B` can read `C` because automatic modules can read the unnamed module.

This is the practical migration trick.

So in top-down migration:

* named modules can depend on automatic modules
* automatic modules can still depend on classpath code

That makes migration easier.

---

## 9. Example of top-down dependency chain

Suppose:

* `app.main` is named
* `reports.jar` is non-modular
* `pdf.jar` is also non-modular, used by `reports.jar`

You can arrange things like this:

* `app.main` → module path
* `reports.jar` → module path, becomes automatic module
* `pdf.jar` → classpath

Then:

* `app.main` can `requires reports`
* `reports` can still use `pdf.jar` from classpath

But if later `reports.jar` becomes a proper named module, then `pdf.jar` can no longer stay only on classpath if `reports` needs it. You would need to modularize or at least make `pdf.jar` automatic too.

---

## 10. Automatic module naming

A non-modular JAR gets an automatic module name in one of two ways:

* from `Automatic-Module-Name` in the manifest
* otherwise derived from the JAR filename

Example:

```text
mysql-connector-java-8.0.11.jar
```

becomes something like:

```text
mysql.connector.java
```

Version parts are ignored, and hyphens usually become dots. 

That derived name is what you use in `requires`.

---

## Final mental model

Think of it like this:

* **named module** = strict and explicit
* **automatic module** = migration bridge
* **unnamed module** = old classpath world

And the key access rules are:

* named module → can read named and automatic, but **not unnamed**
* automatic module → can read named, automatic, and unnamed
* unnamed module → can read almost everything exported

So if a named module needs an old JAR, put that JAR on the **module path**, not the classpath. That turns it into an automatic module and makes it readable.

