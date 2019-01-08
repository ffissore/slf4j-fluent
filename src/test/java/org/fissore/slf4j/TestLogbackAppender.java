package org.fissore.slf4j;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;

import java.util.ArrayList;
import java.util.List;

/**
 * Hacky appender that will expose logged events via a public static var, thus allowing tests to check what was logged
 * Not thread safe
 */
public class TestLogbackAppender extends ConsoleAppender<ILoggingEvent> {

  public static final List<ILoggingEvent> EVENTS = new ArrayList<>();

  @Override
  protected void append(ILoggingEvent event) {
    EVENTS.add(event);
    super.append(event);
  }
}
