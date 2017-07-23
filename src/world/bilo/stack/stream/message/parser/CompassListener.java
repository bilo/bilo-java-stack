/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

import world.bilo.stack.Vector;

public interface CompassListener {

  public void compassChanged(Vector vector);

}
