/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.serializer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class StateAwareSerializer_Test {
  private final SerializerSelector selector = mock(SerializerSelector.class);
  private final Serializer normal = mock(Serializer.class);
  private final Serializer errorRecovery = mock(Serializer.class);
  private final StateAwareSerializer testee = new StateAwareSerializer(selector, normal, errorRecovery);

  @Test
  public void forwards_serialization_to_normal_when_told_so() {
    when(selector.isNormal()).thenReturn(true);

    testee.serialize();

    verify(normal).serialize();
  }

  @Test
  public void forwards_serialization_to_errorRecovery_when_told_so() {
    when(selector.isNormal()).thenReturn(false);

    testee.serialize();

    verify(errorRecovery).serialize();
  }

}
