/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack;

public class BlockId {
  public final BlockType type;
  public final Vector position;
  public final Rotation rotation;

  public BlockId(BlockType type, Vector position, Rotation rotation) {
    this.type = type;
    this.position = position;
    this.rotation = rotation;
  }

  @Override
  public String toString() {
    return "BlockId{type:" + type + " position:" + position + " rotation:" + rotation + "}";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((position == null) ? 0 : position.hashCode());
    result = prime * result + ((rotation == null) ? 0 : rotation.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BlockId other = (BlockId) obj;
    if (position == null) {
      if (position != null)
        return false;
    } else if (!position.equals(other.position))
      return false;
    if (rotation != other.rotation)
      return false;
    if (type != other.type)
      return false;
    return true;
  }

}
