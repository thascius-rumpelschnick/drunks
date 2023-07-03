package org.kappa.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;


// nanosecond = 1_000_000_000 of second
public class Timer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Timer.class);
  private static final Long FRACTAL = 100_000_000L;

  private final int updateInterval;
  private final LocalTime startTime;
  private final LocalTime lapTime;
  private int round;


  public Timer(final long startTime, final int updateInterval) {
    this.startTime = LocalTime.ofNanoOfDay(startTime);
    this.lapTime = LocalTime.from(this.startTime);
    this.updateInterval = updateInterval;
  }


  public boolean update(final long now) {
    final var t = LocalTime.ofNanoOfDay(now);

    return (this.lapTime.getNano() - t.getNano() / 10_000_000) > 5000;

    // if ((now - this.lapTime) >= this.round * FRACTAL) {
    //   this.round++;
    //
    //   if (this.round > 10) {
    //     this.round = 1;
    //     this.lapTime = now;
    //   }
    //
    //   return true;
    // }
  }


  public int getUpdateInterval() {
    return this.updateInterval;
  }


  public LocalTime getLapTime() {
    return this.lapTime;
  }


  public int getRound() {
    return this.round;
  }


  // public long getStartTimeInSeconds() {
  //   return convertToSeconds(this.startTime);
  // }


  @Deprecated
  private static long convertToMilliseconds(final long nanoseconds) {
    return Math.max(0L, Math.round(nanoseconds / 1_000_000D));
  }


  @Deprecated
  private static long convertToSeconds(final long nanoseconds) {
    return Math.max(0L, Math.round(nanoseconds / 1_000_000_000D));
  }

}
