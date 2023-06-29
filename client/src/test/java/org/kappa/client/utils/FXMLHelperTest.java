package org.kappa.client.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


class FXMLHelperTest {

  @Test
  void testValidateLayoutPosition_givenWrongPositionXThrowsIllegalArgumentException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FXMLHelper.validateLayoutPosition(65, 65),
        "[X] not on grid: " + 65
    );
  }


  @Test
  void testValidateLayoutPosition_givenWrongPositionYThrowsIllegalArgumentException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> FXMLHelper.validateLayoutPosition(64, 65),
        "[Y] not on grid: " + 65
    );
  }

}