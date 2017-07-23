/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.internal;

public interface ErrorListener {
  public void error(BaseError error);

  public void unknownDataReceived(byte data);

}
