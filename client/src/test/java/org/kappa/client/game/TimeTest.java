package org.kappa.client.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TimeTest {

  @Test
  void testTime() {
    final var start = 1_000_000_000L;
    final var interval = 500;
    final var time = new Time(interval, start);

    assertEquals(0, time.getElapsedTimeInMilliseconds());

    time.update(start * 2);
    assertEquals(1000, time.getElapsedTimeInMilliseconds());

    time.update(1_555_000_000L);
    assertEquals(555, time.getElapsedTimeInMilliseconds());

    time.update(1_549_000_000L);
    assertTrue(time.isInInterval());

    time.update(2_449_000_000L);
    assertTrue(time.isInInterval(250));

    time.update(2_449_000_000L);
    assertTrue(time.isInInterval(1000));

    time.update(2_449_000_000L);
    assertFalse(time.isInInterval(600));
  }

}