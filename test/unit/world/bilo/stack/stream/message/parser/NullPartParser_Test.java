/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static world.bilo.stack.stream.message.ByteConvertion.toByte;

import org.junit.Test;

public class NullPartParser_Test {
  final private NullPartParser testee = new NullPartParser();

  @Test
  public void can_never_start() {
    assertEquals(false, testee.canStartWith(toByte(0x00)));
    assertEquals(false, testee.canStartWith(toByte(0x01)));
    assertEquals(false, testee.canStartWith(toByte(0x02)));
    assertEquals(false, testee.canStartWith(toByte(0x80)));
    assertEquals(false, testee.canStartWith(toByte(0x81)));
    assertEquals(false, testee.canStartWith(toByte(0x82)));
    assertEquals(false, testee.canStartWith(toByte(0xff)));
  }

  @Test
  public void is_always_finished() {
    assertTrue(testee.isFinished());

    testee.receive(toByte(123));
    assertTrue(testee.isFinished());

    testee.reset();
    assertTrue(testee.isFinished());
  }

}
