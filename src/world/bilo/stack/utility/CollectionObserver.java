/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.utility;

import java.util.Collection;

public interface CollectionObserver<T> {

  public void added(Collection<T> added);

  public void removed(Collection<T> removed);

}
