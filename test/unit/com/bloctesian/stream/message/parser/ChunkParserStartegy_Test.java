/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class ChunkParserStartegy_Test {
  final private ChunkParserStrategy testee = new ChunkParserStrategy();

  final private PartParser activeParser = mock(PartParser.class);

  @Test
  public void is_finished_when_activeParser_is_finished() {
    when(activeParser.isFinished()).thenReturn(true);

    assertEquals(true, testee.isFinished(activeParser));
  }

  @Test
  public void is_not_finished_when_activeParser_is_not_finished() {
    when(activeParser.isFinished()).thenReturn(false);

    assertEquals(false, testee.isFinished(activeParser));
  }

}
