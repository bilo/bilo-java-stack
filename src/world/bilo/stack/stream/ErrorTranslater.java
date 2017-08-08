/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream;

import world.bilo.stack.Logger;
import world.bilo.stack.internal.BaseError;
import world.bilo.stack.internal.ErrorListener;

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
    case UartBangLed:
    case UartBufferOverflow:
    case UartLedOverflow:
    case UartUnknownData:
    case UartUnknownLed:
      return "protocol missmatch";
    }

    return "unhandled error: " + error;
  }

}
