package com.lukajozic.main;

import java.util.Objects;

/**
 * Student class
 * CS-635
 *
 * @author Luka Jozic
 */
public class Student implements Comparable<Student> {

    private String name;
    private String redId;
    private double gpa;

    public Student(String name, String redId, double gpa) {
        this.name = name;
        this.redId = redId;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRedId() {
        return redId;
    }

    public void setRedId(String redId) {
        this.redId = redId;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", redId='" + redId + '\'' +
                ", gpa=" + gpa +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Student))
            return false;
        Student other = (Student)obj;
        return other.getName().equals(this.getName()) &&
                other.getGpa() == this.getGpa() &&
                other.getRedId().equals(this.getRedId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, redId, gpa);
    }

//    public int getComparator(Strategy strategy, Student o1, Student o2) {
//        return strategy.compareTo()
//    }

//    protected static class OrderByAlpha implements Strategy {
//        @Override
//        public int compareTo(Student currentStudent, Student otherStudent) {
//            if(currentStudent.getName().compareTo(otherStudent.getName()) > 0) {
//                return 1;
//            } else if(currentStudent.getName().compareTo(otherStudent.getName()) < 0) {
//                return -1;
//            } else {
//                return 0;
//            }
//        }
//    }
//
//    protected static class OrderByGpa implements Strategy {
//        @Override
//        public int compareTo(Student currentStudent, Student otherStudent) {
//            if(currentStudent.getGpa() > otherStudent.getGpa()) {
//                return 1;
//            } else if(currentStudent.getGpa() < otherStudent.getGpa()) {
//                return -1;
//            } else {
//                return 0;
//            }
//        }
//    }
}


