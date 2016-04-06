/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static com.bloctesian.stream.message.ByteConvertion.toByte;
import static helper.Lists.list;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bloctesian.BlockId;
import com.bloctesian.BlockType;
import com.bloctesian.Rotation;
import com.bloctesian.Vector;

public class BlockParser_Test {
  final private List<BlockId> blocks = new ArrayList<>();
  final private BlockParser testee = new BlockParser(blocks);

  @Test
  public void decode_one_block() {
    assertFalse(testee.isFinished());
    testee.receive(toByte(0x03));
    assertFalse(testee.isFinished());
    testee.receive(toByte(0x05));
    assertFalse(testee.isFinished());
    testee.receive(toByte(0x07));
    assertFalse(testee.isFinished());
    testee.receive(toByte(0x01));
    assertTrue(testee.isFinished());

    assertEquals(list(new BlockId(BlockType.Block4x2, new Vector(3, 5, 7), Rotation.Deg270)), blocks);
  }

  @Test
  public void decode_another_block() {
    testee.receive(toByte(0x10));
    testee.receive(toByte(0x20));
    testee.receive(toByte(0x30));
    testee.receive(toByte(0x06));

    assertEquals(list(new BlockId(BlockType.Block2x2, new Vector(0x10, 0x20, 0x30), Rotation.Deg180)), blocks);
  }

  @Test
  public void decode_block_with_negative_coordinates() {
    testee.receive(toByte(0xff));
    testee.receive(toByte(0xfe));
    testee.receive(toByte(0xfd));
    testee.receive(toByte(0x00));

    assertEquals(list(new BlockId(BlockType.Block4x2, new Vector(-1, -2, -3), Rotation.Deg0)), blocks);
  }

  @Test
  public void does_nothing_for_additional_data() {
    testee.receive(toByte(0x0));
    testee.receive(toByte(0x0));
    testee.receive(toByte(0x0));
    testee.receive(toByte(0x0));

    testee.receive(toByte(0x0));
    testee.receive(toByte(0x0));
    testee.receive(toByte(0x0));
    testee.receive(toByte(0x0));

    assertEquals(list(new BlockId(BlockType.Block4x2, new Vector(0, 0, 0), Rotation.Deg0)), blocks);
  }

  @Test
  public void restart_parsing_when_reseting() {
    testee.receive(toByte(0x0));
    testee.receive(toByte(0x0));
    testee.receive(toByte(0x0));
    testee.receive(toByte(0x03));

    blocks.clear();
    testee.reset();
    assertFalse(testee.isFinished());

    testee.receive(toByte(0x0));
    testee.receive(toByte(0x0));
    testee.receive(toByte(0x0));
    testee.receive(toByte(0x03));

    assertEquals(list(new BlockId(BlockType.Block4x2, new Vector(0, 0, 0), Rotation.Deg90)), blocks);
  }

  @Test
  public void can_handle_unused_bits_in_rotation_and_block_type() {
    testee.receive(toByte(0x00));
    testee.receive(toByte(0x00));
    testee.receive(toByte(0x00));
    testee.receive(toByte(0xf8));

    assertEquals(list(new BlockId(BlockType.Block4x2, new Vector(0x00, 0x00, 0x00), Rotation.Deg0)), blocks);
  }

}
