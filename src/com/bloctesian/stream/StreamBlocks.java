/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bloctesian.Block;
import com.bloctesian.BlockId;
import com.bloctesian.BlockType;
import com.bloctesian.Logger;
import com.bloctesian.Rotation;
import com.bloctesian.RotationListener;
import com.bloctesian.Timer;
import com.bloctesian.TimerCallback;
import com.bloctesian.Vector;
import com.bloctesian.internal.BlockFactory;
import com.bloctesian.internal.BlockRepository;
import com.bloctesian.internal.BlockStateDistributor;
import com.bloctesian.internal.ErrorDistributor;
import com.bloctesian.stream.message.parser.ParserFactory;
import com.bloctesian.stream.message.parser.PartParser;
import com.bloctesian.stream.message.parser.ReceiveState;
import com.bloctesian.stream.message.serializer.ErrorRecoverySerializer;
import com.bloctesian.stream.message.serializer.MessageSerializer;
import com.bloctesian.stream.message.serializer.Serializer;
import com.bloctesian.stream.message.serializer.StateAwareSerializer;
import com.bloctesian.utility.ObservableCollection;
import com.bloctesian.utility.ObservableHashSet;

import world.bilo.util.UniqueOrderedList;

public class StreamBlocks implements Stream, TimerCallback {
  static public final int RequestTimeoutMs = 1500;
  private final Stream output;
  private final Timer timer;

  private final Block base = new Block(new BlockId(BlockType.Base10x10, new Vector(0, 0, -1), Rotation.Deg0));
  private final BlockRepository repository = new BlockRepository(new BlockFactory());
  private final MessageSerializer composer = new MessageSerializer(base, repository);
  private final Logger logger;
  private final ObservableHashSet<Block> blocks = new ObservableHashSet<>();
  private final Serializer errorComposer = new ErrorRecoverySerializer();
  private final ReceiveState receiveState = new ReceiveState();
  private final StateAwareSerializer stateComposer = new StateAwareSerializer(receiveState, composer, errorComposer);
  private final ErrorDistributor error = new ErrorDistributor();
  private final BlockStateDistributor blockState = new BlockStateDistributor();
  private final RotationDistributor rotationDistributor = new RotationDistributor();
  private final PartParser parser;

  public StreamBlocks(Stream output, Timer timer, Logger logger) {
    this.output = output;
    this.timer = timer;
    this.logger = logger;
    error.getListener().add(receiveState);
    error.getListener().add(new ErrorTranslater(logger));
    blockState.getListener().add(receiveState);
    blockState.getListener().add(repository);
    parser = ParserFactory.produce(blockState, rotationDistributor, error, logger);
  }

  public void start() {
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

    logger.debug("received: " + hexString(data));

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

    receiveState.timeout();

    sendRequest();
  }

  private void sendRequest() {
    if (parser.isFinished()) {
      timer.setTimeout(RequestTimeoutMs, this);
      List<Byte> data = stateComposer.serialize();
      output.newData(data);
      logger.debug("sent: " + hexString(data));
    }
  }

  public ObservableCollection<Block> getBlocks() {
    return blocks;
  }

  public Block getBase() {
    return base;
  }

  private String hexString(Collection<Byte> data) {
    String result = "";
    boolean first = true;
    for (Byte itr : data) {
      if (first) {
        first = false;
      } else {
        result += " ";
      }
      result += hexString(itr);
    }
    return result;
  }

  private String hexString(Byte value) {
    int upper = (value >> 4) & 0x0f;
    int lower = (value >> 0) & 0x0f;
    return Integer.toHexString(upper) + Integer.toHexString(lower);
  }

  public UniqueOrderedList<RotationListener> rotationListener() {
    return rotationDistributor.listeners;
  }

  private class RotationDistributor implements RotationListener {
    final UniqueOrderedList<RotationListener> listeners = new UniqueOrderedList<>();

    @Override
    public void rotationChanged(double value) {
      for (RotationListener itr : listeners) {
        itr.rotationChanged(value);
      }
    }

  }

}
