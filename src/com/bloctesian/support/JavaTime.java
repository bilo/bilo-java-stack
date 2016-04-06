/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.support;

public class JavaTime implements Time {

  @Override
  public long getTimeMs() {
    return System.currentTimeMillis();
  }

}
