package com.lukajozic.main;

import java.util.Comparator;
import java.util.function.Consumer;

public class NullNode<E extends Comparable<E>> implements Node<E> {
    private int numNodes;
    private final E[] keys;
    private final Node<E>[] children;
    private final boolean isLeaf;
    private final Comparator<? super E> comparator;
    private final int order;

    public NullNode(int order, boolean isLeaf, int numNodes, Comparator<? super E> comparator) {
        this.keys = (E[]) new Comparable[2 * order - 1];
        this.children = new Node[2 * order];
        this.isLeaf = isLeaf;
        this.numNodes = numNodes;
        this.comparator = comparator;
        this.order = order;
    }

    @Override
    public void addNonFull(E element) {
        // do nothing
    }

    @Override
    public void splitChild(int childIndex, Node<E> newChild) {
        // do nothing
    }

    @Override
    public void reversedTraversal(Consumer<? super E> action) {
        // do nothing
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public E getKey(int index) {
        return this.keys[index];
    }

    @Override
    public void setKey(int index, E element) {
        this.keys[index] = element;
    }

    @Override
    public Node<E> getChild(int index) {
        return children[index];
    }

    @Override
    public void setChild(int index, Node<E> node) {
        this.children[index] = node;
    }

    public int getNumNodes() {
        return numNodes;
    }

    @Override
    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }

    public boolean isLeaf() {
        return isLeaf;
    }
}
