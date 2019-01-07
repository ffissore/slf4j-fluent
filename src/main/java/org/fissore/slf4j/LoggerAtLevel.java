package org.fissore.slf4j;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.util.function.BiConsumer;

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
        FormattingTuple ft = MessageFormatter.format(format, arg);
        messageThrowableConsumer.accept(ft.getMessage(), cause);
    }

    public void log(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
        messageThrowableConsumer.accept(ft.getMessage(), cause);
    }

    public void log(String format, Object... args) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, args);
        messageThrowableConsumer.accept(ft.getMessage(), cause);
    }
}
