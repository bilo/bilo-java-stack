/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.serializer;

import java.util.List;

import com.bloctesian.Block;

class BaseSerializer implements Serializer {
  private final Block base;
  private final BlockSerializer serializer;

  public BaseSerializer(Block base, BlockSerializer serializer) {
    this.base = base;
    this.serializer = serializer;
  }

  @Override
  public List<Byte> serialize() {
    return serializer.execute(base);
  }
}