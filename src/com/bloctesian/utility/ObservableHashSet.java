/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.utility;

import java.util.Collection;
import java.util.HashSet;

import com.bloctesian.utility.functional.Function;

public class ObservableHashSet<T> implements ObservableCollection<T> {
  final private UniqueOrderedList<CollectionObserver<T>> listeners = new UniqueOrderedList<>();
  private Collection<T> oldItems = new HashSet<>();

  @Override
  public Collection<T> items() {
    return oldItems;
  }

  @Override
  public UniqueOrderedList<CollectionObserver<T>> listener() {
    return listeners;
  }

  public void changed(Collection<T> newItems) {
    Collection<T> removed = new HashSet<>(oldItems);
    removed.removeAll(newItems);

    Collection<T> added = new HashSet<>(newItems);
    added.removeAll(oldItems);

    oldItems = new HashSet<>(newItems);

    notifyIf(!removed.isEmpty(), removedFunction(removed));
    notifyIf(!added.isEmpty(), addedFunction(added));
  }

  private void notifyIf(boolean predicate, Function<CollectionObserver<T>> function) {
    if (predicate) {
      listeners.apply(function);
    }
  }

  private Function<CollectionObserver<T>> addedFunction(final Collection<T> added) {
    return new Function<CollectionObserver<T>>() {
      @Override
      public void execute(CollectionObserver<T> item) {
        item.added(added);
      }
    };
  }

  private Function<CollectionObserver<T>> removedFunction(final Collection<T> removed) {
    return new Function<CollectionObserver<T>>() {
      @Override
      public void execute(CollectionObserver<T> item) {
        item.removed(removed);
      }
    };
  }

}
