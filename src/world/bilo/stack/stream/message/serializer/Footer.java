/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.serializer;

import static world.bilo.stack.stream.message.ByteList.byteList;
import static world.bilo.stack.stream.message.Constants.END_MESSAGE;

import java.util.List;;

class Footer implements Serializer {
  @Override
  public List<Byte> serialize() {
    return byteList(END_MESSAGE);
  }
}