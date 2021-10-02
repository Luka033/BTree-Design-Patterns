package com.lukajozic.main;

import com.lukajozic.main.strategy.Order;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class Main {
    public static void printStudentsOnProbation(SortedTreeSet<Student> bTree) {
        for (Student candidateStudent : bTree) {
            if (candidateStudent.getGpa() < 2.85) {
                System.out.println(candidateStudent.getRedId());
            }
        }
    }

    public static void printStudentsOnDeansList(SortedTreeSet<Student> bTree) {
        bTree.forEach(candidateStudent -> {
            if (candidateStudent.getGpa() == 4.0) {
                System.out.println(candidateStudent.getName());
            }
        });
    }

    public static void main(String[] args) {
        Comparator<Student> strategy = new Order.AlphaIncreasing().getOrder();
        SortedTreeSet<Student> b = new BTree<>(3, strategy);
        b.add(new Student("Adam", "001", 4.0));
        b.add(new Student("Bertil", "002", 4.0));
        b.add(new Student("Charlie", "003", 3.5));
        b.add(new Student("David", "004", 3.4));
        b.add(new Student("Erik", "005", 3.1));
        b.add(new Student("Jonas", "006", 3.0));
        b.add(new Student("Luka", "007", 2.85));
        b.add(new Student("Martin", "008", 2.5));
        b.add(new Student("Niklas", "009", 2.4));
        b.add(new Student("Roger", "010", 1.0));

        System.out.println(Arrays.toString(b.toArray()));

        System.out.println(b.get(0));
        printStudentsOnDeansList(b);
        printStudentsOnProbation(b);
        System.out.println(b.toString());

        System.out.println("\n============= EXTERNAL ITERATOR ==============");
        Iterator<Student> iterator = b.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("\n============= EXTERNAL ITERATOR ==============");
        for (Student student : b) {
            System.out.println(student);
        }

        System.out.println("\n============= INTERNAL ITERATOR ==============");
        b.forEach(System.out::println);




    }
}
