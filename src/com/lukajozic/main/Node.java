package com.lukajozic.main;

import java.util.function.Consumer;

public interface Node<E extends Comparable<E>> {
    void addNonFull(E element);

    void splitChild(int childIndex, Node<E> newChild);

    void reversedTraversal(Consumer<? super E> action);

    E getKey(int index);

    void setKey(int index, E element);

    Node<E> getChild(int index);

    void setChild(int index, Node<E> node);

    int getNumNodes();

    void setNumNodes(int numNodes);

    boolean isLeaf();
}
