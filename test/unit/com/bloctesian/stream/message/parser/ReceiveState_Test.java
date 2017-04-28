/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bloctesian.internal.BaseError;

public class ReceiveState_Test {
  private final ReceiveState testee = new ReceiveState();

  @Test
  public void is_ok_by_default() {

    assertTrue(testee.isNormal());
  }

  @Test
  public void is_in_error_after_receiving_an_error() {

    testee.error(BaseError.UartUnknownData);

    assertFalse(testee.isNormal());
  }

  @Test
  public void is_in_error_after_receiving_unknown_data() {

    testee.unknownDataReceived((byte) 0);

    assertFalse(testee.isNormal());
  }

  @Test
  public void is_in_error_after_timeout() {

    testee.timeout();

    assertFalse(testee.isNormal());
  }

  @Test
  public void is_ok_after_receiving_a_valid_message() {
    testee.unknownDataReceived((byte) 0);

    testee.blocks(null);

    assertTrue(testee.isNormal());
  }

}
