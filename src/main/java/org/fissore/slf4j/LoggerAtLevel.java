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
        FormattingTuple ft = MessageFormatter.format(format, toString(arg));
        messageThrowableConsumer.accept(ft.getMessage(), cause);
    }

    public void log(String format, Object arg1, Object arg2) {
        FormattingTuple ft = MessageFormatter.format(format, toString(arg1), toString(arg2));
        messageThrowableConsumer.accept(ft.getMessage(), cause);
    }

    public void log(String format, Object... args) {
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
