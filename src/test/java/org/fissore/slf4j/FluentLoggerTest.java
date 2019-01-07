package org.fissore.slf4j;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.fissore.slf4j.Util.lazy;
import static org.junit.Assert.assertEquals;

public class FluentLoggerTest {

    @Before
    public void setUp() {
        TestLogbackAppender.EVENTS.clear();
    }

    @Test
    public void errorLogger() {
        FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.error.Test");

        tryLoggingAtAllLevels(logger);

        assertEquals(5, TestLogbackAppender.EVENTS.size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.ERROR_INT).size());
    }

    @Test
    public void warnLogger() {
        FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.warn.Test");

        tryLoggingAtAllLevels(logger);

        assertEquals(10, TestLogbackAppender.EVENTS.size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.ERROR_INT).size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.WARN_INT).size());
    }

    @Test
    public void infoLogger() {
        FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.info.Test");

        tryLoggingAtAllLevels(logger);

        assertEquals(15, TestLogbackAppender.EVENTS.size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.ERROR_INT).size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.WARN_INT).size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.INFO_INT).size());
    }

    @Test
    public void debugLogger() {
        FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.debug.Test");

        tryLoggingAtAllLevels(logger);

        assertEquals(20, TestLogbackAppender.EVENTS.size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.ERROR_INT).size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.WARN_INT).size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.INFO_INT).size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.DEBUG_INT).size());
    }

    @Test
    public void traceLogger() {
        FluentLogger logger = FluentLoggerFactory.getLogger("org.fissore.slf4j.trace.Test");

        tryLoggingAtAllLevels(logger);

        assertEquals(25, TestLogbackAppender.EVENTS.size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.ERROR_INT).size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.WARN_INT).size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.INFO_INT).size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.DEBUG_INT).size());
        assertEquals(5, loggedEventsOfLevel(TestLogbackAppender.EVENTS, Level.TRACE_INT).size());
    }

    private List<ILoggingEvent> loggedEventsOfLevel(List<ILoggingEvent> events, int targetLevelInt) {
        return events.stream()
            .filter(event -> event.getLevel().levelInt == targetLevelInt)
            .collect(Collectors.toList());
    }

    private void tryLoggingAtAllLevels(FluentLogger logger) {
        logger.atError().log("error no args");
        logger.atError().log("error 1 arg {}", "one");
        logger.atError().log("error 2 args {} {}", "one", lazy(() -> "two"));
        logger.atError().log("error 3 args {} {} {}", "one", lazy(() -> "two"), "three");
        logger.atError().withCause(new Exception()).log("error 2 args with exception {} {}", "one", lazy(() -> "two"));

        logger.atWarn().log("warn no args");
        logger.atWarn().log("warn 1 arg {}", "one");
        logger.atWarn().log("warn 2 args {} {}", "one", lazy(() -> "two"));
        logger.atWarn().log("warn 3 args {} {} {}", "one", lazy(() -> "two"), "three");
        logger.atWarn().withCause(new Exception()).log("warn 2 args with exception {} {}", "one", lazy(() -> "two"));

        logger.atInfo().log("info no args");
        logger.atInfo().log("info 1 arg {}", "one");
        logger.atInfo().log("info 2 args {} {}", "one", lazy(() -> "two"));
        logger.atInfo().log("info 3 args {} {} {}", "one", lazy(() -> "two"), "three");
        logger.atInfo().withCause(new Exception()).log("info 2 args with exception {} {}", "one", lazy(() -> "two"));

        logger.atDebug().log("debug no args");
        logger.atDebug().log("debug 1 arg {}", "one");
        logger.atDebug().log("debug 2 args {} {}", "one", lazy(() -> "two"));
        logger.atDebug().log("debug 3 args {} {} {}", "one", lazy(() -> "two"), "three");
        logger.atDebug().withCause(new Exception()).log("debug 2 args with exception {} {}", "one", lazy(() -> "two"));

        logger.atTrace().log("trace no args");
        logger.atTrace().log("trace 1 arg {}", "one");
        logger.atTrace().log("trace 2 args {} {}", "one", lazy(() -> "two"));
        logger.atTrace().log("trace 3 args {} {} {}", "one", lazy(() -> "two"), "three");
        logger.atTrace().withCause(new Exception()).log("trace 2 args with exception {} {}", "one", lazy(() -> "two"));
    }
}
