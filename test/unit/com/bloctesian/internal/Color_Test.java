/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bloctesian.Color;

public class Color_Test {
  @Test
  public void can_get_bits_for_colors() {
    assertEquals(0x0, Color.Black.bitfield());
    assertEquals(0x1, Color.Red.bitfield());
    assertEquals(0x2, Color.Green.bitfield());
    assertEquals(0x3, Color.Yellow.bitfield());
    assertEquals(0x4, Color.Blue.bitfield());
    assertEquals(0x5, Color.Magenta.bitfield());
    assertEquals(0x6, Color.Cyan.bitfield());
    assertEquals(0x7, Color.White.bitfield());
  }

  @Test
  public void can_retrieve_red_component() {
    assertEquals(false, Color.Black.red());
    assertEquals(true, Color.Red.red());
    assertEquals(false, Color.Green.red());
    assertEquals(true, Color.Yellow.red());
    assertEquals(false, Color.Blue.red());
    assertEquals(true, Color.Magenta.red());
    assertEquals(false, Color.Cyan.red());
    assertEquals(true, Color.White.red());
  }

  @Test
  public void can_retrieve_green_component() {
    assertEquals(false, Color.Black.green());
    assertEquals(false, Color.Red.green());
    assertEquals(true, Color.Green.green());
    assertEquals(true, Color.Yellow.green());
    assertEquals(false, Color.Blue.green());
    assertEquals(false, Color.Magenta.green());
    assertEquals(true, Color.Cyan.green());
    assertEquals(true, Color.White.green());
  }

  @Test
  public void can_retrieve_blue_component() {
    assertEquals(false, Color.Black.blue());
    assertEquals(false, Color.Red.blue());
    assertEquals(false, Color.Green.blue());
    assertEquals(false, Color.Yellow.blue());
    assertEquals(true, Color.Blue.blue());
    assertEquals(true, Color.Magenta.blue());
    assertEquals(true, Color.Cyan.blue());
    assertEquals(true, Color.White.blue());
  }

  @Test
  public void can_create_color_by_components() {
    assertEquals(Color.Black, Color.produce(false, false, false));
    assertEquals(Color.Red, Color.produce(true, false, false));
    assertEquals(Color.Green, Color.produce(false, true, false));
    assertEquals(Color.Yellow, Color.produce(true, true, false));
    assertEquals(Color.Blue, Color.produce(false, false, true));
    assertEquals(Color.Magenta, Color.produce(true, false, true));
    assertEquals(Color.Cyan, Color.produce(false, true, true));
    assertEquals(Color.White, Color.produce(true, true, true));
  }

}
