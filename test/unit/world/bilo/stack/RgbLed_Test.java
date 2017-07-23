/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class RgbLed_Test {
  private final RgbLed testee = new RgbLed();
  private final RgbLedPropertyListener listener = mock(RgbLedPropertyListener.class);

  @Test
  public void color_is_black_by_default() {
    assertEquals(Color.Black, testee.getColor());
  }

  @Test
  public void can_set_color() {
    testee.setColor(Color.Cyan);

    assertEquals(Color.Cyan, testee.getColor());
  }

  @Test
  public void get_notified_when_the_color_changed() {
    testee.listener().add(listener);

    testee.setColor(Color.Magenta);

    verify(listener).colorChanged(eq(testee));
  }

  @Test
  public void do_not_notify_if_the_new_is_the_same_as_the_old() {
    testee.setColor(Color.White);
    testee.listener().add(listener);

    testee.setColor(Color.White);

    verify(listener, never()).colorChanged(any(RgbLed.class));
  }

  @Test
  public void has_a_readable_toString_output() {
    testee.setColor(Color.Green);

    assertEquals("RgbLed{color:" + Color.Green + "}", testee.toString());
  }

}
