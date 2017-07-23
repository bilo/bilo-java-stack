/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack;

public enum Color {
  Black, Red, Green, Yellow, Blue, Magenta, Cyan, White;

  public int bitfield() {
    return ordinal();
  }

  public boolean red() {
    return bit(0);
  }

  public boolean green() {
    return bit(1);
  }

  public boolean blue() {
    return bit(2);
  }

  private boolean bit(int nr) {
    return ((bitfield() >> nr) & 1) == 1;
  }

  public static Color produce(boolean red, boolean green, boolean blue) {
    int bits = (red ? 1 : 0) | (green ? 2 : 0) | (blue ? 4 : 0);

    return Color.values()[bits];
  }

}
