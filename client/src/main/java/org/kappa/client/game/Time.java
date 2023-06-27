package org.kappa.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// nanosecond = 1_000_000_000 of second
public class Time {

  private static final Logger LOGGER = LoggerFactory.getLogger(Time.class);

  private final long startTime;
  private final int loopInterval;
  private long now;


  public Time(final long startTime, final int loopInterval) {
    this.startTime = startTime;
    this.loopInterval = loopInterval;
  }


  public void update(final long now) {
    this.now = now;
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
    return this.now - this.startTime;
  }


  public long getLoopInterval() {
    return this.loopInterval;
  }


  public long getStartTime() {
    return this.startTime;
  }


  public long getNow() {
    return this.now;
  }
}
