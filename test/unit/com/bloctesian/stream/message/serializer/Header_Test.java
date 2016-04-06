/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.serializer;

import static helper.Lists.list;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Header_Test {
  private final Header testee = new Header();

  @Test
  public void header_is_one_specific_byte() {
    assertEquals(list(0x80), testee.serialize());
  }
}
