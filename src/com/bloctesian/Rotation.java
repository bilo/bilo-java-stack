/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian;

/**
 * Mathematical positive, i.e. counterclockwise
 */
public enum Rotation {
  Deg0, Deg90, Deg180, Deg270;

  @Override
  public String toString() {
    return Integer.toString(this.toInt());
  }

  public int toInt() {
    switch (this) {
    case Deg0:
      return 0;
    case Deg90:
      return 90;
    case Deg180:
      return 180;
    case Deg270:
      return 270;
    default:
      throw new RuntimeException("unknown block type " + this.ordinal());
    }
  }
}
