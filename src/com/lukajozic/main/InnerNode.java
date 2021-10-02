package com.lukajozic.main;

import java.util.Comparator;
import java.util.function.Consumer;

public class InnerNode<E extends Comparable<E>> implements Node<E> {
    private int numNodes;
    private final E[] keys;
    private final Node<E>[] children;
    private final boolean isLeaf;
    private final Comparator<? super E> comparator;
    private final int order;

    public InnerNode(int order, boolean isLeaf, int numNodes, Comparator<? super E> comparator) {
        this.keys = (E[]) new Comparable[2 * order - 1];
        this.children =  new Node[2 * order];
        this.isLeaf = isLeaf;
        this.numNodes = numNodes;
        this.comparator = comparator;
        this.order = order;
        initChildren();
    }

    /**
     * Helper function to initialize all the children as NullNodes
     */
    private void initChildren() {
        for(int i = 0; i < this.children.length - 1; i++) {
            this.children[i] = new NullNode<>(order, true, 0, this::compare);
        }
    }

    /**
     * Add a new key in the subtree rooted with the current Node.
     *
     * @param element the student to be inserted
     */
    @Override
    public void addNonFull(E element) {
        int i = this.numNodes;
        if (this.isLeaf) {
            while (i >= 1 && compare(element, this.keys[i - 1]) < 0) {
                this.keys[i] = this.keys[i - 1];
                i--;
            }
            this.keys[i] = element;
            this.numNodes++;
        } else {
            while (i >= 1 && compare(element, this.keys[i - 1]) < 0) {
                i--;
            }
            i++;
            if (this.children[i - 1].getNumNodes() == order) {
                this.splitChild(i, this.children[i - 1]);
                if (element.compareTo(this.keys[i - 1]) > 0) {
                    i++;
                }
            }
            this.children[i - 1].addNonFull(element);
        }
    }

    /**
     * Split a child Node
     *
     * @param childIndex the index of the element
     * @param newChild   the new child causing the split
     */
    @Override
    public void splitChild(int childIndex, Node<E> newChild) {
        Node<E> childNode = new InnerNode<E>(order, newChild.isLeaf(), 1, this::compare);
        childNode.setKey(0, newChild.getKey(2));

        childNode.setChild(1, newChild.getChild(3));
        childNode.setChild(0, newChild.getChild(2));
        newChild.setNumNodes(1);

        for (int i = this.numNodes + 1; i >= childIndex + 1; i--) {
            this.children[i] = this.children[i - 1];
            this.keys[i - 1] = this.keys[i - 2];
        }
        this.children[childIndex] = childNode;
        this.keys[childIndex - 1] = newChild.getKey(1);
        this.numNodes++;
    }

    /**
     * Reversed recursive in-order traversal on this node. Performs the given action for each element
     * until all elements have been processed or the action throws an exception.
     *
     * @param action to be performed on each element
     */
    @Override
    public void reversedTraversal(Consumer<? super E> action) {
        int i = this.numNodes;
        while(i > 0) {
            this.children[i].reversedTraversal(action);
            action.accept(this.keys[i - 1]);
            i--;
        }
        this.children[i].reversedTraversal(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        int i = 0;
        while(i < this.numNodes) {
            str.append(children[i].toString()).append(this.keys[i]).append(" ");
            i++;
        }
        return str + this.children[i].toString();
    }

    /**
     * {@inheritDoc}
     */
    final int compare(Object k1, Object k2) {
        return comparator == null ? ((Comparable<? super E>)k1).compareTo((E)k2)
                : comparator.compare((E)k1, (E)k2);
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
