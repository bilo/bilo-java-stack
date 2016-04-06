/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.serializer;

import static com.bloctesian.stream.message.ByteList.byteList;
import static com.bloctesian.stream.message.Constants.START_MESSAGE;

import java.util.List;;

class Header implements Serializer {
  @Override
  public List<Byte> serialize() {
    return byteList(START_MESSAGE);
  }
}