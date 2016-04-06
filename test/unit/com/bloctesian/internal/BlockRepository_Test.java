/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.internal;

import static helper.Lists.list;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bloctesian.Block;
import com.bloctesian.BlockId;
import com.bloctesian.BlockType;
import com.bloctesian.Rotation;
import com.bloctesian.Vector;

public class BlockRepository_Test {
  private final BlockFactory factory = mock(BlockFactory.class);
  private final BlockRepository testee = new BlockRepository(factory);
  private final BlockId id1 = new BlockId(BlockType.Block4x2, new Vector(0, 0, 1), Rotation.Deg0);
  private final BlockId id2 = new BlockId(BlockType.Block2x2, new Vector(1, 0, 0), Rotation.Deg90);
  private final Block block1 = new Block(id1);
  private final Block block2 = new Block(id2);

  @Test
  public void is_empty_by_default() {
    assertFalse(testee.iterator().hasNext());
    assertEquals(0, testee.items().size());
  }

  @Test
  public void can_iterate_over_items() {
    when(factory.produce(any(BlockId.class))).thenReturn(block1).thenReturn(block2);
    testee.blocks(list(id1, id2));

    List<Block> blocks = new ArrayList<>();
    for (Block block : testee) {
      blocks.add(block);
    }

    assertEquals(list(block1, block2), blocks);
  }

  @Test
  public void blocks_are_in_order_when_adding() {
    when(factory.produce(any(BlockId.class))).thenReturn(block1).thenReturn(block2);

    testee.blocks(list(id1, id2));

    assertEquals(list(block1, block2), testee.items());
  }

  @Test
  public void blocks_are_in_the_order_as_newly_added() {
    when(factory.produce(any(BlockId.class))).thenReturn(block1).thenReturn(block2);
    testee.blocks(list(id1, id2));

    testee.blocks(list(id2, id1));

    assertEquals(list(block2, block1), testee.items());
  }

  @Test
  public void blocks_are_removed_when_no_longer_available() {
    when(factory.produce(any(BlockId.class))).thenReturn(block1).thenReturn(block2);
    testee.blocks(list(id1, id2));

    testee.blocks(list(id1));

    assertEquals(list(block1), testee.items());
  }
}
