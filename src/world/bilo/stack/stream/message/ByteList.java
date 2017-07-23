/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message;

import static world.bilo.stack.stream.message.ByteConvertion.toByte;

import java.util.ArrayList;
import java.util.List;

public class ByteList {

  static public List<Byte> byteList(int value) {
    List<Byte> symbols = new ArrayList<>();
    symbols.add(toByte(value));
    return symbols;
  }

}
