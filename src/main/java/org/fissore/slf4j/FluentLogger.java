package org.fissore.slf4j;

import org.slf4j.Logger;

public class FluentLogger {

    private static final LoggerAtLevel NOOP_LOGGER = new NOOPLogger();

    private final LoggerAtLevel infoLogger;
    private final LoggerAtLevel debugLogger;
    private final LoggerAtLevel errorLogger;
    private final LoggerAtLevel traceLogger;
    private final LoggerAtLevel warnLogger;

    public FluentLogger(Logger logger) {
        if (!logger.isInfoEnabled()) {
            this.infoLogger = NOOP_LOGGER;
        } else {
            this.infoLogger = new LoggerAtLevel(logger::info);
        }
        if (!logger.isDebugEnabled()) {
            this.debugLogger = NOOP_LOGGER;
        } else {
            this.debugLogger = new LoggerAtLevel(logger::debug);
        }
        if (!logger.isErrorEnabled()) {
            this.errorLogger = NOOP_LOGGER;
        } else {
            this.errorLogger = new LoggerAtLevel(logger::error);
        }
        if (!logger.isTraceEnabled()) {
            this.traceLogger = NOOP_LOGGER;
        } else {
            this.traceLogger = new LoggerAtLevel(logger::trace);
        }
        if (!logger.isWarnEnabled()) {
            this.warnLogger = NOOP_LOGGER;
        } else {
            this.warnLogger = new LoggerAtLevel(logger::warn);
        }
    }

    public LoggerAtLevel atInfo() {
        return infoLogger;
    }

    public LoggerAtLevel atDebug() {
        return debugLogger;
    }

    public LoggerAtLevel atError() {
        return errorLogger;
    }

    public LoggerAtLevel atTrace() {
        return traceLogger;
    }

    public LoggerAtLevel atWarn() {
        return warnLogger;
    }
}
