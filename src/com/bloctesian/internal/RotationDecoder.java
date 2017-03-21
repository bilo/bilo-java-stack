/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.internal;

import com.bloctesian.Logger;
import com.bloctesian.RotationListener;
import com.bloctesian.Vector;
import com.bloctesian.stream.message.parser.CompassListener;

public class RotationDecoder implements CompassListener {
  private final RotationListener listener;
  private final Logger logger;

  public RotationDecoder(RotationListener listener, Logger logger) {
    this.listener = listener;
    this.logger = logger;
  }

  @Override
  public void compassChanged(Vector vector) {
    double rotation = decodeRotation(vector);

    log(vector, rotation);

    if (isValid(rotation)) {
      listener.rotationChanged(rotation);
    }
  }

  static private double sqr(double value) {
    return value * value;
  }

  static private long degreeFromRad(double rad) {
    return Math.round(rad * 180.0 / Math.PI);
  }

  static private double decodeRotation(Vector vector) {
    double len = Math.sqrt(sqr(vector.x) + sqr(vector.y));
    if (len >= 1) {
      double normX = vector.x / len;
      double angle = Math.acos(normX);
      double sign = vector.y >= 0 ? 1.0 : -1.0;
      double rotation = angle * sign;

      return rotation;
    } else {
      return invalidValue();
    }
  }

  private void log(Vector vector, double rotation) {
    String degText = isValid(rotation) ? degreeFromRad(rotation) + "°" : "invalid";
    logger.debug("rotation: raw=" + vector + " decoded=" + degText);
  }

  static private boolean isValid(double rotation) {
    return !Double.isNaN(rotation);
  }

  static private double invalidValue() {
    return Double.NaN;
  }

}
