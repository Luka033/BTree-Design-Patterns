package com.lukajozic;

import java.util.function.Consumer;

public interface Node<E extends Comparable<E>> {

    void reversedTraversal(Consumer<? super E> action);

    void addNonFull(E element);

}
