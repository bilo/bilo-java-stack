/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.support;

import com.bloctesian.Timer;
import com.bloctesian.TimerCallback;

public class PollTimer implements Timer {
  private final Time time;
  private long timeoutTime = 0;
  private TimerCallback callback = null;

  public PollTimer(Time time) {
    this.time = time;
  }

  @Override
  public void setTimeout(int milliseconds, TimerCallback callback) {
    timeoutTime = time.getTimeMs() + milliseconds;
    this.callback = callback;
  }

  public void poll() {
    if (callback != null) {
      long currentTime = time.getTimeMs();
      if (timeoutTime <= currentTime) {
        TimerCallback fireCallback = callback;
        callback = null;
        fireCallback.timeout();
      }
    }
  }

  @Override
  public void stop(TimerCallback callback) {
    if (this.callback == callback) {
      this.callback = null;
    }
  }

}
