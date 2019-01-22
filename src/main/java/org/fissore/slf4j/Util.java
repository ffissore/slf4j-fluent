package org.fissore.slf4j;

import java.util.function.Supplier;

public class Util {

  /**
   * Casts a lambda to a Supplier. Best used once statically imported.
   * <pre>
   * import static org.fissore.slf4j.Util.lazy;
   *
   * ...
   *
   * logger.error().log("This is a {}", lazy(() -> "test"));
   * </pre>
   *
   * @param supplier the lambda to cast
   * @return a Supplier
   */
  public static Supplier<?> lazy(Supplier<?> supplier) {
    return supplier;
  }

}
