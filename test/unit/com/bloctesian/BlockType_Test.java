/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BlockType_Test {

  @Test
  public void has_a_readable_toString_output() {
    assertEquals("4*2", BlockType.Block4x2.toString());
    assertEquals("2*2", BlockType.Block2x2.toString());
    assertEquals("10*10", BlockType.Base10x10.toString());
  }

  @Test
  public void has_a_readable_toString_output_for_each_type() {
    for (BlockType type : BlockType.values()) {
      type.toString();
    }
  }

  @Test
  public void can_retreive_dimension() {
    assertEquals(4, BlockType.Block4x2.xSize());
    assertEquals(2, BlockType.Block4x2.ySize());

    assertEquals(2, BlockType.Block2x2.xSize());
    assertEquals(2, BlockType.Block2x2.ySize());

    assertEquals(10, BlockType.Base10x10.xSize());
    assertEquals(10, BlockType.Base10x10.ySize());
  }

}
