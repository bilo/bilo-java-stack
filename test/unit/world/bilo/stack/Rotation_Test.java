/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Rotation_Test {
  @Test
  public void has_a_readable_toString_output() {
    assertEquals("0", Rotation.Deg0.toString());
    assertEquals("90", Rotation.Deg90.toString());
    assertEquals("180", Rotation.Deg180.toString());
    assertEquals("270", Rotation.Deg270.toString());
  }

  @Test
  public void can_convert_to_int() {
    assertEquals(0, Rotation.Deg0.toInt());
    assertEquals(90, Rotation.Deg90.toInt());
    assertEquals(180, Rotation.Deg180.toInt());
    assertEquals(270, Rotation.Deg270.toInt());
  }
}
