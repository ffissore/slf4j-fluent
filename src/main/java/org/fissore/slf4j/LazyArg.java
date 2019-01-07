package org.fissore.slf4j;

import java.util.function.Supplier;

public class LazyArg {

    private final Supplier<?> supplier;

    public LazyArg(Supplier<?> supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return supplier.get().toString();
    }
}
