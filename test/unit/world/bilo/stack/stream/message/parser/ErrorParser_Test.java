/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static world.bilo.stack.stream.message.ByteConvertion.toByte;

import org.junit.Test;

import world.bilo.stack.internal.BaseError;
import world.bilo.stack.internal.ErrorListener;

public class ErrorParser_Test {
  final private ErrorListener errorListener = mock(ErrorListener.class);
  final private ErrorParser testee = new ErrorParser(errorListener);

  @Test
  public void is_finished_by_default() {
    assertTrue(testee.isFinished());
  }

  @Test
  public void is_finished_after_reset() {
    testee.reset();

    assertTrue(testee.isFinished());
  }

  @Test
  public void is_finished_after_1_symbol() {
    testee.receive(toByte(1));

    assertTrue(testee.isFinished());
  }

  @Test
  public void reports_unknown_data() {
    testee.receive(toByte(0xff));

    verify(errorListener).unknownDataReceived(toByte(0xff));
  }

  @Test
  public void decode_UartBangLed_Error() {
    testee.receive(toByte(0x42));

    verify(errorListener).error(eq(BaseError.UartBangLed));
  }

  @Test
  public void decode_UartLedOverflow_Error() {
    testee.receive(toByte(0x43));

    verify(errorListener).error(eq(BaseError.UartLedOverflow));
  }

  @Test
  public void decode_UartUnknownLed_Error() {
    testee.receive(toByte(0x44));

    verify(errorListener).error(eq(BaseError.UartUnknownLed));
  }

  @Test
  public void decode_UartBufferOverflow_Error() {
    testee.receive(toByte(0x45));

    verify(errorListener).error(eq(BaseError.UartBufferOverflow));
  }

  @Test
  public void decode_UartBangIdle_Error() {
    testee.receive(toByte(0x46));

    verify(errorListener).error(eq(BaseError.UartBangIdle));
  }

  @Test
  public void decode_UartUnknownData_Error() {
    testee.receive(toByte(0x47));

    verify(errorListener).error(eq(BaseError.UartUnknownData));
  }

  @Test
  public void decode_BlockBufferOverflow_Error() {
    testee.receive(toByte(0x48));

    verify(errorListener).error(eq(BaseError.BlockBufferOverflow));
  }

}
