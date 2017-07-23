/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class BlockId_Test {
  final private Vector position = new Vector(5, 6, 7);
  final private Vector differentPosition = new Vector(4, 5, 6);

  @Test
  public void can_create_a_block_with_all_values() {
    BlockId testee = new BlockId(BlockType.Block2x2, position, Rotation.Deg270);

    assertEquals(BlockType.Block2x2, testee.type);
    assertEquals(position, testee.position);
    assertEquals(Rotation.Deg270, testee.rotation);
  }

  @Test
  public void blocks_are_equal_by_value() {
    assertEquals(new BlockId(BlockType.Block2x2, position, Rotation.Deg270), new BlockId(BlockType.Block2x2, position, Rotation.Deg270));
  }

  @Test
  public void blocks_are_not_equal_when_one_value_is_not_equal() {
    assertNotEquals(new BlockId(BlockType.Block2x2, Vector.Zero, Rotation.Deg0), new BlockId(BlockType.Block4x2, Vector.Zero, Rotation.Deg0));
    assertNotEquals(new BlockId(BlockType.Block2x2, position, Rotation.Deg0), new BlockId(BlockType.Block2x2, differentPosition, Rotation.Deg0));
    assertNotEquals(new BlockId(BlockType.Block4x2, Vector.Zero, Rotation.Deg90), new BlockId(BlockType.Block4x2, Vector.Zero, Rotation.Deg270));
  }

  @Test
  public void equal_blocks_have_the_same_hash_code() {
    assertEquals((new BlockId(BlockType.Block4x2, position, Rotation.Deg270)).hashCode(), (new BlockId(BlockType.Block4x2, position, Rotation.Deg270)).hashCode());
  }

  @Test
  public void not_equal_blocks_have_not_the_same_hash_code() {
    assertNotEquals((new BlockId(BlockType.Block4x2, Vector.Zero, Rotation.Deg0)).hashCode(), (new BlockId(BlockType.Block2x2, Vector.Zero, Rotation.Deg0)).hashCode());
    assertNotEquals((new BlockId(BlockType.Block4x2, position, Rotation.Deg0)).hashCode(), (new BlockId(BlockType.Block4x2, differentPosition, Rotation.Deg0)).hashCode());
    assertNotEquals((new BlockId(BlockType.Block4x2, Vector.Zero, Rotation.Deg90)).hashCode(), (new BlockId(BlockType.Block4x2, Vector.Zero, Rotation.Deg270)).hashCode());
  }

  @Test
  public void has_a_readable_toString_output() {
    assertEquals("BlockId{type:4*2 position:(5/6/7) rotation:90}", (new BlockId(BlockType.Block4x2, position, Rotation.Deg90)).toString());
  }

}
