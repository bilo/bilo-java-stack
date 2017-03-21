/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import com.bloctesian.Vector;

public interface CompassListener {

  public void compassChanged(Vector vector);

}
