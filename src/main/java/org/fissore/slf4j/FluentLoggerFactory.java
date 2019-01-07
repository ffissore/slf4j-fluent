package org.fissore.slf4j;

import org.slf4j.LoggerFactory;

public class FluentLoggerFactory {

    public static FluentLogger getLogger(String name) {
        return new FluentLogger(LoggerFactory.getLogger(name));
    }

    public static FluentLogger getLogger(Class<?> clazz) {
        return new FluentLogger(LoggerFactory.getLogger(clazz));
    }

}
