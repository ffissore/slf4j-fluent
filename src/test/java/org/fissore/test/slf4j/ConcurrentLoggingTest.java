package org.fissore.test.slf4j;

import org.fissore.slf4j.FluentLogger;
import org.fissore.slf4j.FluentLoggerFactory;
import org.junit.Before;
import org.junit.Test;

import static org.fissore.slf4j.Util.lazy;
import static org.junit.Assert.assertEquals;

public class ConcurrentLoggingTest {

  @Before
  public void setUp() {
    TestListAppender.init();
  }

  private static class Exception1 extends Exception {
  }

  private static class Exception2 extends Exception {
  }

  private static class Exception3 extends Exception {
  }

  private static class Exception4 extends Exception {
  }

  @Test
  public void concurrentLogging() throws Exception {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.list.error.Test");

    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        logger.error().withCause(new Exception1()).log("error {}", lazy(() -> "message"));
      }
    });

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        logger.error().withCause(new Exception2()).log("error {}", lazy(() -> "message"));
      }
    });

    Thread thread3 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        logger.error().withCause(new Exception3()).log("error {}", lazy(() -> "message"));
      }
    });

    Thread thread4 = new Thread(() -> {
      for (int i = 0; i < 10000; i++) {
        logger.error().withCause(new Exception4()).log("error {}", lazy(() -> "message"));
      }
    });

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();

    thread1.join();
    thread2.join();
    thread3.join();
    thread4.join();

    assertEquals(10000, TestListAppender.EVENTS.stream()
      .filter(event -> event.getThrowableProxy().getClassName().equals(Exception1.class.getName()))
      .count());

    assertEquals(10000, TestListAppender.EVENTS.stream()
      .filter(event -> event.getThrowableProxy().getClassName().equals(Exception2.class.getName()))
      .count());

    assertEquals(10000, TestListAppender.EVENTS.stream()
      .filter(event -> event.getThrowableProxy().getClassName().equals(Exception3.class.getName()))
      .count());

    assertEquals(10000, TestListAppender.EVENTS.stream()
      .filter(event -> event.getThrowableProxy().getClassName().equals(Exception4.class.getName()))
      .count());
  }
}
