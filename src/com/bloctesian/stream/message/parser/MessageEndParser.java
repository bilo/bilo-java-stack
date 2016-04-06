/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static com.bloctesian.stream.message.Constants.END_MESSAGE;

import java.util.List;

import com.bloctesian.BlockId;
import com.bloctesian.internal.BlockStateListener;

class MessageEndParser implements PartParser {
  final private List<BlockId> blocs;
  final private BlockStateListener blockListener;
  private boolean finished = true;

  public MessageEndParser(List<BlockId> blocs, BlockStateListener blockListener) {
    this.blocs = blocs;
    this.blockListener = blockListener;
  }

  @Override
  public boolean canStartWith(byte symbol) {
    return symbol == END_MESSAGE;
  }

  @Override
  public void reset() {
    finished = false;
  }

  @Override
  public void receive(byte symbol) {
    if (symbol != END_MESSAGE) {
      throw new RuntimeException("Expected end message");
    }
    finished = true;
    blockListener.blocks(blocs);
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

}