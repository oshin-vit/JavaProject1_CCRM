package edu.ccrm.util;

import edu.ccrm.domain.Student;

import java.util.Comparator;

public class Comparators {
    public static Comparator<Student> byName = Comparator.comparing(Student::getFullName);
    public static Comparator<Student> byReg = Comparator.comparing(Student::getRegNo);
}
