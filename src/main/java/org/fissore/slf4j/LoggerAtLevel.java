package org.fissore.slf4j;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

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

  private static final LoggerStats LOGGER_STATS = new LoggerStats();

  private final BiConsumer<String, Throwable> messageThrowableConsumer;

  private Throwable cause;
  private LogEveryAmountOfTime logEveryAmountOfTime;
  private LogEveryNumberOfCalls logEveryNumberOfCalls;

  public LoggerAtLevel(BiConsumer<String, Throwable> messageThrowableConsumer) {
    this.messageThrowableConsumer = messageThrowableConsumer;
  }

  public LoggerAtLevel withCause(Throwable cause) {
    this.cause = cause;
    return this;
  }

  public LoggerAtLevel every(long amountOfTime, ChronoUnit unit) {
    if (logEveryNumberOfCalls != null) {
      throw new IllegalStateException("You cannot filter log by time frame AND number of calls: you must pick one");
    }
    this.logEveryAmountOfTime = new LogEveryAmountOfTime(amountOfTime, unit);
    return this;
  }

  public LoggerAtLevel every(int amountOfCalls) {
    if (logEveryAmountOfTime != null) {
      throw new IllegalStateException("You cannot filter log by time frame AND number of calls: you must pick one");
    }
    this.logEveryNumberOfCalls = new LogEveryNumberOfCalls(amountOfCalls);
    return this;
  }

  private static final Object[] EMPTY_ARRAY = new Object[0];

  public void log(String message) {
    logInternal(message, EMPTY_ARRAY);
  }

  public void log(String format, Object arg) {
    logInternal(format, new Object[] { arg });
  }

  public void log(String format, Object arg1, Object arg2) {
    logInternal(format, new Object[] { arg1, arg2 });
  }

  public void log(String format, Object arg1, Object arg2, Object arg3) {
    logInternal(format, new Object[] { arg1, arg2, arg3 });
  }

  public void log(String format, Object arg1, Object arg2, Object arg3, Object arg4) {
    logInternal(format, new Object[] { arg1, arg2, arg3, arg4 });
  }

  public void log(String format, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
    logInternal(format, new Object[] { arg1, arg2, arg3, arg4, arg5 });
  }

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

    FormattingTuple ft = MessageFormatter.arrayFormat(format, toStrings(args));
    messageThrowableConsumer.accept(ft.getMessage(), cause);
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
