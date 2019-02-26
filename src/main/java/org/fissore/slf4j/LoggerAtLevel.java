package org.fissore.slf4j;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * This is where the fluent API of slf4j-fluent is implemented.
 * With its methods we can build the log message, assign an exception to it, and specify logging limits such as quantity ("don't log this more than once every 5 times") or time ("don't log this more than once every 2 seconds").
 * <p>
 * In order to avoid implicit array creation when using varargs, this class provides a number overloaded version of the {@code log} method with increasing number of arguments.
 * <p>
 * {@code log} methods arguments can be of any type. If they are {@link Supplier}s, they will be called only when actually logging, thus providing lazy argument evaluation. Also see {@link Util#lazy(Supplier)}.
 * <p>
 * When filtering log entries based on quantity or time, we need to generate the "call site" which is the fully qualified name of the class, plus the name of the method calling the {@code log} method, plus the line number.
 *
 * @see Util#lazy(Supplier)
 */
public class LoggerAtLevel {

  private static class LogEveryAmountOfTime {

    private final long amount;
    private final ChronoUnit unit;

    public LogEveryAmountOfTime(long amount, ChronoUnit unit) {
      this.amount = amount;
      this.unit = unit;
    }

  }

  private static class LogEveryNumberOfCalls {

    private final int amount;

    public LogEveryNumberOfCalls(int amount) {
      this.amount = amount;
    }

  }

  private static final String FQCN = LoggerAtLevel.class.getName();
  private static final LoggerStats LOGGER_STATS = new LoggerStats();
  private static final Object[] EMPTY_ARRAY = new Object[0];

  private final BiConsumer<String, Object[]> loggerMethod;
  private final boolean isLocationAwareLogger;
  private final Logger logger;
  private final int level;

  private Throwable cause;
  private Marker marker;
  private LogEveryAmountOfTime logEveryAmountOfTime;
  private LogEveryNumberOfCalls logEveryNumberOfCalls;

  public LoggerAtLevel(BiConsumer<String, Object[]> loggerMethod, boolean isLocationAwareLogger, Logger logger, int level) {
    this.loggerMethod = loggerMethod;
    this.isLocationAwareLogger = isLocationAwareLogger;
    this.logger = logger;
    this.level = level;
  }

  /**
   * Associates an exception to this log entry.
   *
   * @param cause the exception we want to associate this log entry to
   * @return this instance of {@link LoggerAtLevel}
   */
  public LoggerAtLevel withCause(Throwable cause) {
    this.cause = cause;
    return this;
  }

  public LoggerAtLevel withMarker(Marker marker) {
    this.marker = marker;
    return this;
  }

  /**
   * Configures this {@link LoggerAtLevel} to log at most every amount of time.
   * <p>
   * For example, say we want to log at most every 2 seconds.
   * If our code attempts to log multiple times for 5 seconds, we'll see only 3 log entries in our log files: the first one, the first one after 2 seconds, and the first one after 4 seconds.
   *
   * @param amountOfTime the amount of time to wait between log entries
   * @param unit         the unit of time expressed by amountOfTime
   * @return this instance of {@link LoggerAtLevel}
   * @throws IllegalStateException if this {@link LoggerAtLevel} was already configured to limit logging by quantity
   */
  public LoggerAtLevel every(long amountOfTime, ChronoUnit unit) {
    if (logEveryNumberOfCalls != null) {
      throw new IllegalStateException("We cannot filter log by time frame AND number of calls: pick one please");
    }
    this.logEveryAmountOfTime = new LogEveryAmountOfTime(amountOfTime, unit);
    return this;
  }

  /**
   * Configures this {@link LoggerAtLevel} to log at most every number of times
   * <p>
   * For example, say we want to log at most every 5 times.
   * If our code attempts to log 8 times, we'll see 2 log entries in our log files: the first one, and the fifth one.
   *
   * @param amountOfCalls the amount of calls that must be made before logging
   * @return this instance of {@link LoggerAtLevel}
   * @throws IllegalStateException if this {@link LoggerAtLevel} was already configured to limit logging by time
   */
  public LoggerAtLevel every(int amountOfCalls) {
    if (logEveryAmountOfTime != null) {
      throw new IllegalStateException("We cannot filter log by time frame AND number of calls: pick one please");
    }
    this.logEveryNumberOfCalls = new LogEveryNumberOfCalls(amountOfCalls);
    return this;
  }

  /**
   * Logs a message with no params
   *
   * @param message the log message
   */
  public void log(String message) {
    logInternal(message, EMPTY_ARRAY);
  }

  /**
   * Logs a message with one param
   *
   * @param format the log message
   * @param arg    a log message param
   */
  public void log(String format, Object arg) {
    logInternal(format, new Object[] { arg });
  }

  /**
   * Logs a message with two params
   *
   * @param format the log message
   * @param arg1   a log message param
   * @param arg2   a log message param
   */
  public void log(String format, Object arg1, Object arg2) {
    logInternal(format, new Object[] { arg1, arg2 });
  }

  /**
   * Logs a message with three params
   *
   * @param format the log message
   * @param arg1   a log message param
   * @param arg2   a log message param
   * @param arg3   a log message param
   */
  public void log(String format, Object arg1, Object arg2, Object arg3) {
    logInternal(format, new Object[] { arg1, arg2, arg3 });
  }

  /**
   * Logs a message with four params
   *
   * @param format the log message
   * @param arg1   a log message param
   * @param arg2   a log message param
   * @param arg3   a log message param
   * @param arg4   a log message param
   */
  public void log(String format, Object arg1, Object arg2, Object arg3, Object arg4) {
    logInternal(format, new Object[] { arg1, arg2, arg3, arg4 });
  }

  /**
   * Logs a message with five params
   *
   * @param format the log message
   * @param arg1   a log message param
   * @param arg2   a log message param
   * @param arg3   a log message param
   * @param arg4   a log message param
   * @param arg5   a log message param
   */
  public void log(String format, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
    logInternal(format, new Object[] { arg1, arg2, arg3, arg4, arg5 });
  }

  /**
   * Logs a message with varying number of params
   *
   * @param format the log message
   * @param args   log message params
   */
  public void log(String format, Object... args) {
    logInternal(format, args);
  }

  private void logInternal(String format, Object[] args) {
    if (logEveryAmountOfTime != null || logEveryNumberOfCalls != null) {
      String caller = getCaller();
      if (logEveryNumberOfCalls != null
        && !LOGGER_STATS.recordCallThenCheckIfNumberOfCallsMatchesAmount(caller, logEveryNumberOfCalls.amount)) {
        return;
      }

      if (logEveryAmountOfTime != null
        && !LOGGER_STATS.recordCallAndCheckIfEnoughTimePassed(caller, logEveryAmountOfTime.amount, logEveryAmountOfTime.unit)) {
        return;
      }
    }

    args = toStrings(args);
    if (isLocationAwareLogger) {
      ((LocationAwareLogger) logger).log(marker, FQCN, level, format, args, cause);
    } else {
      Object[] newArgs = args;
      if(cause != null) {
        newArgs = new Object[args.length + 1];
        System.arraycopy(args, 0, newArgs, 0, args.length);
        newArgs[newArgs.length - 1] = cause;
      }
      loggerMethod.accept(format, newArgs);
    }
  }

  private String getCaller() {
    return Arrays.stream(new Throwable().getStackTrace())
      .filter(trace -> !trace.getClassName().startsWith(LoggerAtLevel.class.getPackage().getName()))
      .findFirst()
      .map(trace -> trace.getClassName() + ":" + trace.getMethodName() + ":" + trace.getLineNumber())
      .orElseThrow(IllegalStateException::new);
  }

  private Object toString(Object arg) {
    if (arg instanceof Supplier) {
      arg = toString(((Supplier) arg).get());
    }

    return arg;
  }

  private Object[] toStrings(Object[] args) {
    if (args == null) {
      return null;
    }

    for (int i = 0; i < args.length; i++) {
      args[i] = toString(args[i]);
    }
    return args;
  }
}
