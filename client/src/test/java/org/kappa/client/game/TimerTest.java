package org.kappa.client.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TimerTest {

  @Test
  void testTime() {
    final var start = 1_000_000_000L;
    final var timer = new Timer(10, start);

    timer.update(1_159_000_000L);
    assertEquals(1, timer.getRound());
  }

}