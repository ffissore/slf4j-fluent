# slf4j-fluent, a fluent API for SLF4J

[![Build Status](https://travis-ci.org/ffissore/slf4j-fluent.svg?branch=master)](https://travis-ci.org/ffissore/slf4j-fluent)
![License](https://img.shields.io/github/license/ffissore/slf4j-fluent.svg)
![Version](https://img.shields.io/maven-central/v/org.fissore/slf4j-fluent.svg)
[![security status](https://www.meterian.com/badge/gh/ffissore/slf4j-fluent/security)](https://www.meterian.com/report/gh/ffissore/slf4j-fluent)
[![stability status](https://www.meterian.com/badge/gh/ffissore/slf4j-fluent/stability)](https://www.meterian.com/report/gh/ffissore/slf4j-fluent)

slf4j-fluent provides a fluent API for [SLF4J](https://www.slf4j.org/).

## How to use it

Add slf4j-fluent as a dependency to your project

```xml
<dependency> 
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-log4j12</artifactId>
  <version>1.7.32</version>
</dependency>
<dependency> 
  <groupId>org.fissore</groupId>
  <artifactId>slf4j-fluent</artifactId>
  <version>0.13.3</version>
</dependency>
```

Initialize FluentLogger and start logging

```java
FluentLogger log = FluentLoggerFactory.getLogger(getClass());

log.debug().log("debug entry with {} args: {}, {}", 2, "value 1", lazy(() -> someObject.expensiveMethod()));

// will add the stacktrace of the cause to the log entry
log.error().withCause(exception).log("An error occured while fetching user {}", user.getId());

// will log every 5 calls to `log` method, instead of every single time
log.error().every(5).log("Errors occured, but we print only one entry every 5");

// will log every 1 second, instead of every single time
log.error().every(1, ChronoUnit.SECONDS).log("Errors occured, but we print only one entry every 1 second");
```

## More examples

```java
import static org.fissore.slf4j.Util.lazy;
[...]
FluentLogger log = FluentLoggerFactory.getLogger(getClass());

// simple log
log.debug().log("debug with no args");

// log with args
String hello = "Hello world";
log.info().log("info with normal arg: {}", hello);

// log with lazy args
String norm = "norm";
log.error().log("error with normal arg: {}, and lazy arg {}", norm, lazy(() -> "lazy arg which takes a while to compute"));

// log with lazy message (available since 0.13.0)
log.warn().log(() -> "a lazy warning message");

// log with cause
Exception e = new Exception();
log.error().withCause(e).log("An error occured");

// rate limiting: log at most every 500 millis
log.error().every(500, ChronoUnit.MILLIS).log("This error will be logged at most every 500 millis");

// rate limiting: log at most every 5 calls
log.error().every(5).log("This error will be logged every 5 times the `log` method is called");

// all together
log.error().withCause(new Exception()).every(10).log(() -> "error with normal arg: {}, and lazy arg {}", norm, lazy(() -> "lazy arg which takes a while to compute"));
```

## Motivation

As slf4j users, we are used to write code like the following:

```java
Logger log = LoggerFactory.getLogger(getClass());

log.debug("debug entry with {} args: {}, {}", 2, "value 1", someObject.expensiveMethod());
```

This code has 2 problems:

* the `debug` method will always be called regardless of the logger level
* and all the arguments will be evaluated and passed, even that `expensiveMethod` we'd like not to call unless logger level is really set to `debug`.

Current solution is to wrap that code this way:

```java
if (log.isDebugEnabled()) {
    log.debug("debug entry with {} args: {}, {}", 2, "value 1", someObject.expensiveMethod());
}
```

## A fluent solution

By using `slf4j-fluent` together with slf4j, we can rewrite that code this way:

```java
FluentLogger log = FluentLoggerFactory.getLogger(getClass());

log.debug().log("debug entry with {} args: {}, {}", 2, "value 1", lazy(() -> someObject.expensiveMethod()));
```

The `debug()` (and `error()`, `info()`, etc) method returns a no-op logger when the logger is not set at the appropriate level (which might lead Hotspot to optimize that method
call).

The `lazy(...)` syntax leverages lambdas to postpone argument evaluation to the latest moment.

The `log()` method has overloads with up to 5 arguments, so [the cost of varargs](https://stackoverflow.com/questions/2426455/javas-varargs-performance) is postponed. If 5 is not
enough, open an issue and we'll add more.

## Requirements

slf4j-fluent requires java 8 as it uses lambdas.

It is not an slf4j replacement, nor yet-another-logging-framework: it's just a fluent API for slf4j. Which means you can start using it right now with no changes to your existing
code.

## Trivia

The fluent API looks a lot like that of [Flogger](https://github.com/google/flogger), which however has the downside of being yet-another-logging-framework.

## Changelog

See [Changelog](CHANGELOG.md).
