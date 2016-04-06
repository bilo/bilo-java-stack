/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.utility;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.bloctesian.utility.functional.Function;

public class UniqueOrderedList<T> implements Iterable<T> {
  final private List<T> items = new LinkedList<>();

  public void remove(T item) {
    if (!items.contains(item)) {
      throw new IllegalArgumentException("item not registered: " + item);
    }

    items.remove(item);
  }

  public void add(T item) {
    if (items.contains(item)) {
      throw new IllegalArgumentException("can not add same item twice: " + item);
    }

    items.add(item);
  }

  public boolean contains(T item) {
    return items.contains(item);
  }

  public void apply(Function<T> function) {
    for (T item : items) {
      function.execute(item);
    }
  }

  @Override
  public Iterator<T> iterator() {
    return items.iterator();
  }

}
