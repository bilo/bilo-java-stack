/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

import java.util.List;

import world.bilo.stack.BlockId;
import world.bilo.stack.internal.BaseError;
import world.bilo.stack.internal.BlockStateListener;
import world.bilo.stack.internal.ErrorListener;
import world.bilo.stack.stream.message.serializer.SerializerSelector;

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
