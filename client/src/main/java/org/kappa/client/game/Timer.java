package org.kappa.client.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;


// nanosecond = 1_000_000_000 of second
public class Timer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Timer.class);

  private final Random random = new Random();

  private final int updateInterval;
  private long startTime;
  private long lapTime;
  private int round = 1;


  public Timer(final int updateInterval) {
    this.updateInterval = updateInterval;
  }


  public boolean update(final long now) {
    final var nowInMillis = convertToMilliseconds(now);

    if (this.startTime == 0) {
      this.startTime = this.lapTime = nowInMillis;
    }

    // Update game board every 100 milliseconds
    if (nowInMillis - this.lapTime >= 100) {
      this.lapTime = nowInMillis;
      this.round++;

      if (this.round == 10) {
        this.round = 1;
      }

      return true;
    }

    return false;
  }


  public boolean canAct(final int updateInterval) {
    return this.random.nextInt(updateInterval) == (updateInterval - 1);
  }


  public int getRound() {
    return this.round;
  }


  private static long convertToMilliseconds(final long nanoseconds) {
    return Math.max(0L, Math.round(nanoseconds / 1_000_000D));
  }


  @Deprecated
  private static long convertToSeconds(final long nanoseconds) {
    return Math.max(0L, Math.round(nanoseconds / 1_000_000_000D));
  }

}
