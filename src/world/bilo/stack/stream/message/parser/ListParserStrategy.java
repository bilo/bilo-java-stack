/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

class ListParserStrategy implements IsFinishedStrategy {
  final private PartParser endParser;

  public ListParserStrategy(PartParser endParser) {
    this.endParser = endParser;
  }

  @Override
  public boolean isFinished(PartParser active) {
    return (active == endParser) && (active.isFinished());
  }

}