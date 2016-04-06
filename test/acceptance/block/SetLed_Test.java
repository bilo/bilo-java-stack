/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package block;

import static helper.Lists.list;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;

import com.bloctesian.Block;
import com.bloctesian.Color;
import com.bloctesian.Logger;
import com.bloctesian.RgbLed;
import com.bloctesian.Timer;
import com.bloctesian.stream.Stream;
import com.bloctesian.stream.StreamBlocks;

public class SetLed_Test {
  private static final List<Byte> block2x2_block4x2 = list(0x80, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x81);
  private static final List<Byte> block2x2 = list(0x80, 0x00, 0x00, 0x00, 0x04, 0x81);
  private static final List<Byte> block4x2 = list(0x80, 0x00, 0x00, 0x00, 0x00, 0x81);
  private final Logger logger = mock(Logger.class);
  private final Stream output = mock(Stream.class);
  private final Timer timer = mock(Timer.class);
  private final StreamBlocks streamBlocks = new StreamBlocks(output, timer, logger);

  @Test
  public void base_led0_is_off_led1_red_led2_green_led3_blue() {
    streamBlocks.start();

    verify(output).newData(list(0x80, 0x01, 0x14, 0x81));
  }

  @Test
  public void turn_led_off_by_default_on_one_4x2_block() {
    streamBlocks.start();

    streamBlocks.newData(block4x2);

    verify(output).newData(list(0x80, 0x01, 0x14, 0x00, 0x00, 0x81));
  }

  @Test
  public void turn_led_red_on_one_4x2_block() {
    streamBlocks.start();
    streamBlocks.newData(block4x2);

    setColor(Color.Red);
    streamBlocks.newData(block4x2);

    verify(output).newData(list(0x80, 0x01, 0x14, 0x09, 0x09, 0x81));
  }

  @Test
  public void turn_led_blue_on_one_2x2_block() {
    streamBlocks.start();
    streamBlocks.newData(block2x2);

    setColor(Color.Blue);
    streamBlocks.newData(block2x2);

    verify(output).newData(list(0x80, 0x01, 0x14, 0x24, 0x81));
  }

  @Test
  public void turn_led_green_on_one_2x2_block_and_green_on_one_4x2_block() {
    streamBlocks.start();
    streamBlocks.newData(block2x2_block4x2);

    setColor(Color.Green);
    streamBlocks.newData(block2x2_block4x2);

    verify(output).newData(list(0x80, 0x01, 0x14, 0x12, 0x12, 0x12, 0x81));
  }

  private void setColor(Color color) {
    for (Block block : streamBlocks.getBlocks().items()) {
      for (RgbLed led : block.getLeds()) {
        led.setColor(color);
      }
    }
  }
}
