/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack.stream.message.parser;

interface IsFinishedStrategy {
  public boolean isFinished(PartParser active);
}