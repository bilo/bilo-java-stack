/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static world.bilo.stack.stream.message.ByteConvertion.toByte;
import static world.bilo.stack.stream.message.Constants.START_MESSAGE;

import java.util.List;

import org.junit.Test;

import world.bilo.stack.BlockId;

public class MessageStartParser_Test {
  final private List<BlockId> blocks = mock(List.class);
  final private MessageStartParser testee = new MessageStartParser(blocks);

  @Test
  public void can_start_with_start_message() {
    assertEquals(true, testee.canStartWith(START_MESSAGE));
  }

  @Test
  public void can_not_start_with_other_symbols() {
    assertEquals(false, testee.canStartWith(toByte(0x00)));
    assertEquals(false, testee.canStartWith(toByte(0x81)));
    assertEquals(false, testee.canStartWith(toByte(0xff)));
  }

  @Test
  public void is_not_finished_by_default() {
    assertEquals(false, testee.isFinished());
  }

  @Test
  public void is_finished_after_start_symbol() {
    testee.receive(START_MESSAGE);

    assertEquals(true, testee.isFinished());
  }

  @Test
  public void clears_block_list_upon_start() {
    testee.receive(START_MESSAGE);

    verify(blocks).clear();
  }

  @Test
  public void is_not_finished_after_reset() {
    testee.receive(START_MESSAGE);

    testee.reset();

    assertEquals(false, testee.isFinished());
  }

  @Test(expected = RuntimeException.class)
  public void throws_error_for_unexpected_symbols() {
    testee.receive(toByte(0x00));
  }

}
