package org.kappa.client.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TimerTest {

  @Test
  void testTime() {
    final var start = 10_000_000_000L;
    final var timer = new Timer(start, 10);

    timer.update(start + 59_000_000L);
    assertEquals(1, timer.getRound());

    timer.update(start + 59_000_000L + 59_000_000L);
    assertEquals(2, timer.getRound());
  }

}