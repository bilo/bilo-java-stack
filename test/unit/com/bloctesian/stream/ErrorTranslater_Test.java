/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream;

import static com.bloctesian.stream.message.ByteConvertion.toByte;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.bloctesian.Logger;
import com.bloctesian.internal.BaseError;

public class ErrorTranslater_Test {
  private final Logger listener = mock(Logger.class);
  private final ErrorTranslater testee = new ErrorTranslater(listener);

  @Test
  public void BlockBufferOverflow_is_forwarded() {
    testee.error(BaseError.BlockBufferOverflow);

    verify(listener).error("to many blocks on base");
  }

  @Test
  public void UartBangIdle_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartBangIdle);

    verify(listener).error("received data from blocks but was not expecting");
  }

  @Test
  public void UartBangLed_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartBangLed);

    verify(listener).error("received data while communicating with the blocks");
  }

  @Test
  public void UartBufferOverflow_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartBufferOverflow);

    verify(listener).error("uart buffer overflow");
  }

  @Test
  public void UartLedOverflow_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartLedOverflow);

    verify(listener).error("base received data for unknown led (led overflow)");
  }

  @Test
  public void UartUnknownData_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartUnknownData);

    verify(listener).error("base received unexpected data");
  }

  @Test
  public void UartUnknownLed_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartUnknownLed);

    verify(listener).error("base received data for unknown led (unknown led)");
  }

  @Test
  public void receiving_unknown_data_logged_so() {
    testee.unknownDataReceived(toByte(123));

    verify(listener).error("received unexpected data: 123");
  }
}
