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
        this.children = new Node[2 * order];
        this.isLeaf = isLeaf;
        this.numNodes = numNodes;
        this.comparator = comparator;
        this.order = order;
        initChildren();
    }

    private void initChildren() {
        for(int i = 0; i < this.children.length - 1; i++) {
            this.children[i] = new NullNode<>(this.order, true, 0, this::compare);
        }
    }

    /**
     * Add a new key in the subtree rooted with the current Node.
     *
     * @param element the student to be inserted
     * @param parentNode
     */
    @Override
    public void addNonFull(E element, Node<E> parentNode) {
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
            if (this.children[i - 1].getNumNodes() == this.order) {
                this.splitChild(i, this.children[i - 1]);
                if (element.compareTo(this.keys[i - 1]) > 0) {
                    i++;
                }
            }
            this.children[i - 1].addNonFull(element, this.children[i - 1]);
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
        childNode.getKeys()[0] = newChild.getKeys()[2];

        childNode.getChildren()[1] = newChild.getChildren()[3];
        childNode.getChildren()[0] = newChild.getChildren()[2];
        newChild.setNumNodes(1);

        for (int i = this.numNodes + 1; i >= childIndex + 1; i--) {
            this.children[i] = this.children[i - 1];
            this.keys[i - 1] = this.keys[i - 2];
        }
        this.children[childIndex] = childNode;
        this.keys[childIndex - 1] = newChild.getKeys()[1];
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

    //    @Override
//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for(Node<E> child : this.getChildren()) {
//            stringBuilder.append(child.toString() + keys[0]);
//        }
//        return stringBuilder.toString(); // + children[1].toString() + keys[1].toString() + children[2].toString();
//        return children[0].toString() + keys[0] + children[1].toString();// + keys[1].toString() + children[2].toString();
//        int i = 0;
//        while(i < this.numNodes) {
//            this.children[i].toString();
//            return this.keys[i].toString();
//            i++;
//        }
//        return this.children[i].toString();
//    }

    @Override
    public StringBuilder toString(StringBuilder stringBuilder) {
        int i = 0;
        while(i < this.numNodes) {
            this.children[i].toString(stringBuilder);
            stringBuilder.append(this.keys[i].toString()).append(" ");
            i++;
        }
        this.children[i].toString(stringBuilder);
        return stringBuilder;
    }

    /**
     * {@inheritDoc}
     */
    final int compare(Object k1, Object k2) {
        return comparator == null ? ((Comparable<? super E>)k1).compareTo((E)k2)
                : comparator.compare((E)k1, (E)k2);
    }

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
