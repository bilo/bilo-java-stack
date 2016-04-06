/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream;

import java.util.List;

public interface Stream {
  public void newData(List<Byte> data);
}