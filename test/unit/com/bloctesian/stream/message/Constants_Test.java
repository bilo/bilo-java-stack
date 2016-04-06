/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message;

import static com.bloctesian.stream.message.ByteConvertion.toByte;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Constants_Test {

  @Test
  public void has_start_message() {
    assertEquals(toByte(0x80), Constants.START_MESSAGE);
  }

  @Test
  public void has_end_message() {
    assertEquals(toByte(0x81), Constants.END_MESSAGE);
  }

  @Test
  public void has_start_compass() {
    assertEquals(toByte(0x82), Constants.START_COMPASS);
  }

}
