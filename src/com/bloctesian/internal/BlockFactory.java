/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.internal;

import com.bloctesian.Block;
import com.bloctesian.BlockId;

public class BlockFactory {
  public Block produce(BlockId id) {
    return new Block(id);
  }

}
