/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.serializer;

import static helper.Lists.list;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import com.bloctesian.Block;
import com.bloctesian.BlockId;
import com.bloctesian.BlockType;
import com.bloctesian.Rotation;
import com.bloctesian.Vector;

public class BaseSerializer_Test {
  private final Block base = new Block(new BlockId(BlockType.Base10x10, Vector.Zero, Rotation.Deg0));
  private final BlockSerializer serializer = mock(BlockSerializer.class);
  private final BaseSerializer testee = new BaseSerializer(base, serializer);

  @Test
  public void call_serializer_with_provided_block_when_serializing() {
    testee.serialize();

    verify(serializer).execute(eq(base));
  }

  @Test
  public void return_value_from_serializer_when_serializing() {
    List<Byte> stream = list(1, 2, 3);
    when(serializer.execute(any(Block.class))).thenReturn(stream);

    assertEquals(stream, testee.serialize());
  }

}
