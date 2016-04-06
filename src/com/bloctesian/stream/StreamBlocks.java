/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream;

import java.util.ArrayList;
import java.util.List;

import com.bloctesian.Block;
import com.bloctesian.BlockId;
import com.bloctesian.BlockType;
import com.bloctesian.Color;
import com.bloctesian.Logger;
import com.bloctesian.Rotation;
import com.bloctesian.Timer;
import com.bloctesian.TimerCallback;
import com.bloctesian.Vector;
import com.bloctesian.internal.BlockFactory;
import com.bloctesian.internal.BlockRepository;
import com.bloctesian.stream.message.parser.ParserFactory;
import com.bloctesian.stream.message.parser.PartParser;
import com.bloctesian.stream.message.serializer.MessageSerializer;
import com.bloctesian.utility.ObservableCollection;
import com.bloctesian.utility.ObservableHashSet;

public class StreamBlocks implements Stream, TimerCallback {
  static public final int RequestTimeoutMs = 1500;
  private final Stream output;
  private final Timer timer;

  private final Block base = new Block(new BlockId(BlockType.Base10x10, new Vector(0, 0, -1), Rotation.Deg0));
  private final BlockRepository repository = new BlockRepository(new BlockFactory());
  private final PartParser parser;
  private final MessageSerializer composer = new MessageSerializer(base, repository);
  private final Logger logger;
  private final ObservableHashSet<Block> blocks = new ObservableHashSet<>();

  public StreamBlocks(Stream output, Timer timer, Logger logger) {
    this.output = output;
    this.timer = timer;
    this.logger = logger;
    parser = ParserFactory.produce(repository, new ErrorTranslater(logger));
  }

  public void start() {
    base.getLeds().get(0).setColor(Color.Black);
    base.getLeds().get(1).setColor(Color.Red);
    base.getLeds().get(2).setColor(Color.Green);
    base.getLeds().get(3).setColor(Color.Blue);

    parser.reset();
    sendRequest();
  }

  public void stop() {
    timer.stop(this);
    repository.blocks(new ArrayList<BlockId>());
    blocks.changed(repository.items());
  }

  @Override
  public void newData(List<Byte> data) {
    if (data.isEmpty()) {
      return;
    }

    for (byte symbol : data) {
      parser.receive(symbol);
    }

    blocks.changed(repository.items());

    sendRequest();
  }

  @Override
  public void timeout() {
    if (parser.isFinished()) {
      logger.error("No response from the hardware");
    } else {
      logger.error("Incomplete response from the hardware");
    }
    parser.reset();

    sendRequest();
  }

  private void sendRequest() {
    if (parser.isFinished()) {
      timer.setTimeout(RequestTimeoutMs, this);
      output.newData(composer.serialize());
    }
  }

  public ObservableCollection<Block> getBlocks() {
    return blocks;
  }

  public Block getBase() {
    return base;
  }

}
