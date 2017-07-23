/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package world.bilo.stack;

public interface Timer {
  public void setTimeout(int milliseconds, TimerCallback callback);

  public void stop(TimerCallback callback);

}