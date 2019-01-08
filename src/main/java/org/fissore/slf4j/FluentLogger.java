package org.fissore.slf4j;

import org.slf4j.Logger;

public class FluentLogger {

    private final LoggerAtLevel infoLogger;
    private final LoggerAtLevel debugLogger;
    private final LoggerAtLevel errorLogger;
    private final LoggerAtLevel traceLogger;
    private final LoggerAtLevel warnLogger;

    public FluentLogger(Logger logger) {
        this.infoLogger = new LoggerAtLevel(logger::info);
        this.debugLogger = new LoggerAtLevel(logger::debug);
        this.errorLogger = new LoggerAtLevel(logger::error);
        this.traceLogger = new LoggerAtLevel(logger::trace);
        this.warnLogger = new LoggerAtLevel(logger::warn);
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
