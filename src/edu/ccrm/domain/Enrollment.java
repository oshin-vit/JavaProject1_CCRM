package edu.ccrm.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Enrollment {
    private final String id;
    private final Student student;
    private final Course course;
    private final LocalDateTime enrolledAt;
    private Grade grade;

    public Enrollment(Student student, Course course) {
        this.id = UUID.randomUUID().toString();
        this.student = student;
        this.course = course;
        this.enrolledAt = LocalDateTime.now();
    }

    public String getId(){ return id; }
    public Student getStudent(){ return student; }
    public Course getCourse(){ return course; }
    public LocalDateTime getEnrolledAt(){ return enrolledAt; }
    public Grade getGrade(){ return grade; }
    public void setGrade(Grade g){ this.grade = g; }

    @Override public String toString(){
        return student.getRegNo()+" -> "+course.getCode()+" ["+(grade!=null?grade:"-")+"] @"+enrolledAt.toString();
    }
}
