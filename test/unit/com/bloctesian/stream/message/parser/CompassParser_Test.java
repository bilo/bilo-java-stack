/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static com.bloctesian.stream.message.ByteConvertion.toByte;
import static com.bloctesian.stream.message.Constants.START_COMPASS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompassParser_Test {
  final private CompassParser testee = new CompassParser();

  @Test
  public void can_start_with_start_compass() {
    assertEquals(true, testee.canStartWith(START_COMPASS));
  }

  @Test
  public void can_not_start_with_other_symbols() {
    assertEquals(false, testee.canStartWith(toByte(0x00)));
    assertEquals(false, testee.canStartWith(toByte(0x80)));
    assertEquals(false, testee.canStartWith(toByte(0x81)));
    assertEquals(false, testee.canStartWith(toByte(0xff)));
  }

  @Test
  public void is_finished_after_7_symbols() {
    assertFalse(testee.isFinished());
    testee.receive(toByte(1));
    assertFalse(testee.isFinished());
    testee.receive(toByte(2));
    assertFalse(testee.isFinished());
    testee.receive(toByte(3));
    assertFalse(testee.isFinished());
    testee.receive(toByte(4));
    assertFalse(testee.isFinished());
    testee.receive(toByte(5));
    assertFalse(testee.isFinished());
    testee.receive(toByte(6));
    assertFalse(testee.isFinished());

    testee.receive(toByte(7));
    assertTrue(testee.isFinished());

    testee.receive(toByte(8));
    assertTrue(testee.isFinished());
  }

  @Test
  public void can_restart_parsing() {
    testee.receive(toByte(1));
    testee.receive(toByte(2));
    testee.receive(toByte(3));
    testee.receive(toByte(4));
    testee.receive(toByte(5));
    testee.receive(toByte(6));
    testee.receive(toByte(7));

    testee.reset();

    assertFalse(testee.isFinished());
  }

}
