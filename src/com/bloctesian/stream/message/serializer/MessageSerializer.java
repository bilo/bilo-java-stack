/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.serializer;

import java.util.ArrayList;
import java.util.List;

import com.bloctesian.Block;

public class MessageSerializer implements Serializer {
  private final List<Serializer> serializers = new ArrayList<>();

  public MessageSerializer(Block base, Iterable<Block> blocks) {
    BlockSerializer blockSerializer = new BlockSerializer();

    serializers.add(new Header());
    serializers.add(new BaseSerializer(base, blockSerializer));
    serializers.add(new ListSerializer<Block>(blocks, blockSerializer));
    serializers.add(new Footer());
  }

  @Override
  public List<Byte> serialize() {
    List<Byte> symbols = new ArrayList<>();

    for (Serializer serializer : serializers) {
      symbols.addAll(serializer.serialize());
    }

    return symbols;
  }

}
