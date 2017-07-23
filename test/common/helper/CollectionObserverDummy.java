/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import world.bilo.stack.utility.CollectionObserver;

public class CollectionObserverDummy<T> implements CollectionObserver<T> {
  public final List<T> items = new ArrayList<>();

  @Override
  public void added(Collection<T> added) {
    items.addAll(added);
  }

  @Override
  public void removed(Collection<T> removed) {
    items.removeAll(removed);
  }

}