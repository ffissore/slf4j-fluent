package org.fissore.slf4j;

@FunctionalInterface
interface TriConsumer<A, B, C> {

  void accept(A a, B b, C c);

}
