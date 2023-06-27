package org.kappa.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// nanosecond = 1_000_000_000 of second
public class Time {

  private static final Logger LOGGER = LoggerFactory.getLogger(Time.class);

  private final int loopInterval;
  private long lastUpdate;


  public Time(final int loopInterval, final long lastUpdate) {
    this.loopInterval = loopInterval;
    this.lastUpdate = lastUpdate;
  }


  public void update(final long now) {
    this.lastUpdate = now;
  }


  public boolean isInInterval() {
    return this.isInInterval(this.loopInterval);
  }


  public boolean isInInterval(final int interval) {
    return Math.round(this.getElapsedTimeInMilliseconds() / 1000D) * 1000 % interval == 0;
  }


  public long getElapsedTimeInMilliseconds() {
    return convertToMilliseconds(this.getTimeElapsed());
  }


  private static long convertToMilliseconds(final long nanoseconds) {
    return Math.max(0L, Math.round(nanoseconds / 1_000_000D));
  }


  public long getElapsedTimeInSeconds() {
    return convertToSeconds(this.getTimeElapsed());
  }


  private static long convertToSeconds(final long nanoseconds) {
    return Math.max(0L, Math.round(nanoseconds / 1_000_000_000D));
  }


  private long getTimeElapsed() {
    return 0; // this.now - this.lastUpdate;
  }


  public long getLoopInterval() {
    return this.loopInterval;
  }


  public long getLastUpdate() {
    return this.lastUpdate;
  }

}
