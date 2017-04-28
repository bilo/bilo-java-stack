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

  static final private String ToManyBlocksOnBase = "to many blocks on base";
  static final private String ProtocolMissmatch = "protocol missmatch";

  @Test
  public void BlockBufferOverflow_is_forwarded() {
    testee.error(BaseError.BlockBufferOverflow);

    verify(listener).error(ToManyBlocksOnBase);
  }

  @Test
  public void UartBangIdle_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartBangIdle);

    verify(listener).error(ProtocolMissmatch);
  }

  @Test
  public void UartBangLed_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartBangLed);

    verify(listener).error(ProtocolMissmatch);
  }

  @Test
  public void UartBufferOverflow_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartBufferOverflow);

    verify(listener).error(ProtocolMissmatch);
  }

  @Test
  public void UartLedOverflow_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartLedOverflow);

    verify(listener).error(ProtocolMissmatch);
  }

  @Test
  public void UartUnknownData_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartUnknownData);

    verify(listener).error(ProtocolMissmatch);
  }

  @Test
  public void UartUnknownLed_is_translated_to_protocoll_error() {
    testee.error(BaseError.UartUnknownLed);

    verify(listener).error(ProtocolMissmatch);
  }

  @Test
  public void receiving_unknown_data_logged_so() {
    testee.unknownDataReceived(toByte(123));

    verify(listener).error("received unexpected data: 123");
  }
}
