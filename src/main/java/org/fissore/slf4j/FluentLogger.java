package org.fissore.slf4j;

import org.slf4j.Logger;

public class FluentLogger {

    private static final LoggerAtLevel NOOP_LOGGER = new NOOPLogger();

    private final Logger logger;

    public FluentLogger(Logger logger) {
        this.logger = logger;
    }

    public LoggerAtLevel atInfo() {
        if (!logger.isInfoEnabled()) {
            return NOOP_LOGGER;
        }

        return new LoggerAtLevel(logger::info);
    }

    public LoggerAtLevel atDebug() {
        if (!logger.isDebugEnabled()) {
            return NOOP_LOGGER;
        }

        return new LoggerAtLevel(logger::debug);
    }

    public LoggerAtLevel atError() {
        if (!logger.isErrorEnabled()) {
            return NOOP_LOGGER;
        }

        return new LoggerAtLevel(logger::error);
    }

    public LoggerAtLevel atTrace() {
        if (!logger.isTraceEnabled()) {
            return NOOP_LOGGER;
        }

        return new LoggerAtLevel(logger::trace);
    }

    public LoggerAtLevel atWarn() {
        if (!logger.isWarnEnabled()) {
            return NOOP_LOGGER;
        }

        return new LoggerAtLevel(logger::warn);
    }
}
