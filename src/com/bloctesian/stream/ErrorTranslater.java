/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream;

import com.bloctesian.Logger;
import com.bloctesian.internal.BaseError;
import com.bloctesian.internal.ErrorListener;

public class ErrorTranslater implements ErrorListener {
  private final Logger listener;

  public ErrorTranslater(Logger listener) {
    this.listener = listener;
  }

  @Override
  public void error(BaseError error) {
    listener.error(translateError(error));
  }

  @Override
  public void unknownDataReceived(byte data) {
    listener.error("received unexpected data: " + data);
  }

  static private String translateError(BaseError error) {
    switch (error) {
    case BlockBufferOverflow:
      return "to many blocks on base";
    case UartBangIdle:
      return "received data from blocks but was not expecting";
    case UartBangLed:
      return "received data while communicating with the blocks";
    case UartBufferOverflow:
      return "uart buffer overflow";
    case UartLedOverflow:
      return "base received data for unknown led (led overflow)";
    case UartUnknownData:
      return "base received unexpected data";
    case UartUnknownLed:
      return "base received data for unknown led (unknown led)";
    }

    return "unhandled error: " + error;
  }

}
