/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.internal;

import java.util.List;

import com.bloctesian.BlockId;
import com.bloctesian.utility.UniqueOrderedList;

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
