/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.support;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.bloctesian.TimerCallback;

public class PollTimer_Test {
  private final Time time = mock(Time.class);
  private final PollTimer testee = new PollTimer(time);
  private final TimerCallback callback = mock(TimerCallback.class);

  @Test
  public void does_not_fire_when_time_is_not_up() {
    when(time.getTimeMs()).thenReturn(0l).thenReturn(9l);
    testee.setTimeout(10, callback);

    testee.poll();

    verify(callback, times(0)).timeout();
  }

  @Test
  public void does_fire_when_time_is_up() {
    when(time.getTimeMs()).thenReturn(0l).thenReturn(10l);
    testee.setTimeout(10, callback);

    testee.poll();

    verify(callback, times(1)).timeout();
  }

  @Test
  public void uses_relative_time() {
    when(time.getTimeMs()).thenReturn(1000l).thenReturn(1009l).thenReturn(1010l);
    testee.setTimeout(10, callback);

    testee.poll();
    verify(callback, times(0)).timeout();

    testee.poll();
    verify(callback, times(1)).timeout();
  }

  @Test
  public void does_only_fire_once() {
    when(time.getTimeMs()).thenReturn(0l).thenReturn(10l).thenReturn(10l);
    testee.setTimeout(10, callback);
    testee.poll();

    testee.poll();

    verify(callback, times(1)).timeout();
  }

  @Test
  public void does_not_fire_when_time_is_up_but_stopped() {
    when(time.getTimeMs()).thenReturn(0l).thenReturn(10l);
    testee.setTimeout(10, callback);

    testee.stop(callback);
    testee.poll();

    verify(callback, never()).timeout();
  }

  @Test
  public void does_fire_when_time_is_up_but_and_unrelated_callback_was_stopped() {
    when(time.getTimeMs()).thenReturn(0l).thenReturn(10l);
    testee.setTimeout(10, callback);

    testee.stop(mock(TimerCallback.class));
    testee.poll();

    verify(callback, times(1)).timeout();
  }

}
