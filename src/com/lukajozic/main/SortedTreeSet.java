package com.lukajozic.main;

import java.util.SortedSet;

public interface SortedTreeSet<T> extends SortedSet<T> {
    T get(int index);
}
