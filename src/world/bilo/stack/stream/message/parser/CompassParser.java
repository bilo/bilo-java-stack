/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

import static world.bilo.stack.stream.message.ByteConvertion.toShort;
import static world.bilo.stack.stream.message.Constants.START_COMPASS;

import world.bilo.stack.Vector;

public class CompassParser implements PartParser {
  private final CompassListener listener;
  private int received;
  private State state;
  private byte data[] = { 1, 2, 3, 4, 5, 6 };

  public CompassParser(CompassListener listener) {
    this.listener = listener;
    reset();
  }

  @Override
  public void reset() {
    state = State.Idle;
  }

  @Override
  public boolean isFinished() {
    return state == State.Finished;
  }

  @Override
  public void receive(byte symbol) {
    switch (state) {
    case Idle:
      received = 0;
      state = State.Receiving;
      break;
    case Receiving:
      data[received] = symbol;
      received++;
      if (received == data.length) {
        state = State.Finished;
        listener.compassChanged(toVector(data));
      }
      break;
    case Finished:
      break;
    }
  }

  @Override
  public boolean canStartWith(byte symbol) {
    return symbol == START_COMPASS;
  }

  static private Vector toVector(byte[] data) {
    int x = toShort(data[0], data[1]);
    int y = toShort(data[2], data[3]);
    int z = toShort(data[4], data[5]);
    return new Vector(x, y, z);
  }

  private enum State {
    Idle, Receiving, Finished,
  }

}
