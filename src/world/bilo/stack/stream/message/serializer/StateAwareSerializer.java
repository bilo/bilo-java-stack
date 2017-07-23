/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.serializer;

import java.util.List;

public class StateAwareSerializer implements Serializer {
  private final SerializerSelector selector;
  private final Serializer normal;
  private final Serializer errorRecovery;

  public StateAwareSerializer(SerializerSelector selector, Serializer normal, Serializer errorRecovery) {
    this.selector = selector;
    this.normal = normal;
    this.errorRecovery = errorRecovery;
  }

  @Override
  public List<Byte> serialize() {
    if (selector.isNormal()) {
      return normal.serialize();
    } else {
      return errorRecovery.serialize();
    }
  }

}
