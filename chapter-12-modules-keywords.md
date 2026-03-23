We can think of Java modules as a way to say:

* what this module needs
* what this module exposes
* what services it consumes or provides

The file that controls this is `module-info.java`.

---

# 1. `requires`

This means: “my module depends on another module.”

## Example

### `com.shop.app/module-info.java`

```java
module com.shop.app {
    requires com.shop.utils;
}
```

This says:

* `com.shop.app` needs `com.shop.utils`
* code inside `com.shop.app` can use public types from packages that `com.shop.utils` exports

## Simple analogy

Like adding a library dependency.

---

# 2. `exports`

This means: “other modules are allowed to use this package.”

## Example

### `com.shop.utils/module-info.java`

```java
module com.shop.utils {
    exports com.shop.utils;
}
```

### `com.shop.utils/com/shop/utils/TextUtils.java`

```java
package com.shop.utils;

public class TextUtils {
    public static String upper(String s) {
        return s.toUpperCase();
    }
}
```

### `com.shop.app/module-info.java`

```java
module com.shop.app {
    requires com.shop.utils;
}
```

### `com.shop.app/com/shop/app/Main.java`

```java
package com.shop.app;

import com.shop.utils.TextUtils;

public class Main {
    public static void main(String[] args) {
        System.out.println(TextUtils.upper("hello"));
    }
}
```

## Important point

If `com.shop.utils` does **not** export `com.shop.utils`, then `com.shop.app` cannot import `TextUtils`, even if the class is `public`.

So:

* `public` makes the class visible inside the package world
* `exports` makes the package visible to other modules

---

# 3. `exports ... to ...`

This means: “only specific modules may use this package.”

This is called **qualified export**.

## Example

### `com.bank.core/module-info.java`

```java
module com.bank.core {
    exports com.bank.api;
    exports com.bank.internal to com.bank.report;
}
```

This says:

* everyone can use `com.bank.api`
* only `com.bank.report` can use `com.bank.internal`

### Structure

* `com.bank.api` → public package for everyone
* `com.bank.internal` → restricted package

### `com.bank.report/module-info.java`

```java
module com.bank.report {
    requires com.bank.core;
}
```

Then `com.bank.report` can access `com.bank.internal`.

But another module like `com.bank.app` cannot.

## Easier meaning

Use this when:

* you want one package public only for a trusted module
* but not for the whole world

---

# 4. `uses`

This means: “my module wants some service implementation, but I do not know which one.”

This is part of the **service loader** pattern.

---

# 5. `provides ... with ...`

This means: “my module provides an implementation of a service interface.”

`uses` and `provides ... with ...` usually go together.

---

## Easy service example

Let’s say we have a payment system.

We want:

* one module defines the payment service
* another module provides PayPal implementation
* app module uses the service without depending directly on PayPal class

---

## Module 1: service API

### `com.payment.api/module-info.java`

```java
module com.payment.api {
    exports com.payment.api;
}
```

### `com.payment.api/com/payment/api/PaymentService.java`

```java
package com.payment.api;

public interface PaymentService {
    void pay(int amount);
}
```

---

## Module 2: provider module

### `com.payment.paypal/module-info.java`

```java
module com.payment.paypal {
    requires com.payment.api;
    provides com.payment.api.PaymentService
        with com.payment.paypal.PayPalPaymentService;
}
```

### `com.payment.paypal/com/payment/paypal/PayPalPaymentService.java`

```java
package com.payment.paypal;

import com.payment.api.PaymentService;

public class PayPalPaymentService implements PaymentService {
    @Override
    public void pay(int amount) {
        System.out.println("Paying " + amount + " using PayPal");
    }
}
```

---

## Module 3: consumer module

### `com.payment.app/module-info.java`

```java
module com.payment.app {
    requires com.payment.api;
    uses com.payment.api.PaymentService;
}
```

### `com.payment.app/com/payment/app/Main.java`

```java
package com.payment.app;

import com.payment.api.PaymentService;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<PaymentService> loader =
                ServiceLoader.load(PaymentService.class);

        for (PaymentService service : loader) {
            service.pay(500);
        }
    }
}
```

---

## What is happening?

* `com.payment.app` says `uses PaymentService`
* `com.payment.paypal` says `provides PaymentService with PayPalPaymentService`
* `ServiceLoader` finds the implementation automatically

## Why useful?

Your app depends on the interface, not the concrete class.

That gives loose coupling.

---

# 6. `requires transitive`

This means:

* my module depends on another module
* and anyone depending on me automatically reads that other module too

## Normal `requires`

```java
module com.a {
    requires com.b;
}
```

If `com.c` requires `com.a`, it does **not** automatically get access to `com.b`.

---

## `requires transitive`

```java
module com.a {
    requires transitive com.b;
}
```

Now if `com.c` requires `com.a`, then `com.c` also automatically reads `com.b`.

---

## Easy example

### `com.common/module-info.java`

```java
module com.common {
    exports com.common;
}
```

### `com.library/module-info.java`

```java
module com.library {
    requires transitive com.common;
    exports com.library;
}
```

### `com.app/module-info.java`

```java
module com.app {
    requires com.library;
}
```

Now `com.app` can use classes from both:

* `com.library`
* `com.common`

even though it only directly required `com.library`.

## When to use

Use `requires transitive` when your public API exposes types from another module.

For example:

```java
package com.library;

import com.common.Result;

public class LibraryService {
    public Result process() {
        return new Result();
    }
}
```

Since users of `com.library` need `com.common.Result`, `com.library` should probably declare:

```java
requires transitive com.common;
```

---

# 7. Other important module directives

Here are the other relevant ones you should know.

---

## `opens`

This means: allow runtime reflection on a package.

This is mainly for frameworks like:

* Spring
* Hibernate
* Jackson

## Example

```java
module com.myapp {
    opens com.myapp.model;
}
```

This allows reflective access to `com.myapp.model`.

### Difference from `exports`

* `exports` → compile-time and normal public access
* `opens` → runtime reflective access

A package can be opened without being exported.

---

## `opens ... to ...`

Like qualified export, but for reflection only.

## Example

```java
module com.myapp {
    opens com.myapp.entity to org.hibernate.orm.core;
}
```

This means only Hibernate gets reflective access.

---

## `open module`

This opens all packages in the module for reflection.

## Example

```java
open module com.myapp {
    requires spring.context;
}
```

Useful, but less strict.

---

## `requires static`

This means: dependency needed only at compile time, not necessarily at runtime.

## Example

```java
module com.myapp {
    requires static lombok;
}
```

Good for optional or compile-time-only dependencies.

---

# Small summary of the main ones

## Dependency related

```java
requires com.other;
requires transitive com.other;
requires static com.other;
```

## Visibility related

```java
exports com.my.pkg;
exports com.my.pkg to com.friend.module;
```

## Reflection related

```java
opens com.my.pkg;
opens com.my.pkg to framework.module;
open module com.myapp { ... }
```

## Service related

```java
uses com.example.Service;
provides com.example.Service with com.example.ServiceImpl;
```

---

# One compact example

Suppose you have 3 modules:

* `com.notification.api`
* `com.notification.email`
* `com.notification.app`

---

## `com.notification.api/module-info.java`

```java
module com.notification.api {
    exports com.notification.api;
}
```

```java
package com.notification.api;

public interface NotificationService {
    void send(String message);
}
```

---

## `com.notification.email/module-info.java`

```java
module com.notification.email {
    requires com.notification.api;
    provides com.notification.api.NotificationService
        with com.notification.email.EmailNotificationService;
}
```

```java
package com.notification.email;

import com.notification.api.NotificationService;

public class EmailNotificationService implements NotificationService {
    public void send(String message) {
        System.out.println("Email sent: " + message);
    }
}
```

---

## `com.notification.app/module-info.java`

```java
module com.notification.app {
    requires com.notification.api;
    uses com.notification.api.NotificationService;
}
```

```java
package com.notification.app;

import com.notification.api.NotificationService;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<NotificationService> loader =
                ServiceLoader.load(NotificationService.class);

        loader.findFirst().ifPresent(service -> service.send("Hello"));
    }
}
```

---

# Very easy mental model

## `requires`

I need this module.

## `exports`

Others may use this package.

## `exports ... to`

Only selected modules may use this package.

## `uses`

I want some implementation of this service.

## `provides ... with`

I provide that implementation.

## `requires transitive`

Anyone who needs me also gets access to this dependency.

## `opens`

Allow reflection.

---

# Common exam trap

A `public` class in a package is still not usable outside the module unless that package is exported.

So this fails conceptually:

```java
module com.example {
    // no exports
}
```

```java
package com.example.internal;

public class Helper { }
```

Another module cannot use `Helper`, because the package is not exported.

---

# Real-world advice

In practice:

* use `exports` for your public API packages
* avoid exporting internal packages
* use `opens` only for frameworks needing reflection
* use `uses` and `provides` for plugin-style architecture
* use `requires transitive` only when your API exposes types from that dependency

---

# A clean exam-oriented list

```java
module my.module {
    requires other.module;
    requires transitive another.module;
    requires static optional.module;

    exports com.example.api;
    exports com.example.internal to friend.module;

    opens com.example.model;
    opens com.example.entity to framework.module;

    uses com.example.spi.Service;
    provides com.example.spi.Service with com.example.impl.ServiceImpl;
}
```