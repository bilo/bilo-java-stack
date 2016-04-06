/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static com.bloctesian.stream.message.Constants.START_COMPASS;

public class CompassParser implements PartParser {
  private static final int StartBytes = 1;
  private static final int DataBytes = 6;
  private int received;

  public CompassParser() {
    reset();
  }

  @Override
  public void reset() {
    received = 0;
  }

  @Override
  public boolean isFinished() {
    return received >= (StartBytes + DataBytes);
  }

  @Override
  public void receive(byte symbol) {
    received++;
  }

  @Override
  public boolean canStartWith(byte symbol) {
    return symbol == START_COMPASS;
  }

}
