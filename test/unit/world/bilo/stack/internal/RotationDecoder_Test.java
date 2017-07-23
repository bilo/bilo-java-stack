/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.internal;

import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import world.bilo.stack.Logger;
import world.bilo.stack.RotationListener;
import world.bilo.stack.Vector;

public class RotationDecoder_Test {
  static final private double Delta = 0.0001;
  private final RotationListener listener = mock(RotationListener.class);
  private final Logger logger = mock(Logger.class);
  private final RotationDecoder testee = new RotationDecoder(listener, logger);

  @Test
  public void notifies_listener_on_new_data() {
    testee.compassChanged(new Vector(1, 2, 3));

    verify(listener).rotationChanged(anyDouble());
  }

  @Test
  public void does_not_notify_on_invalid_data() {
    testee.compassChanged(new Vector(0, 0, 0));

    verify(listener, times(0)).rotationChanged(anyDouble());
  }

  @Test
  public void does_not_notify_on_invalid_x_and_y() {
    testee.compassChanged(new Vector(0, 0, 1));

    verify(listener, times(0)).rotationChanged(anyDouble());
  }

  @Test
  public void decodes_0_deg() {
    testee.compassChanged(new Vector(42, 0, 0));

    verify(listener).rotationChanged(eq(0, Delta));
  }

  @Test
  public void decodes_45_deg() {
    testee.compassChanged(new Vector(1, 1, 0));

    verify(listener).rotationChanged(eq(Math.PI / 4, Delta));
  }

  @Test
  public void ignores_z() {
    testee.compassChanged(new Vector(1, 1, 42));

    verify(listener).rotationChanged(eq(Math.PI / 4, Delta));
  }

  @Test
  public void decodes_90_deg() {
    testee.compassChanged(new Vector(0, 100, 0));

    verify(listener).rotationChanged(eq(Math.PI / 2, Delta));
  }

  @Test
  public void decodes_135_deg() {
    testee.compassChanged(new Vector(-30, 30, 0));

    verify(listener).rotationChanged(eq(3 * Math.PI / 4, Delta));
  }

  @Test
  public void decodes_180_deg() {
    testee.compassChanged(new Vector(-88, 0, 0));

    verify(listener).rotationChanged(eq(Math.PI, Delta));
  }

  @Test
  public void decodes_225_deg() {
    testee.compassChanged(new Vector(-5, -5, 0));

    verify(listener).rotationChanged(eq(-3 * Math.PI / 4, Delta));
  }

  @Test
  public void decodes_270_deg() {
    testee.compassChanged(new Vector(0, -1, 0));

    verify(listener).rotationChanged(eq(-Math.PI / 2, Delta));
  }

  @Test
  public void decodes_315_deg() {
    testee.compassChanged(new Vector(7, -7, 0));

    verify(listener).rotationChanged(eq(-Math.PI / 4, Delta));
  }

  @Test
  public void notifies_logger_on_received_data() {
    testee.compassChanged(new Vector(1732, -1000, 42));

    verify(logger).debug("rotation: raw=(1732/-1000/42) decoded=-30°");
  }

  @Test
  public void notifies_logger_on_invalid_data() {
    testee.compassChanged(new Vector(0, 00, 42));

    verify(logger).debug("rotation: raw=(0/0/42) decoded=invalid");
  }
}
