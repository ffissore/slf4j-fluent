# slf4j-fluent, a fluent API for SLF4J

[![Build Status](https://travis-ci.com/ffissore/slf4j-fluent.svg?branch=master)](https://travis-ci.com/ffissore/slf4j-fluent)

slf4j-fluent provides a fluent API for [SLF4J](https://www.slf4j.org/).

## Motivation

As slf4j users, we are used to write code like the following:

```java
Logger log = LoggerFactory.getLogger(getClass());

log.debug("A debug statement with {} args: {}, {}", 2, "value 1", someObject.expensiveMethod());
```

This code has 2 problems: 
* the `debug` method will always be called regardless of the logger level
* and all the arguments will be evaluated and passed, even that `expensiveMethod` we'd like not to call unless logger level is really set to `debug`.

Current solution is to wrap that code this way:

```java
if (log.isDebugEnabled()) {
    log.debug("A debug log entry with {} args: {}, {}", 2, "value 1", someObject.expensiveMethod());    
}
```

## A fluent solution

By using `slf4j-fluent` together with slf4j, we can rewrite that code this way:

```java
FluentLogger log = FluentLoggerFactory.getLogger(getClass());

log.debug().log("A debug log entry with {} args: {}, {}", 2, "value 1", lazy(() -> someObject.expensiveMethod()));
```

The `debug()` (and `error()`, `info()`, etc) method returns a no-op logger when the logger is not set at the appropriate level (which might lead Hotspot to optimize that method call).

The `lazy(...)` syntax leverages lambdas to postpone argument evaluation to the latest moment.

## Requirements

slf4j-fluent requires java 8 as it uses lambdas.

It is not an slf4j replacement, nor yet-another-logging-framework: it's just a fluent API for slf4j. Which means you can start using it right now with no changes to your existing code.

## How to use it

Add slf4j-fluent as a dependency to your project

```xml
<dependency> 
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-log4j12</artifactId>
  <version>1.7.25</version>
</dependency>
<dependency> 
  <groupId>org.fissore</groupId>
  <artifactId>slf4j-fluent</artifactId>
  <version>0.9.0</version>
</dependency>
```

Initialize FluentLogger and start logging

```java
FluentLogger log = FluentLoggerFactory.getLogger(getClass());

log.debug().log("A debug log entry with {} args: {}, {}", 2, "value 1", lazy(() -> someObject.expensiveMethod()));

// will add the stacktrace of the cause to the log entry
log.error().withCause(exception).log("An error occured while fetching user {}", user.getId());

// will log every 5 calls to `log` method, instead of every single time
log.error().every(5).log("Errors occured, but we print only one entry every 5");

// will log every 1 second, instead of every single time
log.error().every(1, ChronoUnit.SECONDS).log("Errors occured, but we print only one entry every 1 second");
```

## Trivia

The fluent API looks a lot like that of [Flogger](https://github.com/google/flogger), which however has the downside of being yet-another-logging-framework.
