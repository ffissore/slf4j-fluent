package org.fissore.test.slf4j;

import org.fissore.slf4j.FluentLogger;
import org.fissore.slf4j.FluentLoggerFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LogEveryNumberOfCallsTest {

  @Before
  public void setUp() {
    TestListAppender.init();
    TestConsoleAppender.init();
  }

  @Test
  public void logEveryNumberOfCalls() {
    FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.error.Test");

    for (int i = 0; i < 13; i++) {
      logger.error().every(5).log("error");
    }

    assertEquals(3, TestConsoleAppender.EVENTS.size());
  }

  @Test
  public void logEveryNumberOfCallsConcurrent() throws Exception {
    int iterations = 10000;
    int t1Every = 5;
    int t2Every = 7;

    FluentLogger logger1 = FluentLoggerFactory.getLogger("org.fissore.slf4j.list.error.Test1");
    FluentLogger logger2 = FluentLoggerFactory.getLogger("org.fissore.slf4j.list.error.Test2");

    Thread t1 = new Thread(() -> {
      for (int i = 0; i < iterations; i++) {
        logger1.error().every(t1Every).log("error");
      }
    });

    Thread t2 = new Thread(() -> {
      for (int i = 0; i < iterations; i++) {
        logger2.error().every(t2Every).log("error");
      }
    });

    t1.start();
    t2.start();
    t1.join();
    t2.join();

    assertEquals((1 + iterations / t1Every) + (1 + iterations / t2Every), TestListAppender.EVENTS.size());
  }
}
