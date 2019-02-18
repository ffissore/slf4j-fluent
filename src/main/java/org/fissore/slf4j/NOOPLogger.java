package org.fissore.slf4j;

/**
 * A no-op logger is a logger that does nothing. {@link FluentLogger} has one statically initialized instance of this class, and will return it whenever the desired logging level is not enabled.
 */
public class NOOPLogger extends LoggerAtLevel {

  public NOOPLogger() {
    super(null, false, null, -1);
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
