package org.kappa.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// nanosecond = 1_000_000_000 of second
public class Timer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Timer.class);
  private static final Long FRACTAL = 100_000_000L;

  private final int updateInterval;
  private long lapTime;
  private int round;


  public Timer(final int updateInterval, final long lapTime) {
    this.updateInterval = updateInterval;
    this.lapTime = lapTime;
  }


  public boolean update(final long now) {
    if ((now - this.lapTime) >= this.round * FRACTAL) {
      this.round++;

      if (this.round > 10) {
        this.round = 1;
        this.lapTime = now;
      }

      return true;
    }

    return false;
  }


  public int getUpdateInterval() {
    return this.updateInterval;
  }


  public long getLapTime() {
    return this.lapTime;
  }


  public int getRound() {
    return this.round;
  }


  @Deprecated
  private static long convertToMilliseconds(final long nanoseconds) {
    return Math.max(0L, Math.round(nanoseconds / 1_000_000D));
  }


  @Deprecated
  private static long convertToSeconds(final long nanoseconds) {
    return Math.max(0L, Math.round(nanoseconds / 1_000_000_000D));
  }

}
