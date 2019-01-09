package org.fissore.slf4j;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.fissore.slf4j.Util.lazy;
import static org.junit.Assert.assertEquals;

public class LoggingAtDifferentLevelsTest {

  @Before
  public void setUp() {
    TestConsoleAppender.init();
  }

  @Test
  public void offLogger() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.off.Test");

    tryLoggingAtAllLevels(logger);

    assertEquals(0, TestConsoleAppender.EVENTS.size());
  }

  @Test
  public void errorLogger() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.error.Test");

    tryLoggingAtAllLevels(logger);

    assertEquals(5, TestConsoleAppender.EVENTS.size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.ERROR_INT).size());
  }

  @Test
  public void warnLogger() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.warn.Test");

    tryLoggingAtAllLevels(logger);

    assertEquals(10, TestConsoleAppender.EVENTS.size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.ERROR_INT).size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.WARN_INT).size());
  }

  @Test
  public void infoLogger() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.info.Test");

    tryLoggingAtAllLevels(logger);

    assertEquals(15, TestConsoleAppender.EVENTS.size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.ERROR_INT).size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.WARN_INT).size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.INFO_INT).size());
  }

  @Test
  public void debugLogger() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.debug.Test");

    tryLoggingAtAllLevels(logger);

    assertEquals(20, TestConsoleAppender.EVENTS.size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.ERROR_INT).size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.WARN_INT).size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.INFO_INT).size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.DEBUG_INT).size());
  }

  @Test
  public void traceLogger() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.trace.Test");

    tryLoggingAtAllLevels(logger);

    assertEquals(25, TestConsoleAppender.EVENTS.size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.ERROR_INT).size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.WARN_INT).size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.INFO_INT).size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.DEBUG_INT).size());
    assertEquals(5, loggedEventsOfLevel(TestConsoleAppender.EVENTS, Level.TRACE_INT).size());
  }

  private List<ILoggingEvent> loggedEventsOfLevel(List<ILoggingEvent> events, int targetLevelInt) {
    return events.stream()
      .filter(event -> event.getLevel().levelInt == targetLevelInt)
      .collect(Collectors.toList());
  }

  private void tryLoggingAtAllLevels(FluentLogger logger) {
    logger.error().log("error no args");
    logger.error().log("error 1 arg {}", "one");
    logger.error().log("error 2 args {} {}", "one", lazy(() -> "two"));
    logger.error().log("error 3 args {} {} {}", "one", lazy(() -> "two"), "three");
    logger.error().withCause(new Exception()).log("error 2 args with exception {} {}", "one", lazy(() -> "two"));

    logger.warn().log("warn no args");
    logger.warn().log("warn 1 arg {}", "one");
    logger.warn().log("warn 2 args {} {}", "one", lazy(() -> "two"));
    logger.warn().log("warn 3 args {} {} {}", "one", lazy(() -> "two"), "three");
    logger.warn().withCause(new Exception()).log("warn 2 args with exception {} {}", "one", lazy(() -> "two"));

    logger.info().log("info no args");
    logger.info().log("info 1 arg {}", "one");
    logger.info().log("info 2 args {} {}", "one", lazy(() -> "two"));
    logger.info().log("info 3 args {} {} {}", "one", lazy(() -> "two"), "three");
    logger.info().withCause(new Exception()).log("info 2 args with exception {} {}", "one", lazy(() -> "two"));

    logger.debug().log("debug no args");
    logger.debug().log("debug 1 arg {}", "one");
    logger.debug().log("debug 2 args {} {}", "one", lazy(() -> "two"));
    logger.debug().log("debug 3 args {} {} {}", "one", lazy(() -> "two"), "three");
    logger.debug().withCause(new Exception()).log("debug 2 args with exception {} {}", "one", lazy(() -> "two"));

    logger.trace().log("trace no args");
    logger.trace().log("trace 1 arg {}", "one");
    logger.trace().log("trace 2 args {} {}", "one", lazy(() -> "two"));
    logger.trace().log("trace 3 args {} {} {}", "one", lazy(() -> "two"), "three");
    logger.trace().withCause(new Exception()).log("trace 2 args with exception {} {}", "one", lazy(() -> "two"));
  }
}
