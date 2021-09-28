package com.lukajozic.strategy;

import com.lukajozic.Strategy;
import com.lukajozic.Student;

import java.util.Comparator;

public class Order {
    public static class AlphaDecreasing implements Strategy {
        @Override
        public Comparator<Student> getOrder() {
            return Comparator.comparing(Student::getName).reversed();
        }
    }

    public static class AlphaIncreasing implements Strategy {
        @Override
        public Comparator<Student> getOrder() {
            return Comparator.comparing(Student::getName);
        }
    }

    public static class GpaDecreasing implements Strategy {
        @Override
        public Comparator<Student> getOrder() {
            return Comparator.comparing(Student::getGpa).reversed();
        }
    }

    public static class GpaIncreasing implements Strategy {
        @Override
        public Comparator<Student> getOrder() {
            return Comparator.comparing(Student::getGpa);
        }
    }
}

