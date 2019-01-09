package org.fissore.slf4j;

public class NOOPLogger extends LoggerAtLevel {

  public NOOPLogger() {
    super(null);
  }

  @Override
  public LoggerAtLevel withCause(Throwable cause) {
    return this;
  }

  @Override
  public void log(String message) {
  }

  @Override
  public void log(String format, Object arg) {
  }

  @Override
  public void log(String format, Object arg1, Object arg2) {
  }

  @Override
  public void log(String format, Object arg1, Object arg2, Object arg3) {
  }

  @Override
  public void log(String format, Object arg1, Object arg2, Object arg3, Object arg4) {
  }

  @Override
  public void log(String format, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
  }

  @Override
  public void log(String format, Object... args) {
  }
}
