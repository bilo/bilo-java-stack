/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.internal;

import java.util.List;

import world.bilo.stack.BlockId;

public interface BlockStateListener {
  public void blocks(List<BlockId> blocks);
}
