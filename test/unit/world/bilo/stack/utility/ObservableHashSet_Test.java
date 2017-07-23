/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.utility;

import static helper.Lists.list;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.mockito.InOrder;

public class ObservableHashSet_Test {
  final private ObservableHashSet<Byte> testee = new ObservableHashSet<>();
  final private CollectionObserver<Byte> listener = mock(CollectionObserver.class);
  final private InOrder inOrder = inOrder(listener);
  final private Collection<Byte> list12 = new HashSet<>(list(1, 2));
  final private Collection<Byte> list1 = new HashSet<>(list(1));
  final private Collection<Byte> list2 = new HashSet<>(list(2));

  @Test
  public void notifies_listener_when_items_are_added() {
    testee.changed(list1);
    testee.listener().add(listener);

    testee.changed(list12);

    verify(listener).added(list2);
  }

  @Test
  public void does_not_notify_if_nothing_was_added() {
    testee.changed(list12);
    testee.listener().add(listener);

    testee.changed(list12);

    verify(listener, never()).added(any(Collection.class));
  }

  @Test
  public void notifies_listener_when_items_are_removed() {
    testee.changed(list12);
    testee.listener().add(listener);

    testee.changed(list1);

    verify(listener).removed(list2);
  }

  @Test
  public void does_not_notify_if_nothing_was_removed() {
    testee.changed(list12);
    testee.listener().add(listener);

    testee.changed(list12);

    verify(listener, never()).removed(any(Collection.class));
  }

  @Test
  public void notifies_first_about_removed_then_about_added_items() {
    testee.changed(list1);
    testee.listener().add(listener);

    testee.changed(list2);

    inOrder.verify(listener).removed(list1);
    inOrder.verify(listener).added(list2);
  }

}
