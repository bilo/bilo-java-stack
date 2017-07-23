/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import world.bilo.stack.Block;
import world.bilo.stack.BlockId;

public class BlockRepository implements Iterable<Block>, BlockStateListener {
  private final BlockFactory factory;
  private List<Block> blocks = new LinkedList<>();

  public BlockRepository(BlockFactory factory) {
    this.factory = factory;
  }

  @Override
  public void blocks(List<BlockId> valid) {
    List<Block> oldBlocks = blocks;
    blocks = new LinkedList<>();

    for (BlockId id : valid) {
      Block block = find(id, oldBlocks);
      if (block == null) {
        block = factory.produce(id);
      }

      blocks.add(block);
    }
  }

  private Block find(BlockId id, Collection<Block> blocks) {
    for (Block block : blocks) {
      if (block.getId().equals(id)) {
        return block;
      }
    }
    return null;
  }

  @Override
  public Iterator<Block> iterator() {
    return blocks.iterator();
  }

  public List<Block> items() {
    return new ArrayList<Block>(blocks);
  }

}
