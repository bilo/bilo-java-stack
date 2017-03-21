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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.bloctesian.Vector;

public class CompassParser_Test {
  final private CompassListener listener = mock(CompassListener.class);
  final private CompassParser testee = new CompassParser(listener);

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

  @Test
  public void notifies_listener_when_finished() {
    testee.receive(toByte(START_COMPASS));
    testee.receive(toByte(1));
    testee.receive(toByte(2));
    testee.receive(toByte(3));
    testee.receive(toByte(4));
    testee.receive(toByte(5));

    testee.receive(toByte(6));

    verify(listener).compassChanged(any(Vector.class));
  }

  @Test
  public void notifies_listener_only_once() {
    testee.receive(toByte(START_COMPASS));
    testee.receive(toByte(1));
    testee.receive(toByte(2));
    testee.receive(toByte(3));
    testee.receive(toByte(4));
    testee.receive(toByte(5));
    testee.receive(toByte(6));

    testee.receive(toByte(7));

    verify(listener).compassChanged(any(Vector.class));
  }

  @Test
  public void decodes_zeros() {
    testee.receive(toByte(START_COMPASS));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));

    verify(listener).compassChanged(new Vector(0, 0, 0));
  }

  @Test
  public void decodes_positive_x_axis() {
    testee.receive(toByte(START_COMPASS));
    testee.receive(toByte(0x12));
    testee.receive(toByte(0x34));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));

    verify(listener).compassChanged(new Vector(0x1234, 0, 0));
  }

  @Test
  public void decodes_negative_x_axis() {
    testee.receive(toByte(START_COMPASS));
    testee.receive(toByte(0xff));
    testee.receive(toByte(0xff));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));

    verify(listener).compassChanged(new Vector(-1, 0, 0));
  }

  @Test
  public void decodes_big_negative_x_axis() {
    testee.receive(toByte(START_COMPASS));
    testee.receive(toByte(0x80));
    testee.receive(toByte(0x00));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));

    verify(listener).compassChanged(new Vector(-32768, 0, 0));
  }

  @Test
  public void decodes_big_positive_x_axis() {
    testee.receive(toByte(START_COMPASS));
    testee.receive(toByte(0x03));
    testee.receive(toByte(0xe3));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));

    verify(listener).compassChanged(new Vector(995, 0, 0));
  }

  @Test
  public void decodes_y_axis() {
    testee.receive(toByte(START_COMPASS));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0x76));
    testee.receive(toByte(0x54));
    testee.receive(toByte(0));
    testee.receive(toByte(0));

    verify(listener).compassChanged(new Vector(0, 0x7654, 0));
  }

  @Test
  public void decodes_z_axis() {
    testee.receive(toByte(START_COMPASS));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0));
    testee.receive(toByte(0x22));
    testee.receive(toByte(0x33));

    verify(listener).compassChanged(new Vector(0, 0, 0x2233));
  }

}
