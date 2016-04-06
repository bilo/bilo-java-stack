/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.serializer;

import static com.bloctesian.stream.message.ByteList.byteList;
import static com.bloctesian.stream.message.Constants.END_MESSAGE;

import java.util.List;;

class Footer implements Serializer {
  @Override
  public List<Byte> serialize() {
    return byteList(END_MESSAGE);
  }
}