/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.internal;

import java.util.List;

import com.bloctesian.BlockId;

public interface BlockStateListener {
  public void blocks(List<BlockId> blocks);
}
