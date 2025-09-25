package edu.ccrm.service;

import edu.ccrm.domain.*;

import java.util.*;
import java.util.stream.Collectors;

public class EnrollmentService {
    private final Map<String, Enrollment> enrollments = new LinkedHashMap<>();
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService ss, CourseService cs){
        this.studentService = ss; this.courseService = cs;
    }

    public Enrollment enroll(String regNo, String courseCode) throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        var sOpt = studentService.findByReg(regNo);
        var cOpt = courseService.findByCode(courseCode);
        if(sOpt.isEmpty() || cOpt.isEmpty()) throw new IllegalArgumentException("Invalid ids");
        Student s = sOpt.get();
        Course c = cOpt.get();

        // check duplicate
        boolean dup = enrollments.values().stream().anyMatch(e -> e.getStudent().getRegNo().equals(regNo) && e.getCourse().getCode().getCode().equals(courseCode));
        if(dup) throw new DuplicateEnrollmentException("Already enrolled");

        // simplistic credit limit check (unchecked exception)
        int total = enrollments.values().stream().filter(e->e.getStudent().getRegNo().equals(regNo)).mapToInt(e->e.getCourse().getCredits()).sum();
        if(total + c.getCredits() > 30) throw new MaxCreditLimitExceededException("Exceeds credit limit");

        Enrollment e = new Enrollment(s,c);
        enrollments.put(e.getId(), e);
        return e;
    }

    public boolean assignGrade(String regNo, String courseCode, String gradeName){
        var found = enrollments.values().stream().filter(e->e.getStudent().getRegNo().equals(regNo) && e.getCourse().getCode().getCode().equals(courseCode)).findFirst();
        if(found.isEmpty()) return false;
        try {
            Grade g = Grade.valueOf(gradeName.toUpperCase());
            found.get().setGrade(g);
            return true;
        } catch(Exception ex){ return false; }
    }

    public List<Enrollment> listAll(){ return new ArrayList<>(enrollments.values()); }

    // demonstration of upcast/downcast and instanceof
    public void demoCasting(){
        if(studentService.listAll().isEmpty()) return;
        Object o = studentService.listAll().get(0); // upcast to Object
        if(o instanceof Student){
            Student s = (Student)o; // downcast
            System.out.println("Casted student: "+s.getRegNo());
        }
    }
}
