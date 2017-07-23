/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.stream.message.serializer;

import static helper.Lists.list;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import world.bilo.util.functional.UnaryFunction;

public class ListSerializer_Test {
  private final Object item1 = mock(Object.class);
  private final Object item2 = mock(Object.class);
  private final List<Object> items = new ArrayList<>();
  private final UnaryFunction<List<Byte>, Object> itemSerializer = mock(UnaryFunction.class);
  private final ListSerializer<Object> testee = new ListSerializer<>(items, itemSerializer);

  @Test
  public void return_nothing_for_empty_list() {
    assertEquals(list(), testee.serialize());
  }

  @Test
  public void forward_items_to_serializer() {
    when(itemSerializer.execute(any(Object.class))).thenReturn(list());
    fillList();

    testee.serialize();

    verify(itemSerializer).execute(item1);
    verify(itemSerializer).execute(item2);
  }

  @Test
  public void use_return_value_from_serializer() {
    fillList();
    when(itemSerializer.execute(item1)).thenReturn(list(12));
    when(itemSerializer.execute(item2)).thenReturn(list(34));

    assertEquals(list(12, 34), testee.serialize());
  }

  private void fillList() {
    items.add(item1);
    items.add(item2);
  }

}
