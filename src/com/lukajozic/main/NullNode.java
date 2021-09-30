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

    /**
     * Add a new key in the subtree rooted with the current Node.
     *
     * @param element the student to be inserted
     * @param parentNode
     */
    @Override
    public void addNonFull(E element, Node<E> parentNode) {
        // do nothing
    }

    /**
     * Split a child Node
     *
     * @param childIndex the index of the element
     * @param newChild   the new child causing the split
     */
    @Override
    public void splitChild(int childIndex, Node<E> newChild) {
        // do nothing
    }

    /**
     * Reversed recursive in-order traversal on this node. Performs the given action for each element
     * until all elements have been processed or the action throws an exception.
     *
     * @param action to be performed on each element
     */
    @Override
    public void reversedTraversal(Consumer<? super E> action) {
        // do nothing
    }

    @Override
    public StringBuilder toString(StringBuilder stringBuilder) {
        return stringBuilder;
    }

//    @Override
//    public String toString() {
//        return "";
//    }

    public Node<E>[] getChildren() {
        return children;
    }

    @Override
    public void setChildren(int index, Node<E> node) {
        this.children[index] = node;
    }

    public int getNumNodes() {
        return numNodes;
    }

    @Override
    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }

    public E[] getKeys() {
        return keys;
    }

    public boolean isLeaf() {
        return isLeaf;
    }
}
