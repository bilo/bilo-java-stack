/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.utility;

import java.util.Collection;

public interface ObservableCollection<T> {

  public Collection<T> items();

  public UniqueOrderedList<CollectionObserver<T>> listener();

}
