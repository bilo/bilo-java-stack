/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

public class MessageParser implements PartParser {
  final private ParserRepository parsers;
  final private PartParser startParser;
  final private IsFinishedStrategy finishedStrategy;

  private PartParser partParser;

  public MessageParser(PartParser startParser, ParserRepository parsers, IsFinishedStrategy finishedStrategy) {
    this.parsers = parsers;
    this.startParser = startParser;
    this.finishedStrategy = finishedStrategy;
    partParser = startParser;
  }

  @Override
  public boolean canStartWith(byte symbol) {
    return startParser.canStartWith(symbol);
  }

  @Override
  public void reset() {
    partParser = startParser;
    partParser.reset();
  }

  @Override
  public void receive(byte symbol) {
    if (partParser.isFinished()) {
      partParser = parsers.get(symbol);
      partParser.reset();
    }
    partParser.receive(symbol);
  }

  @Override
  public boolean isFinished() {
    return finishedStrategy.isFinished(partParser);
  }

}