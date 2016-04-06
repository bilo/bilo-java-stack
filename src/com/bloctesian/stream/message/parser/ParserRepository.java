/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import java.util.List;

class ParserRepository {
  final private List<PartParser> parsers;

  public ParserRepository(List<PartParser> parsers) {
    this.parsers = parsers;
  }

  public PartParser get(byte symbol) {
    for (PartParser parser : parsers) {
      if (parser.canStartWith(symbol)) {
        return parser;
      }
    }

    throw new RuntimeException("no parser for: " + symbol);
  }

}