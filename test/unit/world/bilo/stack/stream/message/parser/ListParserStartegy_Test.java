/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class ListParserStartegy_Test {
  final private PartParser endParser = mock(PartParser.class);
  final private ListParserStrategy testee = new ListParserStrategy(endParser);

  final private PartParser activeParser = mock(PartParser.class);

  @Test
  public void is_finished_when_EndParser_is_finished() {
    when(endParser.isFinished()).thenReturn(true);

    assertEquals(true, testee.isFinished(endParser));
  }

  @Test
  public void is_not_finished_when_EndParser_is_not_finished() {
    when(endParser.isFinished()).thenReturn(false);

    assertEquals(false, testee.isFinished(endParser));
  }

  @Test
  public void is_not_finished_when_other_than_EndParser_working() {
    when(activeParser.isFinished()).thenReturn(true);

    assertEquals(false, testee.isFinished(activeParser));
  }

}
