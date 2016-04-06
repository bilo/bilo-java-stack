/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian;

import java.util.ArrayList;
import java.util.List;

public class Block {
  private final BlockId id;
  private final List<RgbLed> leds;

  public Block(BlockId id) {
    this.id = id;
    leds = createLeds(id.type);
  }

  @Override
  public String toString() {
    return "Block{id:" + id + " leds:" + leds + "}";
  }

  public BlockId getId() {
    return id;
  }

  public List<RgbLed> getLeds() {
    return leds;
  }

  static private List<RgbLed> createLeds(BlockType type) {
    List<RgbLed> leds = new ArrayList<>();

    leds.add(new RgbLed());
    leds.add(new RgbLed());

    if (type != BlockType.Block2x2) {
      leds.add(new RgbLed());
      leds.add(new RgbLed());
    }

    return leds;
  }

}
