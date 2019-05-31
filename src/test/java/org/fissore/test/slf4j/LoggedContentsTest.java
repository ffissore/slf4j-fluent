package org.fissore.test.slf4j;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.fissore.slf4j.FluentLogger;
import org.fissore.slf4j.FluentLoggerFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static org.fissore.slf4j.Util.lazy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoggedContentsTest {

  @Before
  public void setUp() {
    TestConsoleAppender.init();
  }

  @Test
  public void errorLoggerContents() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.error.Test");

    logger.error().log("error no args");
    logger.error().log(() -> "error no args");
    logger.error().log("error 1 arg {}", "one");
    logger.error().log(() -> "error 1 arg {}", "one");
    logger.error().log("error 2 args {} {}", "one", (Supplier<?>) () -> "two");
    logger.error().log(() -> "error 2 args {} {}", "one", (Supplier<?>) () -> "two");
    logger.error().log("error 2 args {} {}", "one", lazy(() -> "two"));
    logger.error().log(() -> "error 2 args {} {}", "one", lazy(() -> "two"));
    logger.error().log("error 3 args {} {} {}", "one", (Supplier<?>) () -> null, lazy(null));
    logger.error().log(() -> "error 3 args {} {} {}", "one", (Supplier<?>) () -> null, lazy(null));
    logger.error().log("error 4 args {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four");
    logger.error().log(() -> "error 4 args {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four");
    logger.error().log("error 5 args {} {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four", "five");
    logger.error().log(() -> "error 5 args {} {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four", "five");
    logger.error().log("error 6 args {} {} {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four", "five", "six");
    logger.error().log(() -> "error 6 args {} {} {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four", "five", "six");
    logger.error().log("error null varargs", (Object[]) null);
    logger.error().log(() -> "error null varargs", (Object[]) null);
    logger.error().withCause(new Exception()).log("error 2 args with exception {} {}", "one", (Supplier<?>) () -> "two");
    logger.error().withCause(new Exception()).log(() -> "error 2 args with exception {} {}", "one", (Supplier<?>) () -> "two");

    assertEquals(20, TestConsoleAppender.EVENTS.size());

    int idx = -1;
    assertEquals("error no args", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error no args", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 1 arg one", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 1 arg one", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 2 args one two", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 2 args one two", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 2 args one two", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 2 args one two", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 3 args one null null", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 3 args one null null", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 4 args one null null four", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 4 args one null null four", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 5 args one null null four five", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 5 args one null null four five", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 6 args one null null four five six", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 6 args one null null four five six", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error null varargs", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error null varargs", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 2 args with exception one two", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());
    assertEquals("error 2 args with exception one two", TestConsoleAppender.EVENTS.get(++idx).getFormattedMessage());

    int lineNumber = 26;
    for (ILoggingEvent event : TestConsoleAppender.EVENTS) {
      assertTrue(event.getCallerData().length > 0);
      assertEquals(getClass().getName(), event.getCallerData()[0].getClassName());
      assertEquals("errorLoggerContents", event.getCallerData()[0].getMethodName());
      assertEquals(lineNumber, event.getCallerData()[0].getLineNumber());
      lineNumber++;
    }
  }

}
