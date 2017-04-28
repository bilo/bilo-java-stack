/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.serializer;

import java.util.ArrayList;
import java.util.List;

//TODO merge with MessageSerializer
public class ErrorRecoverySerializer implements Serializer {
  private final List<Serializer> serializers = new ArrayList<>();

  public ErrorRecoverySerializer() {
    serializers.add(new Header());
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
