package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TranscriptService {
    private final EnrollmentService enrollmentService;

    public TranscriptService(EnrollmentService es){ this.enrollmentService = es; }

    public String buildTranscriptFor(String regNo){
        List<Enrollment> list = enrollmentService.listAll().stream().filter(e->e.getStudent().getRegNo().equals(regNo)).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        list.forEach(e-> sb.append(e.toString()).append("\n"));
        return sb.toString();
    }

    // GPA distribution using simple grade points average over enrollments per student
    public void printGpaDistribution(){
        var enrollments = enrollmentService.listAll();
        Map<String, Double> gpa = enrollments.stream().filter(e->e.getGrade()!=null)
                .collect(Collectors.groupingBy(e->e.getStudent().getRegNo(),
                        Collectors.averagingDouble(e->e.getGrade().points())));
        var dist = gpa.values().stream().collect(Collectors.groupingBy(d-> (int)Math.round(d), Collectors.counting()));
        dist.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e-> System.out.println("GPA~"+e.getKey()+": "+e.getValue()));
    }

    public void printTopStudents(int n){
        var enrollments = enrollmentService.listAll().stream().filter(e->e.getGrade()!=null).collect(Collectors.toList());
        Map<String, Double> gpa = enrollments.stream()
                .collect(Collectors.groupingBy(e->e.getStudent().getRegNo(), Collectors.averagingDouble(e->e.getGrade().points())));
        gpa.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())).limit(n).forEach(e-> System.out.println(e.getKey()+" -> "+e.getValue()));
    }
}
