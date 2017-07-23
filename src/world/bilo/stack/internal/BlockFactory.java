/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.internal;

import world.bilo.stack.Block;
import world.bilo.stack.BlockId;

public class BlockFactory {
  public Block produce(BlockId id) {
    return new Block(id);
  }

}
