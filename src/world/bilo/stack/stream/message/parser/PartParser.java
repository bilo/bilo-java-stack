/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

public interface PartParser {

  public boolean canStartWith(byte symbol);

  public void reset();

  public void receive(byte symbol);

  public boolean isFinished();

}
