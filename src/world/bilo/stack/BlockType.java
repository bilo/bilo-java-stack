/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack;

public enum BlockType {
  Block4x2, Block2x2, Base10x10;

  @Override
  public String toString() {
    switch (this) {
    case Block4x2:
      return "4*2";
    case Block2x2:
      return "2*2";
    case Base10x10:
      return "10*10";
    default:
      throw new RuntimeException("unknown block type name for " + this.ordinal());
    }
  }

  public int xSize() {
    switch (this) {
    case Block4x2:
      return 4;
    case Block2x2:
      return 2;
    case Base10x10:
      return 10;
    default:
      throw new RuntimeException("unknown block type " + this.ordinal());
    }
  }

  public int ySize() {
    switch (this) {
    case Block4x2:
      return 2;
    case Block2x2:
      return 2;
    case Base10x10:
      return 10;
    default:
      throw new RuntimeException("unknown block type " + this.ordinal());
    }
  }

}
