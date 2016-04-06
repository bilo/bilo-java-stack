/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import static com.bloctesian.stream.message.ByteConvertion.toByte;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class ParserRepository_Test {
  final private List<PartParser> parsers = new ArrayList<>();
  final private ParserRepository testee = new ParserRepository(parsers);

  final private PartParser parser1 = mock(PartParser.class);
  final private PartParser parser2 = mock(PartParser.class);
  final private InOrder order = Mockito.inOrder(parser1, parser2);

  public ParserRepository_Test() {
    parsers.add(parser1);
    parsers.add(parser2);
  }

  @Test
  public void checks_parsers_in_order() {
    when(parser1.canStartWith(anyByte())).thenReturn(false);
    when(parser2.canStartWith(anyByte())).thenReturn(true);

    testee.get(toByte(0));

    order.verify(parser1).canStartWith(anyByte());
    order.verify(parser2).canStartWith(anyByte());
  }

  @Test
  public void forwards_symbol_to_request() {
    when(parser1.canStartWith(anyByte())).thenReturn(true);

    testee.get(toByte(65));

    verify(parser1).canStartWith(eq(toByte(65)));
  }

  @Test(expected = RuntimeException.class)
  public void throw_error_when_no_matching_parser_is_found() {
    when(parser1.canStartWith(anyByte())).thenReturn(false);
    when(parser2.canStartWith(anyByte())).thenReturn(false);

    testee.get(toByte(0));
  }

}
