/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

public class NullPartParser implements PartParser {

  @Override
  public boolean canStartWith(byte symbol) {
    return false;
  }

  @Override
  public void reset() {
  }

  @Override
  public void receive(byte symbol) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }

}
