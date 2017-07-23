/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.serializer;

import java.util.List;

public interface Serializer {

  public List<Byte> serialize();

}
