/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.internal;

public interface ErrorListener {
  public void error(BaseError error);

  public void unknownDataReceived(byte data);

}
