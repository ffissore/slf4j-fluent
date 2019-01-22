package org.fissore.slf4j;

import org.slf4j.Logger;

/**
 * FluentLogger is what we use to start logging at different levels. It exposes log levels as no-arg methods ({@code info}, {@code debug}, {@code error}...) that will return either a new {@link LoggerAtLevel} instance (if logging at that level is enabled) or a shared {@link NOOPLogger} instance.
 */
public class FluentLogger {

  private static final NOOPLogger NOOP_LOGGER = new NOOPLogger();

  private final Logger logger;

  /**
   * Creates a new {@link FluentLogger} wrapping a {@link Logger}.
   *
   * @param logger the wrapped {@link Logger} instance
   */
  public FluentLogger(Logger logger) {
    this.logger = logger;
  }

  /**
   * Returns a {@link LoggerAtLevel} to log at the "info" level, if logging at "info" level is enabled. Returns a shared {@link NOOPLogger} otherwise.
   *
   * @return a new {@link LoggerAtLevel} or a shared {@link NOOPLogger}
   */
  public LoggerAtLevel info() {
    if (!logger.isInfoEnabled()) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::info);
  }

  /**
   * Returns a {@link LoggerAtLevel} to log at the "debug" level, if logging at "debug" level is enabled. Returns a shared {@link NOOPLogger} otherwise.
   *
   * @return a new {@link LoggerAtLevel} or a shared {@link NOOPLogger}
   */
  public LoggerAtLevel debug() {
    if (!logger.isDebugEnabled()) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::debug);
  }

  /**
   * Returns a {@link LoggerAtLevel} to log at the "error" level, if logging at "error" level is enabled. Returns a shared {@link NOOPLogger} otherwise.
   *
   * @return a new {@link LoggerAtLevel} or a shared {@link NOOPLogger}
   */
  public LoggerAtLevel error() {
    if (!logger.isErrorEnabled()) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::error);
  }

  /**
   * Returns a {@link LoggerAtLevel} to log at the "trace" level, if logging at "trace" level is enabled. Returns a shared {@link NOOPLogger} otherwise.
   *
   * @return a new {@link LoggerAtLevel} or a shared {@link NOOPLogger}
   */
  public LoggerAtLevel trace() {
    if (!logger.isTraceEnabled()) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::trace);
  }

  /**
   * Returns a {@link LoggerAtLevel} to log at the "warn" level, if logging at "warn" level is enabled. Returns a shared {@link NOOPLogger} otherwise.
   *
   * @return a new {@link LoggerAtLevel} or a shared {@link NOOPLogger}
   */
  public LoggerAtLevel warn() {
    if (!logger.isWarnEnabled()) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::warn);
  }
}
