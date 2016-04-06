/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Vector_operation_Test {
  @Test
  public void rotate_0_degree() {
    assertEquals(new Vector(0, 0, 0), new Vector(0, 0, 0).rotateOnZeroBy(Rotation.Deg0));
    assertEquals(new Vector(0, 0, 1), new Vector(0, 0, 1).rotateOnZeroBy(Rotation.Deg0));
    assertEquals(new Vector(0, 1, 0), new Vector(0, 1, 0).rotateOnZeroBy(Rotation.Deg0));
    assertEquals(new Vector(0, 1, 1), new Vector(0, 1, 1).rotateOnZeroBy(Rotation.Deg0));
    assertEquals(new Vector(1, 0, 0), new Vector(1, 0, 0).rotateOnZeroBy(Rotation.Deg0));
    assertEquals(new Vector(1, 0, 1), new Vector(1, 0, 1).rotateOnZeroBy(Rotation.Deg0));
    assertEquals(new Vector(1, 1, 0), new Vector(1, 1, 0).rotateOnZeroBy(Rotation.Deg0));
    assertEquals(new Vector(1, 1, 1), new Vector(1, 1, 1).rotateOnZeroBy(Rotation.Deg0));
  }

  @Test
  public void rotate_90_degree() {
    assertEquals(new Vector(+0, +0, 0), new Vector(+0, +0, 0).rotateOnZeroBy(Rotation.Deg90));
    assertEquals(new Vector(-1, +0, 0), new Vector(+0, +1, 0).rotateOnZeroBy(Rotation.Deg90));
    assertEquals(new Vector(+0, +1, 0), new Vector(+1, +0, 0).rotateOnZeroBy(Rotation.Deg90));
    assertEquals(new Vector(+1, +0, 0), new Vector(+0, -1, 0).rotateOnZeroBy(Rotation.Deg90));
    assertEquals(new Vector(+0, -1, 0), new Vector(-1, +0, 0).rotateOnZeroBy(Rotation.Deg90));
  }

  @Test
  public void rotate_180_degree() {
    assertEquals(new Vector(+0, +0, 0), new Vector(+0, +0, 0).rotateOnZeroBy(Rotation.Deg180));
    assertEquals(new Vector(-0, -1, 0), new Vector(+0, +1, 0).rotateOnZeroBy(Rotation.Deg180));
    assertEquals(new Vector(-1, -0, 0), new Vector(+1, +0, 0).rotateOnZeroBy(Rotation.Deg180));
    assertEquals(new Vector(+0, +1, 0), new Vector(+0, -1, 0).rotateOnZeroBy(Rotation.Deg180));
    assertEquals(new Vector(+1, +0, 0), new Vector(-1, +0, 0).rotateOnZeroBy(Rotation.Deg180));
  }

  @Test
  public void rotate_270_degree() {
    assertEquals(new Vector(+0, +0, 0), new Vector(+0, +0, 0).rotateOnZeroBy(Rotation.Deg270));
    assertEquals(new Vector(+1, +0, 0), new Vector(+0, +1, 0).rotateOnZeroBy(Rotation.Deg270));
    assertEquals(new Vector(+0, -1, 0), new Vector(+1, +0, 0).rotateOnZeroBy(Rotation.Deg270));
    assertEquals(new Vector(-1, +0, 0), new Vector(+0, -1, 0).rotateOnZeroBy(Rotation.Deg270));
    assertEquals(new Vector(+0, +1, 0), new Vector(-1, +0, 0).rotateOnZeroBy(Rotation.Deg270));
  }

  @Test
  public void rotation_does_not_change_the_original_vector() {
    Vector testee = new Vector(0, 1, 0);

    testee.rotateOnZeroBy(Rotation.Deg90);

    assertEquals(new Vector(0, 1, 0), testee);
  }

  @Test
  public void translate() {
    assertEquals(new Vector(0, 0, 0), new Vector(0, 0, 0).translate(new Vector(0, 0, 0)));
    assertEquals(new Vector(1, 0, 0), new Vector(1, 0, 0).translate(new Vector(0, 0, 0)));
    assertEquals(new Vector(0, 1, 0), new Vector(0, 1, 0).translate(new Vector(0, 0, 0)));
    assertEquals(new Vector(0, 0, 1), new Vector(0, 0, 1).translate(new Vector(0, 0, 0)));
    assertEquals(new Vector(1, 0, 0), new Vector(0, 0, 0).translate(new Vector(1, 0, 0)));
    assertEquals(new Vector(0, 1, 0), new Vector(0, 0, 0).translate(new Vector(0, 1, 0)));
    assertEquals(new Vector(0, 0, 1), new Vector(0, 0, 0).translate(new Vector(0, 0, 1)));
  }

  @Test
  public void translation_does_not_change_the_original_vector() {
    Vector testee = new Vector(0, 1, 0);

    testee.translate(new Vector(1, 1, 1));

    assertEquals(new Vector(0, 1, 0), testee);
  }

  @Test
  public void translation_does_not_change_the_displacement_vector() {
    Vector testee = new Vector(0, 1, 0);
    Vector displacement = new Vector(1, 1, 1);

    testee.translate(displacement);

    assertEquals(new Vector(1, 1, 1), displacement);
  }
}
