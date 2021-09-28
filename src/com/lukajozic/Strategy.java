package com.lukajozic;

import java.util.Comparator;

/**
 * Interface to implement a Strategy for selecting a ordering strategy for the Student class
 */
public interface Strategy {
    Comparator<Student> getOrder();
}
