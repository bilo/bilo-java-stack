/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package block;

import static helper.Lists.list;
import static java.lang.Math.PI;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.bloctesian.Logger;
import com.bloctesian.RotationListener;
import com.bloctesian.Timer;
import com.bloctesian.stream.Stream;
import com.bloctesian.stream.StreamBlocks;

public class Compass_Test {
  private final RotationListener listener = mock(RotationListener.class);
  private final Logger logger = mock(Logger.class);
  private final Stream output = mock(Stream.class);
  private final Timer timer = mock(Timer.class);
  private final StreamBlocks streamBlocks = new StreamBlocks(output, timer, logger);
  static final float Delta = 0.001f;

  @Before
  public void addListeners() {
    streamBlocks.rotationListener().add(listener);
  }

  @Test
  public void can_handle_invalid_data() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0x82, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x81));

    verify(listener, times(0)).rotationChanged(anyDouble());
  }

  @Test
  public void decode_a_0_deg_rotation() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0x82, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x81));

    verify(listener).rotationChanged(eq(0, Delta));
  }

  @Test
  public void decode_a_45_deg_rotation() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0x82, 0x10, 0x00, 0x10, 0x00, 0x00, 0x00, 0x81));

    verify(listener).rotationChanged(eq(PI / 4, Delta));
  }

  @Test
  public void decode_a_minus_45_deg_rotation() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0x82, 0x00, 0x01, 0xff, 0xff, 0x00, 0x00, 0x81));

    verify(listener).rotationChanged(eq(-PI / 4, Delta));
  }

  @Test
  public void can_decode_from_multiple_messages() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0x82, 0x00, 0x01, 0xff, 0xff, 0x00, 0x00, 0x81));
    streamBlocks.newData(list(0x80, 0x82, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x81));

    verify(listener).rotationChanged(eq(-PI / 4, Delta));
    verify(listener).rotationChanged(eq(PI / 4, Delta));
  }

  @Test
  public void can_decode_from_multiple_fields() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80));
    streamBlocks.newData(list(0x82, 0x00, 0x01, 0xff, 0xff, 0x00, 0x00));
    streamBlocks.newData(list(0x82, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00));
    streamBlocks.newData(list(0x81));

    verify(listener).rotationChanged(eq(-PI / 4, Delta));
    verify(listener).rotationChanged(eq(PI / 4, Delta));
  }

  @Test
  public void reports_received_data_to_logger() {
    streamBlocks.start();

    streamBlocks.newData(list(0x80, 0x82, 0x00, 0x01, 0xff, 0xff, 0x00, 0x20, 0x81));

    verify(logger, times(4)).debug(anyString());
    verify(logger).debug("rotation: raw=(1/-1/32) decoded=-45°");
  }

}
