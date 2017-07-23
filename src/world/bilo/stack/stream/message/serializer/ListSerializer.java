/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.serializer;

import java.util.ArrayList;
import java.util.List;

import world.bilo.util.functional.UnaryFunction;

class ListSerializer<T> implements Serializer {
  private final Iterable<T> items;
  private final UnaryFunction<List<Byte>, T> serializer;

  public ListSerializer(Iterable<T> items, UnaryFunction<List<Byte>, T> serializer) {
    this.items = items;
    this.serializer = serializer;
  }

  @Override
  public List<Byte> serialize() {
    ArrayList<Byte> symbols = new ArrayList<>();

    for (T item : items) {
      symbols.addAll(serializer.execute(item));
    }

    return symbols;
  }
}