package org.fissore.slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

public class LoggerStats {

  private final ConcurrentHashMap<String, Instant> timestamps;
  private final ConcurrentHashMap<String, Long> counts;

  public LoggerStats() {
    timestamps = new ConcurrentHashMap<>();
    counts = new ConcurrentHashMap<>();
  }

  public boolean hasEnoughTimePassed(String caller, long amount, ChronoUnit unit) {
    Instant timestamp = timestamps.get(caller);
    if (timestamp == null) {
      return true;
    }

    return timestamp.plus(amount, unit).isBefore(Instant.now());
  }

  public void recordTimestamp(String caller) {
    timestamps.put(caller, Instant.now());
  }

  public boolean numberOfCallsEquals(String caller, int amount) {
    Long countedCalls = counts.get(caller);
    return countedCalls != null && countedCalls == amount;
  }

  public void recordCall(String caller) {
    counts.merge(caller, 1L, (currentCounts, _v) -> currentCounts + 1);
  }

  public void resetCount(String caller) {
    counts.remove(caller);
  }
}
