package org.fissore.slf4j;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Supplier;

import static org.fissore.slf4j.Util.lazy;
import static org.junit.Assert.assertEquals;

public class ContentsLoggedTest {

  @Before
  public void setUp() {
    TestConsoleAppender.init();
  }

  @Test
  public void errorLoggerContents() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.error.Test");

    logger.error().log("error no args");
    logger.error().log("error 1 arg {}", "one");
    logger.error().log("error 2 args {} {}", "one", (Supplier<?>) () -> "two");
    logger.error().log("error 2 args {} {}", "one", lazy(() -> "two"));
    logger.error().log("error 3 args {} {} {}", "one", (Supplier<?>) () -> null, lazy(null));
    logger.error().log("error 4 args {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four");
    logger.error().log("error 5 args {} {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four", "five");
    logger.error().log("error 6 args {} {} {} {} {} {}", "one", (Supplier<?>) () -> null, lazy(null), "four", "five", "six");
    logger.error().log("error null varargs", (Object[]) null);
    logger.error().withCause(new Exception()).log("error 2 args with exception {} {}", "one", (Supplier<?>) () -> "two");

    assertEquals(10, TestConsoleAppender.EVENTS.size());
    assertEquals("error no args", TestConsoleAppender.EVENTS.get(0).getFormattedMessage());
    assertEquals("error 1 arg one", TestConsoleAppender.EVENTS.get(1).getFormattedMessage());
    assertEquals("error 2 args one two", TestConsoleAppender.EVENTS.get(2).getFormattedMessage());
    assertEquals("error 2 args one two", TestConsoleAppender.EVENTS.get(3).getFormattedMessage());
    assertEquals("error 3 args one null null", TestConsoleAppender.EVENTS.get(4).getFormattedMessage());
    assertEquals("error 4 args one null null four", TestConsoleAppender.EVENTS.get(5).getFormattedMessage());
    assertEquals("error 5 args one null null four five", TestConsoleAppender.EVENTS.get(6).getFormattedMessage());
    assertEquals("error 6 args one null null four five six", TestConsoleAppender.EVENTS.get(7).getFormattedMessage());
    assertEquals("error null varargs", TestConsoleAppender.EVENTS.get(8).getFormattedMessage());
    assertEquals("error 2 args with exception one two", TestConsoleAppender.EVENTS.get(9).getFormattedMessage());
  }

}
