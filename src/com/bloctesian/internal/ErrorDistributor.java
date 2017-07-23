/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.internal;

import world.bilo.util.UniqueOrderedList;

public class ErrorDistributor implements ErrorListener {
  private final UniqueOrderedList<ErrorListener> listener = new UniqueOrderedList<>();

  public UniqueOrderedList<ErrorListener> getListener() {
    return listener;
  }

  @Override
  public void error(BaseError error) {
    for (ErrorListener itr : listener) {
      itr.error(error);
    }
  }

  @Override
  public void unknownDataReceived(byte data) {
    for (ErrorListener itr : listener) {
      itr.unknownDataReceived(data);
    }
  }

}
