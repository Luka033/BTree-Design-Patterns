package com.lukajozic.main;

import java.util.*;
import java.util.function.Consumer;

/**
 * Generic B-Tree Class using design patterns Iterator, Strategy, and Null Object.
 * CS-635
 *
 * @author Luka Jozic
 */
public class BTree<T extends Comparable<T>> implements SortedTreeSet<T> {
    private static final int DEFAULT_ORDER = 3;
    public Node<T> root;
    private final int order;
    private int size;
    private final Comparator<? super T> comparator;


    BTree() {
        this(DEFAULT_ORDER, null);
    }

    BTree(int order) {
        this(order, null);
    }

    public BTree(int order, Comparator<? super T> comparator) {
        this.order = order;
        this.root = new InnerNode<>(this.order, true, 0, this::compare);
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
            Node<T> root = this.root;
            if (root.getNumNodes() == this.order) {
                Node<T> parentNode = new InnerNode<>(this.order, false, 0, this::compare);
                this.root = parentNode;
                parentNode.setChild(0, root);
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
        for (T element : this) {
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
    public boolean contains(Object object) {
        T element = (T) object;
        for (T elem : this) {
            if (element.equals(elem)) {
                return true;
            }
        }
        return false;
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
        this.root.reversedTraversal(action);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if(size == 0) {
            return "[]";
        }
        return "[ " + this.root.toString() + "]";
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
    public Comparator<? super T> comparator() {
        return comparator;
    }

    /**
     * {@inheritDoc}
     */
    final int compare(Object k1, Object k2) {
        return comparator == null ? ((Comparable<? super T>) k1).compareTo((T) k2)
                : comparator.compare((T) k1, (T) k2);
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
    public void clear() {
        this.root = new InnerNode<>(this.order, true, 0, this::compare);
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

    /**
     * Iterator object for the BTree parent class which implements the Iterator design
     * pattern using in-order traversal. This class allows clients to iterate over
     * all elements in the BTree.
     */
    private static final class BTreeIterator<C extends Comparable<C>> implements Iterator<C> {
        private final Stack<Node<C>> nodeStack;
        private final Stack<Integer> indexStack;

        public BTreeIterator(Node<C> root) {
            nodeStack = new Stack<>();
            indexStack = new Stack<>();
            if (root.getNumNodes() > 0) {
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
            Node<C> nextNode = nodeStack.peek();
            int index = indexStack.pop();
            C nextElement = nextNode.getKey(index);
            index++;
            if (index < nextNode.getNumNodes()) {
                indexStack.push(index);
            } else {
                nodeStack.pop();
            }
            if (!nextNode.isLeaf()) {
                pushLeft(nextNode.getChild(index));
            }
            return nextElement;
        }

        /**
         * Push the given element to the left child
         *
         * @param node to be pushed to the nodeStack
         */
        private void pushLeft(Node<C> node) {
            while (true) {
                nodeStack.push(node);
                indexStack.push(0);
                if (node.isLeaf()) {
                    break;
                }
                node = node.getChild(0);
            }
        }
    }
}

