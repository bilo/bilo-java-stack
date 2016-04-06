/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.serializer;

import static helper.Lists.list;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bloctesian.Block;
import com.bloctesian.BlockId;
import com.bloctesian.BlockType;
import com.bloctesian.Color;
import com.bloctesian.RgbLed;
import com.bloctesian.Rotation;
import com.bloctesian.Vector;

public class BlockSerializer_Test {
  private final Block block2x2 = new Block(new BlockId(BlockType.Block2x2, Vector.Zero, Rotation.Deg0));
  private final Block block4x2 = new Block(new BlockId(BlockType.Block4x2, Vector.Zero, Rotation.Deg0));
  private final Block base10x10 = new Block(new BlockId(BlockType.Base10x10, Vector.Zero, Rotation.Deg0));
  private final BlockSerializer testee = new BlockSerializer();

  @Test
  public void set_no_color_for_2x2_block() {
    setLeds(block2x2, Color.Black);

    assertEquals(list(0x00), testee.execute(block2x2));
  }

  @Test
  public void set_color_for_4x2_block() {
    setLeds(block4x2, Color.Black);

    assertEquals(list(0x00, 0x00), testee.execute(block4x2));
  }

  @Test
  public void set_color_for_10x10_base() {
    setLeds(base10x10, Color.Black);

    assertEquals(list(0x00, 0x00), testee.execute(base10x10));
  }

  @Test
  public void set_red_color_when_requested() {
    setLeds(block4x2, Color.Red);

    assertEquals(list(0x09, 0x09), testee.execute(block4x2));
  }

  @Test
  public void set_green_color_when_requested() {
    setLeds(block2x2, Color.Green);

    assertEquals(list(1 << (1 + 3) | 1 << (1 + 0)), testee.execute(block2x2));
  }

  @Test
  public void set_blue_color_when_requested() {
    setLeds(block4x2, Color.Blue);

    assertEquals(list(0x24, 0x24), testee.execute(block4x2));
  }

  @Test
  public void set_multy_color_when_requested() {
    setLeds(block4x2, Color.Magenta);

    assertEquals(list(0x2d, 0x2d), testee.execute(block4x2));
  }

  @Test
  public void can_set_leds_to_different_colors_for_2x2_block() {
    block2x2.getLeds().get(0).setColor(Color.Red);
    block2x2.getLeds().get(1).setColor(Color.Blue);

    assertEquals(list((0x01 << 3) | (0x04 << 0)), testee.execute(block2x2));
  }

  @Test
  public void can_set_leds_to_different_colors_for_4x2_block() {
    block4x2.getLeds().get(0).setColor(Color.Red);
    block4x2.getLeds().get(1).setColor(Color.Blue);
    block4x2.getLeds().get(2).setColor(Color.Green);
    block4x2.getLeds().get(3).setColor(Color.White);

    assertEquals(list((0x01 << 3) | (0x04 << 0), (0x02 << 3) | (0x07 << 0)), testee.execute(block4x2));
  }

  @Test
  public void can_set_leds_to_different_colors_for_10x10_base() {
    base10x10.getLeds().get(0).setColor(Color.Black);
    base10x10.getLeds().get(1).setColor(Color.Red);
    base10x10.getLeds().get(2).setColor(Color.Green);
    base10x10.getLeds().get(3).setColor(Color.Blue);

    assertEquals(list((0x00 << 3) | (0x01 << 0), (0x02 << 3) | (0x04 << 0)), testee.execute(base10x10));
  }

  private void setLeds(Block block, Color color) {
    for (RgbLed led : block.getLeds()) {
      led.setColor(color);
    }
  }
}
