package org.fissore.slf4j;

import org.slf4j.LoggerFactory;

/**
 * The entry point for using slf4j-fluent. It creates a new {@link FluentLogger} associated with the given name or class.
 */
public class FluentLoggerFactory {

  /**
   * Creates a new {@link FluentLogger} associated with the given name.
   *
   * @param name the name of the logger
   * @return a new {@link FluentLogger} instance that wraps a {@link org.slf4j.Logger}
   */
  public static FluentLogger getLogger(String name) {
    return new FluentLogger(LoggerFactory.getLogger(name));
  }

  /**
   * Creates a new {@link FluentLogger} associated with the given class.
   *
   * @param clazz the class to associate this logger to
   * @return a new {@link FluentLogger} instance that wraps a {@link org.slf4j.Logger}
   */
  public static FluentLogger getLogger(Class<?> clazz) {
    return new FluentLogger(LoggerFactory.getLogger(clazz));
  }

}
