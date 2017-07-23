/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message;

public class ByteConvertion {

  public static byte toByte(int value) {
    return (byte) value;
  }

  public static int toInt(byte value) {
    return value;
  }

  public static int toUint(byte value) {
    int positive = 0x100 + value;
    return positive & 0xff;
  }

  public static int toShort(byte higher, byte lower) {
    int result = toUint(higher);
    result <<= 8;
    result |= toUint(lower);
    if ((result & 0x8000) != 0) {
      result = (result & 0x7fff) - 0x8000;
    }
    return result;
  }

}
