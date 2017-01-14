/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.parser;

import java.util.ArrayList;
import java.util.List;

import com.bloctesian.BlockId;
import com.bloctesian.internal.BlockStateListener;

public class ParserFactory {
  static public PartParser produce(BlockStateListener blockListener, ErrorListener errorListener) {
    ErrorParser errorParser = new ErrorParser(errorListener);
    List<BlockId> blocs = new ArrayList<>();
    BlockParser blockParser = new BlockParser(blocs);
    MessageStartParser startParser = new MessageStartParser(blocs);
    MessageEndParser endParser = new MessageEndParser(blocs, blockListener);

    List<PartParser> messageParsers = new ArrayList<>();
    messageParsers.add(endParser);
    messageParsers.add(new CompassParser());
    messageParsers.add(blockParser);
    MessageParser messageParser = new MessageParser(startParser, new ParserRepository(messageParsers), new ListParserStrategy(endParser));

    List<PartParser> continuousParsers = new ArrayList<>();
    continuousParsers.add(messageParser);
    continuousParsers.add(errorParser);
    MessageParser continuousParser = new MessageParser(errorParser, new ParserRepository(continuousParsers), new ChunkParserStrategy());

    return continuousParser;
  }
}
