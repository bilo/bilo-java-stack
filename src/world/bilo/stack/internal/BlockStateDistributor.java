/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.internal;

import java.util.List;

import world.bilo.stack.BlockId;
import world.bilo.util.UniqueOrderedList;

public class BlockStateDistributor implements BlockStateListener {
  private final UniqueOrderedList<BlockStateListener> listener = new UniqueOrderedList<>();

  public UniqueOrderedList<BlockStateListener> getListener() {
    return listener;
  }

  @Override
  public void blocks(List<BlockId> blocks) {
    for (BlockStateListener itr : listener) {
      itr.blocks(blocks);
    }
  }

}
