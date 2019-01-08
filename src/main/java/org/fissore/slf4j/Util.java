package org.fissore.slf4j;

import java.util.function.Supplier;

public class Util {

  public static Supplier<?> lazy(Supplier<?> supplier) {
    return supplier;
  }

}
