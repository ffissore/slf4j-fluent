package org.fissore.slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoggerStats {

  private final ConcurrentHashMap<String, Instant> timestamps;
  private final ConcurrentHashMap<String, Long> counts;

  public LoggerStats() {
    timestamps = new ConcurrentHashMap<>();
    counts = new ConcurrentHashMap<>();
  }

  public boolean recordCallAndCheckIfEnoughTimePassed(String caller, long amount, ChronoUnit unit) {
    Instant now = Instant.now();

    AtomicBoolean result = new AtomicBoolean(true);
    timestamps.merge(caller, now, (previousTimestamp, _v) -> {
      if (previousTimestamp.plus(amount, unit).isBefore(now)) {
        return now;
      }
      result.set(false);
      return previousTimestamp;
    });

    return result.get();
  }

  public boolean recordCallThenCheckIfNumberOfCallsMatchesAmount(String caller, int amount) {
    Long countedCalls = counts.merge(caller, 1L, (currentCounts, _v) -> currentCounts + 1);
    return countedCalls == 1 || countedCalls % amount == 0;
  }

}
