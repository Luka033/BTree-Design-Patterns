package com.lukajozic;

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
    void compareTo() {
        Student otherStudent = new Student("Roger", "002", 3.0);
        assertEquals(testStudent.compareTo(otherStudent), -1);
        assertEquals(otherStudent.compareTo(testStudent), 1);
        otherStudent.setName("Adam");
        assertEquals(testStudent.compareTo(otherStudent), 0);
    }

    @Test
    void testEquals() {
        Student sameStudent = new Student("Adam", "001", 4.0);
        assertTrue(testStudent.equals(sameStudent));
        Student notSameStudent = new Student("Roger", "002", 3.0);
        assertFalse(testStudent.equals(notSameStudent));
    }
}