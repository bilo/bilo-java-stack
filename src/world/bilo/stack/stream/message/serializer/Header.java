/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.serializer;

import static world.bilo.stack.stream.message.ByteList.byteList;
import static world.bilo.stack.stream.message.Constants.START_MESSAGE;

import java.util.List;;

class Header implements Serializer {
  @Override
  public List<Byte> serialize() {
    return byteList(START_MESSAGE);
  }
}