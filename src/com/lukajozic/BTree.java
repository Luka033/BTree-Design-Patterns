package com.lukajozic;

import java.util.*;
import java.util.function.Consumer;

// Where to implement NullObject pattern

/**
 * Generic B-Tree Class
 * CS-635
 *
 * @author Luka Jozic
 */
class BTree<T extends Comparable<T>> implements SortedTreeSet<T> {
    private static final int DEFAULT_ORDER = 3;
    public BTreeNode<T> root;
    private final int order;
    private int size;
    private final Comparator<? super T> comparator;


    BTree() {
        this(DEFAULT_ORDER, null);
    }

    BTree(int order) {
        this(order, null);
    }

    BTree(int order, Comparator<? super T> comparator) {
        this.order = order;
        this.root = new BTreeNode<>(true, 0);
        this.comparator = comparator;
    }

    /**
     * Add an element to the B-Tree
     *
     * @param element to be inserted in the B-Tree
     * @return boolean indicating whether the add was successful
     */
    @Override
    public boolean add(T element) {
        try {
            BTreeNode<T> root = this.root;
            if (root.numNodes == this.order) {
                BTreeNode<T> parentNode = new BTreeNode<>(false, 0);
                this.root = parentNode;
                parentNode.children[0] = root;

                parentNode.splitChild(1, root);
                parentNode.addNonFull(element);
            } else {
                root.addNonFull(element);
            }
            size++;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Return the element at the given index in the BTree
     *
     * @param index of the element to return
     * @return element at the given index
     */
    public T get(int index) {
        int counter = 0;
        for(T element : this) {
            if (counter == index) {
                return element;
            }
            counter++;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {
        return new BTreeIterator<>(this.root);
    }

    /**
     * Uses helper function to traverse the tree in reverse.
     *
     * @param action to be performed on each element
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        root.reversedTraversal(action);
    }

    /**
     * {@inheritDoc}
     */
    public Comparator<? super T> comparator() {
        return comparator;
    }

    /**
     * {@inheritDoc}
     */
    final int compare(Object k1, Object k2) {
        return comparator == null ? ((Comparable<? super T>)k1).compareTo((T)k2)
                : comparator.compare((T)k1, (T)k2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        } else {
            Iterator<T> iterator = this.iterator();
            StringBuilder stringResult = new StringBuilder();
            stringResult.append("[").append(iterator.next());
            while(iterator.hasNext()) {
                stringResult.append(", ").append(iterator.next());
            }
            stringResult.append("]");
            return stringResult.toString();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        Iterator<T> iterator = iterator();
        ArrayList<Object> elements = new ArrayList<>();
        while (iterator.hasNext()) {
            elements.add(iterator.next());
        }
        return elements.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        this.root = new BTreeNode<>(true, 0);
        this.size = 0;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return null;
    }

    @Override
    public T first() {
        return null;
    }

    @Override
    public T last() {
        return null;
    }

    private class BTreeNode<E extends Comparable<E>> implements Node<E> {
        private int numNodes;
        private final E[] keys;
        private final BTreeNode<E>[] children;
        private final boolean isLeaf;

        public BTreeNode(boolean isLeaf, int numNodes) {
            this.keys = (E[]) new Comparable[2 * order - 1];
            this.children = new BTreeNode[2 * order];
            this.isLeaf = isLeaf;
            this.numNodes = numNodes;
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
                if (!this.isLeaf) {
                    this.children[i].reversedTraversal(action);
                }
                action.accept(this.keys[i - 1]);
                i--;
            }
            if (!this.isLeaf) {
                this.children[i].reversedTraversal(action);
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
                if (this.children[i - 1].numNodes == BTree.this.order) {
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
        void splitChild(int childIndex, BTreeNode<E> newChild) {
            BTreeNode<E> childNode = new BTreeNode<>(newChild.isLeaf, 1);
            childNode.keys[0] = newChild.keys[2];

            if (!newChild.isLeaf) {
                childNode.children[1] = newChild.children[3];
                childNode.children[0] = newChild.children[2];
            }
            newChild.numNodes = 1;

            for (int i = this.numNodes + 1; i >= childIndex + 1; i--) {
                this.children[i] = this.children[i - 1];
                this.keys[i - 1] = this.keys[i - 2];
            }
            this.children[childIndex] = childNode;
            this.keys[childIndex - 1] = newChild.keys[1];
            this.numNodes++;
        }
    }

    /**
     * Iterator object for the BTree parent class which implements the Iterator design
     * pattern using in-order traversal. This class allows clients to iterate over
     * all elements in the BTree.
     */
    private final class BTreeIterator<C extends Comparable<C>> implements Iterator<C> {
        private final Stack<BTreeNode<C>> nodeStack;
        private final Stack<Integer> indexStack;

        public BTreeIterator(BTreeNode<C> root) {
            nodeStack  = new Stack<>();
            indexStack = new Stack<>();
            if (root.numNodes > 0) {
                pushLeft(root);
            }
        }

        /**
         * Checks if there are more elements to return
         *
         * @return true if there are more elements in the stack, false otherwise
         */
        public boolean hasNext() {
            return !nodeStack.isEmpty();
        }

        /**
         * Return the next element from the Iterator
         *
         * @return nextElement the student to be inserted
         */
        public C next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BTreeNode<C> nextNode = nodeStack.peek();
            int index = indexStack.pop();
            C nextElement = nextNode.keys[index];
            index++;
            if (index < nextNode.numNodes) {
                indexStack.push(index);
            } else {
                nodeStack.pop();
            }
            if (!nextNode.isLeaf) {
                pushLeft(nextNode.children[index]);
            }
            return nextElement;
        }

        /**
         * Pushes the given element to the left child
         *
         * @param node to be pushed to the nodeStack
         */
        private void pushLeft(BTreeNode<C> node) {
            while (true) {
                nodeStack.push(node);
                indexStack.push(0);
                if (node.isLeaf) {
                    break;
                }
                node = node.children[0];
            }
        }
    }
}
