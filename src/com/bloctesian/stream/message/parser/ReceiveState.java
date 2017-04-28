/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import java.util.List;

import com.bloctesian.BlockId;
import com.bloctesian.internal.BaseError;
import com.bloctesian.internal.BlockStateListener;
import com.bloctesian.internal.ErrorListener;
import com.bloctesian.stream.message.serializer.SerializerSelector;

public class ReceiveState implements SerializerSelector, ErrorListener, BlockStateListener {
  private boolean normal = true;

  @Override
  public boolean isNormal() {
    return normal;
  }

  @Override
  public void error(BaseError error) {
    normal = false;
  }

  @Override
  public void unknownDataReceived(byte data) {
    normal = false;
  }

  public void timeout() {
    normal = false;
  }

  @Override
  public void blocks(List<BlockId> blocks) {
    normal = true;
  }

}
