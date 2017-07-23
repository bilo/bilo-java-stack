/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Block_Test {
  @Test
  public void the_2x2_block_has_2_leds() {
    Block testee = new Block(new BlockId(BlockType.Block2x2, Vector.Zero, Rotation.Deg0));

    assertEquals(2, testee.getLeds().size());
  }

  @Test
  public void the_4x2_block_has_4_leds() {
    Block testee = new Block(new BlockId(BlockType.Block4x2, Vector.Zero, Rotation.Deg0));

    assertEquals(4, testee.getLeds().size());
  }

  @Test
  public void the_10x10_base_has_4_leds() {
    Block testee = new Block(new BlockId(BlockType.Base10x10, Vector.Zero, Rotation.Deg0));

    assertEquals(4, testee.getLeds().size());
  }

}
