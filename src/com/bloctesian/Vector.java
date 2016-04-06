/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian;

public class Vector {
  public static final Vector Zero = new Vector(0, 0, 0);

  final public int x;
  final public int y;
  final public int z;

  public Vector(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector(Dimension dimension) {
    this.x = dimension == Dimension.X ? 1 : 0;
    this.y = dimension == Dimension.Y ? 1 : 0;
    this.z = dimension == Dimension.Z ? 1 : 0;
  }

  public Vector translate(Vector displacement) {
    return new Vector(this.x + displacement.x, this.y + displacement.y, this.z + displacement.z);
  }

  public Vector rotateOnZeroBy(Rotation rotation) {
    int times = rotation.toInt() / 90;
    return rotate90deg(this, times);
  }

  private static Vector rotate90deg(Vector value, int times) {
    for (int i = 0; i < times; i++) {
      value = rotate90deg(value);
    }
    return value;
  }

  private static Vector rotate90deg(Vector value) {
    return new Vector(-value.y, value.x, value.z);
  }

  @Override
  public String toString() {
    return "(" + x + "/" + y + "/" + z + ")";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
    result = prime * result + z;
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
    Vector other = (Vector) obj;
    if (x != other.x)
      return false;
    if (y != other.y)
      return false;
    if (z != other.z)
      return false;
    return true;
  }

}
