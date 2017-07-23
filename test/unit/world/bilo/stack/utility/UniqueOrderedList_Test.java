/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.utility;

import static helper.Lists.list;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import world.bilo.util.UniqueOrderedList;
import world.bilo.util.functional.Function;

public class UniqueOrderedList_Test {
  Object item1 = mock(Object.class);
  Object item2 = mock(Object.class);
  final private UniqueOrderedList<Object> testee = new UniqueOrderedList<>();

  @Test(expected = IllegalArgumentException.class)
  public void can_not_remove_item_that_is_not_in_list() {
    testee.remove(item1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void can_not_add_same_item_twice() {
    testee.add(item1);

    testee.add(item1);
  }

  @Test
  public void can_check_if_it_contains_an_item() {
    testee.add(item1);

    assertTrue(testee.contains(item1));
    assertFalse(testee.contains(item2));
  }

  @Test
  public void can_add_item_once() {
    testee.add(item1);

    assertTrue(testee.contains(item1));
  }

  @Test
  public void can_remove_item() {
    testee.add(item1);

    testee.remove(item1);

    assertFalse(testee.contains(item1));
  }

  @Test
  public void apply_function_to_all_items() {
    final List<Object> collected = new ArrayList<>();
    testee.add(item1);
    testee.add(item2);

    testee.apply(new Function<Object>() {
      @Override
      public void execute(Object item) {
        collected.add(item);
      }
    });

    assertEquals(list(item1, item2), collected);
  }

  @Test
  public void can_iterate_over_items() {
    List<Object> collected = new ArrayList<>();
    testee.add(item1);
    testee.add(item2);

    for (Object item : testee) {
      collected.add(item);
    }

    assertEquals(list(item1, item2), collected);
  }

}
