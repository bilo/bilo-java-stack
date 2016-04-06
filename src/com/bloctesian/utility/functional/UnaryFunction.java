/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package com.bloctesian.utility.functional;

public interface UnaryFunction<Result, Argument> {
  public Result execute(Argument value);
}
