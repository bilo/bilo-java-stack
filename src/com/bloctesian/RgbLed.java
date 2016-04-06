/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian;

import com.bloctesian.utility.UniqueOrderedList;
import com.bloctesian.utility.functional.Function;

public class RgbLed {
  private final UniqueOrderedList<RgbLedPropertyListener> listeners = new UniqueOrderedList<>();
  private final Function<RgbLedPropertyListener> colorChanged = new ColorChangedFunction(this);
  private Color color = Color.Black;

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    if (!this.color.equals(color)) {
      this.color = color;
      listeners.apply(colorChanged);
    }
  }

  public UniqueOrderedList<RgbLedPropertyListener> listener() {
    return listeners;
  }

  private class ColorChangedFunction implements Function<RgbLedPropertyListener> {
    private final RgbLed led;

    public ColorChangedFunction(RgbLed led) {
      this.led = led;
    }

    @Override
    public void execute(RgbLedPropertyListener item) {
      item.colorChanged(led);
    }
  };
}
