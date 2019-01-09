package org.fissore.slf4j;

import org.slf4j.Logger;

public class FluentLogger {

  private static final NOOPLogger NOOP_LOGGER = new NOOPLogger();

  private final Logger logger;

  public FluentLogger(Logger logger) {
    this.logger = logger;
  }

  public LoggerAtLevel info() {
    if (!logger.isInfoEnabled()) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::info);
  }

  public LoggerAtLevel debug() {
    if (!logger.isDebugEnabled()) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::debug);
  }

  public LoggerAtLevel error() {
    if (!logger.isErrorEnabled()) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::error);
  }

  public LoggerAtLevel trace() {
    if (!logger.isTraceEnabled()) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::trace);
  }

  public LoggerAtLevel warn() {
    if (!logger.isWarnEnabled()) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::warn);
  }
}
