package com.lukajozic.main;

import java.util.function.Consumer;

public interface Node<E extends Comparable<E>> {
    void addNonFull(E element, Node<E> parentNode);

    void splitChild(int childIndex, Node<E> newChild);

    void reversedTraversal(Consumer<? super E> action);

    StringBuilder toString(StringBuilder stringBuilder);

    Node<E>[] getChildren();

    void setChildren(int index, Node<E> node);

    int getNumNodes();

    void setNumNodes(int numNodes);

    E[] getKeys();

    boolean isLeaf();
}
