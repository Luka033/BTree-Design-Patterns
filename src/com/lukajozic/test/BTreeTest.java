package com.lukajozic.test;

import com.lukajozic.main.BTree;
import com.lukajozic.main.Student;
import com.lukajozic.main.strategy.Order;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest<T extends Comparable<T>> {

    /**
     * Populate the given tree with 10 Students
     *
     * @return the given tree after it's been populated
     */
    BTree<Student> populateTestTree(BTree<Student> testTree) {
        testTree.add(new Student("Adam", "001", 4.0));
        testTree.add(new Student("Bertil", "002", 3.6));
        testTree.add(new Student("Charlie", "003", 3.5));
        testTree.add(new Student("David", "004", 3.4));
        testTree.add(new Student("Erik", "005", 3.1));
        testTree.add(new Student("Jonas", "006", 3.0));
        testTree.add(new Student("Luka", "007", 2.85));
        testTree.add(new Student("Martin", "008", 2.5));
        testTree.add(new Student("Niklas", "009", 2.4));
        testTree.add(new Student("Roger", "010", 1.0));
        return testTree;
    }

    @Test
    void add() {
        BTree<Student> testTree = new BTree<>(3, new Order.AlphaIncreasing().getOrder());
        assertEquals(testTree.size(), 0);
        Student addedStudent = new Student("Thomas", "011", 3.5);
        testTree.add(addedStudent);
        assertFalse(testTree.isEmpty());
        assertEquals(testTree.size(), 1);
        Student expectedStudent = testTree.get(0);
        assertEquals(addedStudent, expectedStudent);

    }

    @Test
    void get() {
        BTree<Student> testTree = new BTree<>(3, new Order.AlphaIncreasing().getOrder());
        testTree = populateTestTree(testTree);
        Student ithElement = testTree.get(1);
        assertEquals(ithElement, new Student("Bertil", "002", 3.6));
        ithElement = testTree.get(8);
        assertEquals(ithElement, new Student("Niklas", "009", 2.4));
    }

    @Test
    void iterator() {
        BTree<Student> testTree = new BTree<>(3, new Order.AlphaIncreasing().getOrder());
        Iterator<Student> testIterator = testTree.iterator();
        assertFalse(testIterator.hasNext());
        assertThrows(NoSuchElementException.class, testIterator::next);
        testTree.add(new Student("Thomas", "011", 3.5));
        testIterator = testTree.iterator();
        assertTrue(testIterator.hasNext());
        Student testStudent = testIterator.next();
        assertEquals(testStudent, new Student("Thomas", "011", 3.5));
        assertFalse(testIterator.hasNext());
        assertThrows(NoSuchElementException.class, testIterator::next);
    }

    @Test
    void forEach() {
    }

    @Test
    void size() {
        BTree<Student> testTree = new BTree<>(3, new Order.AlphaIncreasing().getOrder());
        assertEquals(testTree.size(), 0);
        testTree.add(new Student("Jack", "012", 3.5));
        assertEquals(testTree.size(), 1);
        testTree = populateTestTree(testTree);
        assertEquals(testTree.size(), 11);
    }

    @Test
    void isEmpty() {
        BTree<Student> testTree = new BTree<>(3, new Order.AlphaIncreasing().getOrder());
        assertTrue(testTree.isEmpty());
        testTree.add(new Student("Thomas", "011", 3.5));
        assertFalse(testTree.isEmpty());
    }

    @Test
    void testToString() {
        BTree<Student> testTree = new BTree<>(3, new Order.AlphaIncreasing().getOrder());
        assertEquals(testTree.toString(), "[]");
        testTree = populateTestTree(testTree);
        assertEquals(testTree.toString(), "[Student{name='Adam', redId='001', gpa=4.0}, Student{name='Bertil', redId='002', gpa=3.6}, Student{name='Charlie', redId='003', gpa=3.5}, Student{name='David', redId='004', gpa=3.4}, Student{name='Erik', redId='005', gpa=3.1}, Student{name='Jonas', redId='006', gpa=3.0}, Student{name='Luka', redId='007', gpa=2.85}, Student{name='Martin', redId='008', gpa=2.5}, Student{name='Niklas', redId='009', gpa=2.4}, Student{name='Roger', redId='010', gpa=1.0}]");
        testTree = new BTree<>(3, new Order.GpaDecreasing().getOrder());
        testTree = populateTestTree(testTree);
        assertEquals(testTree.toString(), "[Student{name='Adam', redId='001', gpa=4.0}, Student{name='Bertil', redId='002', gpa=3.6}, Student{name='Charlie', redId='003', gpa=3.5}, Student{name='David', redId='004', gpa=3.4}, Student{name='Erik', redId='005', gpa=3.1}, Student{name='Jonas', redId='006', gpa=3.0}, Student{name='Luka', redId='007', gpa=2.85}, Student{name='Martin', redId='008', gpa=2.5}, Student{name='Niklas', redId='009', gpa=2.4}, Student{name='Roger', redId='010', gpa=1.0}]");
    }

}