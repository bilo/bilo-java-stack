/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static com.bloctesian.stream.message.ByteConvertion.toByte;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class MessageParser_Test {
  final private PartParser startParser = mock(PartParser.class);
  final private ParserRepository repository = mock(ParserRepository.class);
  final private IsFinishedStrategy finishedStrategy = mock(IsFinishedStrategy.class);
  final private MessageParser testee = new MessageParser(startParser, repository, finishedStrategy);

  final private PartParser parser = mock(PartParser.class);

  @Before
  public void repository_returns_default_parser() {
    when(repository.get(anyByte())).thenReturn(parser);
  }

  @Test
  public void forwards_symbol_from_start_decision_to_StartParser() {
    testee.canStartWith(toByte(123));

    verify(startParser).canStartWith(eq(toByte(123)));
  }

  @Test
  public void return_negative_start_decision_from_StartParser() {
    when(startParser.canStartWith(anyByte())).thenReturn(false);

    assertEquals(false, testee.canStartWith(toByte(0)));
  }

  @Test
  public void return_positive_start_decision_from_StartParser() {
    when(startParser.canStartWith(anyByte())).thenReturn(true);

    assertEquals(true, testee.canStartWith(toByte(0)));
  }

  @Test
  public void is_finished_when_strategy_is_finished() {
    testee.reset();
    when(finishedStrategy.isFinished(any(PartParser.class))).thenReturn(true);

    testee.receive(toByte(0));

    assertEquals(true, testee.isFinished());
  }

  @Test
  public void is_not_finished_when_strategy_is_not_finished() {
    testee.reset();
    when(finishedStrategy.isFinished(any(PartParser.class))).thenReturn(false);

    testee.receive(toByte(0));

    assertEquals(false, testee.isFinished());
  }

  @Test
  public void is_not_finished_when_other_than_EndParser_working() {
    testee.reset();
    when(startParser.isFinished()).thenReturn(true);

    testee.receive(toByte(0));

    assertEquals(false, testee.isFinished());
  }

  @Test
  public void reset_StartParser_on_reset() {
    testee.reset();

    verify(startParser).reset();
  }

  @Test
  public void uses_StartParser_after_reset() {
    testee.reset();

    testee.receive(toByte(0));

    verify(startParser).isFinished();
  }

  @Test
  public void choose_next_parser_when_active_parser_is_finished() {
    testee.reset();
    when(startParser.isFinished()).thenReturn(true);
    when(repository.get(anyByte())).thenReturn(parser);

    testee.receive(toByte(98));

    verify(parser).reset();
    verify(parser).receive(toByte(98));
  }

  @Test
  public void uses_initial_parser_by_default() {
    testee.receive(toByte(0));

    verify(startParser).isFinished();
  }

  @Test
  public void forwards_symbol_to_active_parser() {
    when(startParser.isFinished()).thenReturn(false);

    testee.receive(toByte(44));

    verify(startParser).receive(eq(toByte(44)));
  }

  @Test
  public void chooses_another_parser_when_active_parser_is_finished() {
    when(startParser.isFinished()).thenReturn(true);

    testee.receive(toByte(12));

    verify(repository).get(eq(toByte(12)));
  }

  @Test
  public void reset_the_new_parser_after_it_is_choosen() {
    when(startParser.isFinished()).thenReturn(true);

    testee.receive(toByte(12));

    verify(parser).reset();
  }

  @Test
  public void forward_data_to_newly_choosen_parser() {
    when(startParser.isFinished()).thenReturn(true);

    testee.receive(toByte(89));

    verify(parser).receive(eq(toByte(89)));
  }

  @Test
  public void use_newly_choosen_parser_for_next_symbol() {
    when(startParser.isFinished()).thenReturn(true);
    testee.receive(toByte(0));

    testee.receive(toByte(0));

    verify(parser).isFinished();
  }

}
