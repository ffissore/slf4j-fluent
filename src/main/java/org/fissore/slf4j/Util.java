package org.fissore.slf4j;

import java.util.function.Supplier;

public class Util {

    public static LazyArg lazy(Supplier<?> supplier) {
        return new LazyArg(supplier);
    }

}
