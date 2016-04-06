/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static com.bloctesian.stream.message.ByteConvertion.toByte;
import static com.bloctesian.stream.message.Constants.END_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bloctesian.BlockId;
import com.bloctesian.internal.BlockStateListener;

public class MessageEndParser_Test {
  final private List<BlockId> blocks = new ArrayList<>();
  final private BlockStateListener blockListener = mock(BlockStateListener.class);
  final private MessageEndParser testee = new MessageEndParser(blocks, blockListener);

  @Test
  public void can_start_with_end_message() {
    assertEquals(true, testee.canStartWith(END_MESSAGE));
  }

  @Test
  public void can_not_start_with_other_symbols() {
    assertEquals(false, testee.canStartWith(toByte(0x00)));
    assertEquals(false, testee.canStartWith(toByte(0x80)));
    assertEquals(false, testee.canStartWith(toByte(0xff)));
  }

  @Test
  public void is_finished_by_default() {
    assertEquals(true, testee.isFinished());
  }

  @Test
  public void is_finished_after_end_symbol() {
    testee.receive(END_MESSAGE);

    assertEquals(true, testee.isFinished());
  }

  @Test
  public void is_not_finished_after_reset() {
    testee.receive(END_MESSAGE);

    testee.reset();

    assertEquals(false, testee.isFinished());
  }

  @Test
  public void notifies_block_listener_upon_start() {
    testee.receive(END_MESSAGE);

    verify(blockListener).blocks(eq(blocks));
  }

  @Test(expected = RuntimeException.class)
  public void throws_error_for_unexpected_symbols() {
    testee.receive(toByte(0x00));
  }
}
