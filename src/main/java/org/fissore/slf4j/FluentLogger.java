package org.fissore.slf4j;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

/**
 * FluentLogger is what we use to start logging at different levels. It exposes log levels as no-arg methods ({@code info}, {@code debug}, {@code error}...) that will return either a new {@link LoggerAtLevel} instance (if logging at that level is enabled) or a shared {@link NOOPLogger} instance.
 */
public class FluentLogger {

  private static final NOOPLogger NOOP_LOGGER = new NOOPLogger();

  private final Logger logger;
  private final boolean isLocationAwareLogger;

  /**
   * Creates a new {@link FluentLogger} wrapping a {@link Logger}.
   *
   * @param logger the wrapped {@link Logger} instance
   */
  public FluentLogger(Logger logger) {
    this.logger = logger;
    this.isLocationAwareLogger = logger instanceof LocationAwareLogger;
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

    return new LoggerAtLevel(logger::info, logger::info, isLocationAwareLogger, logger, LocationAwareLogger.INFO_INT);
  }

  /**
   * Returns a {@link LoggerAtLevel}, configured with the given {@link Marker}, to log at the "info" level, if logging at "info" level is enabled also considering the given {@link Marker}. Returns a shared {@link NOOPLogger} otherwise.
   *
   * @return a new {@link LoggerAtLevel} or a shared {@link NOOPLogger}
   */
  public LoggerAtLevel info(Marker marker) {
    if (!logger.isInfoEnabled(marker)) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::info, logger::info, isLocationAwareLogger, logger, LocationAwareLogger.INFO_INT).withMarker(marker);
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

    return new LoggerAtLevel(logger::debug, logger::debug, isLocationAwareLogger, logger, LocationAwareLogger.DEBUG_INT);
  }

  /**
   * Returns a {@link LoggerAtLevel}, configured with the given {@link Marker}, to log at the "debug" level, if logging at "debug" level is enabled also considering the given {@link Marker}. Returns a shared {@link NOOPLogger} otherwise.
   *
   * @return a new {@link LoggerAtLevel} or a shared {@link NOOPLogger}
   */
  public LoggerAtLevel debug(Marker marker) {
    if (!logger.isDebugEnabled(marker)) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::debug, logger::debug, isLocationAwareLogger, logger, LocationAwareLogger.INFO_INT).withMarker(marker);
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

    return new LoggerAtLevel(logger::error, logger::error, isLocationAwareLogger, logger, LocationAwareLogger.ERROR_INT);
  }

  /**
   * Returns a {@link LoggerAtLevel}, configured with the given {@link Marker}, to log at the "error" level, if logging at "error" level is enabled also considering the given {@link Marker}. Returns a shared {@link NOOPLogger} otherwise.
   *
   * @return a new {@link LoggerAtLevel} or a shared {@link NOOPLogger}
   */
  public LoggerAtLevel error(Marker marker) {
    if (!logger.isErrorEnabled(marker)) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::error, logger::error, isLocationAwareLogger, logger, LocationAwareLogger.ERROR_INT).withMarker(marker);
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

    return new LoggerAtLevel(logger::trace, logger::trace, isLocationAwareLogger, logger, LocationAwareLogger.TRACE_INT);
  }

  /**
   * Returns a {@link LoggerAtLevel}, configured with the given {@link Marker}, to log at the "trace" level, if logging at "trace" level is enabled also considering the given {@link Marker}. Returns a shared {@link NOOPLogger} otherwise.
   *
   * @return a new {@link LoggerAtLevel} or a shared {@link NOOPLogger}
   */
  public LoggerAtLevel trace(Marker marker) {
    if (!logger.isTraceEnabled(marker)) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::trace, logger::trace, isLocationAwareLogger, logger, LocationAwareLogger.TRACE_INT).withMarker(marker);
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

    return new LoggerAtLevel(logger::warn, logger::warn, isLocationAwareLogger, logger, LocationAwareLogger.WARN_INT);
  }

  /**
   * Returns a {@link LoggerAtLevel}, configured with the given {@link Marker}, to log at the "warn" level, if logging at "warn" level is enabled also considering the given {@link Marker}. Returns a shared {@link NOOPLogger} otherwise.
   *
   * @return a new {@link LoggerAtLevel} or a shared {@link NOOPLogger}
   */
  public LoggerAtLevel warn(Marker marker) {
    if (!logger.isWarnEnabled(marker)) {
      return NOOP_LOGGER;
    }

    return new LoggerAtLevel(logger::warn, logger::warn, isLocationAwareLogger, logger, LocationAwareLogger.WARN_INT).withMarker(marker);
  }
}
