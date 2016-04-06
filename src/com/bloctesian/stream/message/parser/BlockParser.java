/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static com.bloctesian.stream.message.ByteConvertion.toInt;

import java.util.List;

import com.bloctesian.BlockId;
import com.bloctesian.BlockType;
import com.bloctesian.Rotation;
import com.bloctesian.Vector;

public class BlockParser implements PartParser {
  final private List<BlockId> blocs;

  public BlockParser(List<BlockId> blocs) {
    this.blocs = blocs;
    reset();
  }

  private enum State {
    WaitBlockX, WaitBlockY, WaitBlockZ, WaitBlockRotation, Finished
  }

  private State state;
  private byte blocX;
  private byte blocY;
  private byte blocZ;

  @Override
  public void reset() {
    state = State.WaitBlockX;
  }

  @Override
  public boolean isFinished() {
    return state == State.Finished;
  }

  @Override
  public void receive(byte symbol) {
    switch (state) {
    case WaitBlockX: {
      blocX = symbol;
      state = State.WaitBlockY;
      break;
    }
    case WaitBlockY: {
      blocY = symbol;
      state = State.WaitBlockZ;
      break;
    }
    case WaitBlockZ: {
      blocZ = symbol;
      state = State.WaitBlockRotation;
      break;
    }
    case WaitBlockRotation: {
      Rotation rotation = parseRotation((symbol >> 0) & 0x03);
      BlockType type = parseType((symbol >> 2) & 0x01);

      BlockId bloc = new BlockId(type, getPosition(), rotation);
      blocs.add(bloc);

      state = State.Finished;
      break;
    }
    case Finished: {
      // TODO raise error
      break;
    }
    }
  }

  private Vector getPosition() {
    return new Vector(toInt(blocX), toInt(blocY), toInt(blocZ));
  }

  private Rotation parseRotation(int value) {
    switch (value) {
    case 0x00:
      return Rotation.Deg0;
    case 0x01:
      return Rotation.Deg270;
    case 0x02:
      return Rotation.Deg180;
    case 0x03:
      return Rotation.Deg90;
    default:
      throw new RuntimeException("Unknown block type: " + value);
    }
  }

  private BlockType parseType(int value) {
    switch (value) {
    case 0x00:
      return BlockType.Block4x2;
    case 0x01:
      return BlockType.Block2x2;
    default:
      throw new RuntimeException("Unknown block type: " + value);
    }
  }

  @Override
  public boolean canStartWith(byte symbol) {
    return true;
  }

}
