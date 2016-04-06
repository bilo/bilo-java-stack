/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class Vector_Test {
  @Test
  public void can_create_a_vector_with_all_values() {
    Vector testee = new Vector(76, -13, 9);

    assertEquals(76, testee.x);
    assertEquals(-13, testee.y);
    assertEquals(9, testee.z);
  }

  @Test
  public void create_vector_by_dimension() {
    assertEquals(new Vector(1, 0, 0), new Vector(Dimension.X));
    assertEquals(new Vector(0, 1, 0), new Vector(Dimension.Y));
    assertEquals(new Vector(0, 0, 1), new Vector(Dimension.Z));
  }

  @Test
  public void vectors_are_equal_by_value() {
    assertEquals(new Vector(12, 99, 57), new Vector(12, 99, 57));
  }

  @Test
  public void vectors_are_not_equal_when_one_value_is_not_equal() {
    assertNotEquals(new Vector(42, 0, 0), new Vector(57, 0, 0));
    assertNotEquals(new Vector(0, 42, 0), new Vector(0, 57, 0));
    assertNotEquals(new Vector(0, 0, 42), new Vector(0, 0, 57));
  }

  @Test
  public void equal_vectors_have_the_same_hash_code() {
    assertEquals((new Vector(12, 57, 78)).hashCode(), (new Vector(12, 57, 78)).hashCode());
  }

  @Test
  public void not_equal_vectors_have_not_the_same_hash_code() {
    assertNotEquals((new Vector(42, 0, 0)).hashCode(), (new Vector(57, 0, 0)).hashCode());
    assertNotEquals((new Vector(0, 42, 0)).hashCode(), (new Vector(0, 57, 0)).hashCode());
    assertNotEquals((new Vector(0, 0, 42)).hashCode(), (new Vector(0, 0, 57)).hashCode());
  }

  @Test
  public void has_a_readable_toString_output() {
    assertEquals("(12/34/57)", (new Vector(12, 34, 57)).toString());
  }

}
