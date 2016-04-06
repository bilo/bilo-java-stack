/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ByteConvertion_Test {

  @Test
  public void can_convert_positive_int_to_byte() {
    assertEquals(0, ByteConvertion.toByte(0));
    assertEquals(1, ByteConvertion.toByte(1));
    assertEquals(42, ByteConvertion.toByte(42));
    assertEquals(127, ByteConvertion.toByte(Byte.MAX_VALUE));
  }

  @Test
  public void can_convert_negative_int_to_byte() {
    assertEquals(0, ByteConvertion.toByte(0));
    assertEquals(-1, ByteConvertion.toByte(-1));
    assertEquals(-42, ByteConvertion.toByte(-42));
    assertEquals(-128, ByteConvertion.toByte(Byte.MIN_VALUE));
  }

  @Test
  public void can_convert_higher_positive_int_to_byte() {
    assertEquals(-1, ByteConvertion.toByte(0xff));
    assertEquals(-42, ByteConvertion.toByte(0xd6));
    assertEquals(Byte.MIN_VALUE, ByteConvertion.toByte(0x80));
  }

  @Test
  public void can_convert_positive_byte_to_int() {
    assertEquals(0, ByteConvertion.toInt((byte) 0));
    assertEquals(1, ByteConvertion.toInt((byte) 1));
    assertEquals(42, ByteConvertion.toInt((byte) 42));
    assertEquals(127, ByteConvertion.toInt(Byte.MAX_VALUE));
  }

  @Test
  public void can_convert_higher_positive_byte_to_int() {
    assertEquals(-1, ByteConvertion.toInt((byte) 0xff));
    assertEquals(-42, ByteConvertion.toInt((byte) 0xd6));
    assertEquals(Byte.MIN_VALUE, ByteConvertion.toInt((byte) 0x80));
  }

}
