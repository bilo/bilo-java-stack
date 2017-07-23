/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.serializer;

import static world.bilo.stack.stream.message.ByteConvertion.toByte;

import java.util.ArrayList;
import java.util.List;

import world.bilo.stack.Block;
import world.bilo.util.functional.UnaryFunction;

class BlockSerializer implements UnaryFunction<List<Byte>, Block> {

  @Override
  public List<Byte> execute(Block block) {
    ArrayList<Byte> symbols = new ArrayList<>();

    int byteCode = 0;

    for (int i = 0; i < block.getLeds().size(); i++) {
      byteCode |= block.getLeds().get(i).getColor().bitfield();

      if ((i % 2) == 0) {
        byteCode <<= 3;
      } else {
        symbols.add(toByte(byteCode));
        byteCode = 0;
      }
    }

    return symbols;
  }

}