The easiest way to understand uses, provides, and requires is to think in terms of roles.

There are usually three parts in a service-based module setup:

- one module defines the service contract (the interface)
- another module provides an implementation of that interface
- another module uses the service without knowing which implementation it will get

This is commonly referred to as the ServiceLoader pattern or service provider mechanism.

More precisely:

- uses and provides are part of Java’s service provider mechanism
- ServiceLoader is the class Java uses to discover and load those implementations

So the module declarations are the module-system way of setting up the same service loading mechanism.

---

`ServiceLoader.load(...)` is the runtime piece that actually finds the implementations declared through `provides`.

Here is the full picture.

## 1. Service interface module

### `payment.api/module-info.java`

```java
module payment.api {
    exports com.example.payment;
}
```

### `PaymentService.java`

```java
package com.example.payment;

public interface PaymentService {
    void pay(double amount);
}
```

This module only defines the service contract.

---

## 2. Provider module

### `bkash.payment/module-info.java`

```java
module bkash.payment {
    requires payment.api;
    provides com.example.payment.PaymentService
        with com.example.bkash.BkashPaymentService;
}
```

### `BkashPaymentService.java`

```java
package com.example.bkash;

import com.example.payment.PaymentService;

public class BkashPaymentService implements PaymentService {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using bKash");
    }
}
```

This module says:
“I provide an implementation of `PaymentService`.”

---

## 3. Consumer module

### `shop.app/module-info.java`

```java
module shop.app {
    requires payment.api;
    uses com.example.payment.PaymentService;
}
```

### `Main.java`

```java
package com.example.shop;

import com.example.payment.PaymentService;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<PaymentService> loader =
                ServiceLoader.load(PaymentService.class);

        for (PaymentService service : loader) {
            service.pay(500.0);
        }
    }
}
```

---

## What `ServiceLoader.load(...)` does

This line:

```java
ServiceLoader<PaymentService> loader =
        ServiceLoader.load(PaymentService.class);
```

means:

“Find all available implementations of `PaymentService` that were declared as providers.”

Java then looks through the modules on the module path and finds modules that declared something like:

```java
provides com.example.payment.PaymentService
    with some.implementation.ClassName;
```

So in this case, it finds `BkashPaymentService`.

---

## Why `uses` is needed

The consumer module says:

```java
uses com.example.payment.PaymentService;
```

This tells the module system that `shop.app` intends to use implementations of that service.

Without `uses`, the module system does not know that this module wants to consume the service.

---

## Why `requires bkash.payment` is not needed

The consumer does not directly depend on `bkash.payment`. It only depends on the interface:

```java
requires payment.api;
```

That is because the consumer only knows about `PaymentService.class`.
It does not know or care which module provides the implementation.

That is the main benefit of this pattern: **loose coupling**.

---

## Mental flow

1. `payment.api` defines the interface
2. `bkash.payment` implements it and declares `provides`
3. `shop.app` declares `uses`
4. `ServiceLoader.load(PaymentService.class)` finds the provider at runtime

---

## If multiple providers exist

If you had another provider module, for example `nagad.payment`, and it also declared:

```java
provides com.example.payment.PaymentService
    with com.example.nagad.NagadPaymentService;
```

then `ServiceLoader.load(...)` would load both providers, and your loop would iterate over both implementations.

---

## In one sentence

`uses` and `provides` declare the service relationship in `module-info.java`, and `ServiceLoader.load(...)` is the code that actually discovers and loads those provider implementations at runtime.
