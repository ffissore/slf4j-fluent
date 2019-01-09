package org.fissore.slf4j;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class LoggerAtLevel {

  private final BiConsumer<String, Throwable> messageThrowableConsumer;

  private Throwable cause;

  public LoggerAtLevel(BiConsumer<String, Throwable> messageThrowableConsumer) {
    this.messageThrowableConsumer = messageThrowableConsumer;
  }

  public LoggerAtLevel withCause(Throwable cause) {
    this.cause = cause;
    return this;
  }

  public void log(String message) {
    messageThrowableConsumer.accept(message, cause);
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
    FormattingTuple ft = MessageFormatter.arrayFormat(format, toStrings(args));
    messageThrowableConsumer.accept(ft.getMessage(), cause);
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
