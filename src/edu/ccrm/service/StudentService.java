package edu.ccrm.service;

import edu.ccrm.domain.Student;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentService {
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(1);

    public Student addStudent(String regNo, String fullName, String email){
        String id = "S"+idGen.getAndIncrement();
        Student s = new Student(id, regNo, fullName, email);
        students.put(regNo, s);
        return s;
    }

    public Optional<Student> findByReg(String reg){ return Optional.ofNullable(students.get(reg)); }

    public List<Student> listAll(){ return new ArrayList<>(students.values()); }

    public void addExisting(Student s){ students.put(s.getRegNo(), s); }
}
