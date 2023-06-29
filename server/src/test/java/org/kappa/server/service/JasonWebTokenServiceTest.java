package org.kappa.server.service;

import ch.qos.logback.classic.spi.EventArgUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;


class JasonWebTokenServiceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(JasonWebTokenServiceTest.class);

  @Test
  void testService() {
    final var jwtService = new JasonWebTokenService();

    final var token = jwtService.createWebToken();
    LOGGER.error(token);

    assertNotNull(token);
  }
}