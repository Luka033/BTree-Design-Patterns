package com.lukajozic.test;

import com.lukajozic.main.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student("Adam", "001", 4.0);
    }

    @Test
    void testCompareTo() {
        Student otherStudent = new Student("Roger", "002", 3.0);
        assertTrue(testStudent.compareTo(otherStudent) < 0);
        assertTrue(otherStudent.compareTo(testStudent) > 0);
        otherStudent.setName("Adam");
        assertEquals(testStudent.compareTo(otherStudent), 0);
    }

    @Test
    void testEquals() {
        Student sameStudent = new Student("Adam", "001", 4.0);
        assertEquals(sameStudent, testStudent);
        Student notSameStudent = new Student("Roger", "002", 3.0);
        assertNotEquals(notSameStudent, testStudent);
    }
}