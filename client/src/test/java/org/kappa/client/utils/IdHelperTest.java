package org.kappa.client.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


class IdHelperTest {

  @Test
  void testCreateRandomUuid_ShouldReturnUuid() {
    final var uuid = IdHelper.createRandomUuid();

    assertFalse(uuid.isEmpty());
    assertEquals(36, uuid.length());
  }

}