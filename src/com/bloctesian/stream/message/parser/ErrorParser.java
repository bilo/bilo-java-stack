/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static com.bloctesian.stream.message.ByteConvertion.toByte;

import java.util.HashMap;
import java.util.Map;

import com.bloctesian.internal.BaseError;
import com.bloctesian.internal.ErrorListener;

public class ErrorParser implements PartParser {
  final private ErrorListener errorListener;
  static final private Map<Byte, BaseError> errors = baseErrorMap();

  public ErrorParser(ErrorListener errorListener) {
    this.errorListener = errorListener;
  }

  @Override
  public void reset() {
  }

  @Override
  public void receive(byte symbol) {
    if (errors.containsKey(symbol)) {
      errorListener.error(errors.get(symbol));
    } else {
      errorListener.unknownDataReceived(symbol);
    }
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  static private Map<Byte, BaseError> baseErrorMap() {
    Map<Byte, BaseError> errors = new HashMap<Byte, BaseError>();

    errors.put(toByte(0x42), BaseError.UartBangLed);
    errors.put(toByte(0x43), BaseError.UartLedOverflow);
    errors.put(toByte(0x44), BaseError.UartUnknownLed);
    errors.put(toByte(0x45), BaseError.UartBufferOverflow);
    errors.put(toByte(0x46), BaseError.UartBangIdle);
    errors.put(toByte(0x47), BaseError.UartUnknownData);
    errors.put(toByte(0x48), BaseError.BlockBufferOverflow);

    return errors;
  }

  @Override
  public boolean canStartWith(byte symbol) {
    return true;
  }

}
