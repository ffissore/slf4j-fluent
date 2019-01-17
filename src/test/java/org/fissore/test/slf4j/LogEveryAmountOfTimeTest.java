package org.fissore.test.slf4j;

import org.fissore.slf4j.FluentLogger;
import org.fissore.slf4j.FluentLoggerFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class LogEveryAmountOfTimeTest {

  @Before
  public void setUp() {
    TestListAppender.init();
    TestConsoleAppender.init();
  }

  @Test
  public void everyOneSecond() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.error.Test");

    for (int i = 0; i < 60; i++) {
      logger.error().every(1, ChronoUnit.SECONDS).log("error");
      sleep(50);
    }

    assertEquals(3, TestConsoleAppender.EVENTS.size());
  }

  @Test
  public void everyHalfSecond() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.error.Test");

    for (int i = 0; i < 60; i++) {
      logger.error().every(500, ChronoUnit.MILLIS).log("error");
      sleep(50);
    }

    assertEquals(6, TestConsoleAppender.EVENTS.size());
  }

  @Test
  public void everyConcurrent() throws Exception {
    FluentLogger logger1 = FluentLoggerFactory.getLogger("org.fissore.slf4j.list.error.Test1");
    FluentLogger logger2 = FluentLoggerFactory.getLogger("org.fissore.slf4j.list.error.Test2");

    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 60; i++) {
        logger1.error().every(1, ChronoUnit.SECONDS).log("error");
        sleep(50);
      }
    });

    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 60; i++) {
        logger2.error().every(500, ChronoUnit.MILLIS).log("error");
        sleep(50);
      }
    });

    t1.start();
    t2.start();
    t1.join();
    t2.join();

    assertEquals(9, TestListAppender.EVENTS.size());
  }

  private static void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
