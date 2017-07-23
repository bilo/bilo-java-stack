/*
 * Copyright 2017 Urs Fässler
 * SPDX-License-Identifier: Apache-2.0
 */

package helper;

import static world.bilo.stack.stream.message.ByteConvertion.toByte;

import java.util.ArrayList;
import java.util.List;

public class Lists {

  static public <T> List<T> list(Class<T> kind) {
    List<T> result = new ArrayList<>();
    return result;
  }

  static public <T> List<T> list(T a0) {
    List<T> result = new ArrayList<>();
    result.add(a0);
    return result;
  }

  static public <T> List<T> list(T a0, T a1) {
    List<T> result = list(a0);
    result.add(a1);
    return result;
  }

  static public <T> List<T> list(T a0, T a1, T a2) {
    List<T> result = list(a0, a1);
    result.add(a2);
    return result;
  }

  static public <T> List<T> list(T a0, T a1, T a2, T a3) {
    List<T> result = list(a0, a1, a2);
    result.add(a3);
    return result;
  }

  static public <T> List<T> list(T a0, T a1, T a2, T a3, T a4, T a5, T a6, T a7) {
    List<T> result = list(a0, a1, a2, a3);
    result.add(a4);
    result.add(a5);
    result.add(a6);
    result.add(a7);
    return result;
  }

  static public List<Byte> list() {
    return new ArrayList<>();
  }

  static public List<Byte> list(int a0) {
    List<Byte> result = list();
    result.add(toByte(a0));
    return result;
  }

  static public List<Byte> list(int a0, int a1) {
    List<Byte> result = list(a0);
    result.add(toByte(a1));
    return result;
  }

  static public List<Byte> list(int a0, int a1, int a2) {
    List<Byte> result = list(a0, a1);
    result.add(toByte(a2));
    return result;
  }

  static public List<Byte> list(int a0, int a1, int a2, int a3) {
    List<Byte> result = list(a0, a1, a2);
    result.add(toByte(a3));
    return result;
  }

  static public List<Byte> list(int a0, int a1, int a2, int a3, int a4) {
    List<Byte> result = list(a0, a1, a2, a3);
    result.add(toByte(a4));
    return result;
  }

  static public List<Byte> list(int a0, int a1, int a2, int a3, int a4, int a5) {
    List<Byte> result = list(a0, a1, a2, a3, a4);
    result.add(toByte(a5));
    return result;
  }

  static public List<Byte> list(int a0, int a1, int a2, int a3, int a4, int a5, int a6) {
    List<Byte> result = list(a0, a1, a2, a3, a4, a5);
    result.add(toByte(a6));
    return result;
  }

  static public List<Byte> list(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8) {
    List<Byte> result = list(a0, a1, a2, a3, a4, a5);
    result.add(toByte(a6));
    result.add(toByte(a7));
    result.add(toByte(a8));
    return result;
  }

  static public List<Byte> list(int a0, int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8, int a9) {
    List<Byte> result = list(a0, a1, a2, a3, a4, a5, a6, a7, a8);
    result.add(toByte(a9));
    return result;
  }

}
