/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

import static world.bilo.stack.stream.message.Constants.START_MESSAGE;

import java.util.List;

import world.bilo.stack.BlockId;

class MessageStartParser implements PartParser {
  final private List<BlockId> blocs;

  private boolean finished = false;

  public MessageStartParser(List<BlockId> blocs) {
    super();
    this.blocs = blocs;
  }

  @Override
  public boolean canStartWith(byte symbol) {
    return symbol == START_MESSAGE;
  }

  @Override
  public void reset() {
    finished = false;
  }

  @Override
  public void receive(byte symbol) {
    if (symbol != START_MESSAGE) {
      throw new RuntimeException("Expected start message");
    }
    blocs.clear();
    finished = true;
  }

  @Override
  public boolean isFinished() {
    return finished;
  }

}