/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import world.bilo.stack.Block;
import world.bilo.stack.BlockId;
import world.bilo.stack.BlockType;
import world.bilo.stack.Logger;
import world.bilo.stack.Rotation;
import world.bilo.stack.RotationListener;
import world.bilo.stack.Timer;
import world.bilo.stack.TimerCallback;
import world.bilo.stack.Vector;
import world.bilo.stack.internal.BlockFactory;
import world.bilo.stack.internal.BlockRepository;
import world.bilo.stack.internal.BlockStateDistributor;
import world.bilo.stack.internal.ErrorDistributor;
import world.bilo.stack.stream.message.parser.ParserFactory;
import world.bilo.stack.stream.message.parser.PartParser;
import world.bilo.stack.stream.message.parser.ReceiveState;
import world.bilo.stack.stream.message.serializer.ErrorRecoverySerializer;
import world.bilo.stack.stream.message.serializer.MessageSerializer;
import world.bilo.stack.stream.message.serializer.Serializer;
import world.bilo.stack.stream.message.serializer.StateAwareSerializer;
import world.bilo.stack.utility.ObservableCollection;
import world.bilo.stack.utility.ObservableHashSet;
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
