package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CourseService {
    private final Map<String, Course> courses = new LinkedHashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(1);

    public Course addCourse(String code, String title, int credits, String instructor, String sem){
        Semester s = Semester.FALL;
        try{ s = Semester.valueOf(sem.toUpperCase()); }catch(Exception ignored){}
        Course c = new Course.Builder().code(code).title(title).credits(credits).instructor(instructor).semester(s).build();
        courses.put(code, c);
        return c;
    }

    public Optional<Course> findByCode(String code){ return Optional.ofNullable(courses.get(code)); }

    public List<Course> listAll(){ return new ArrayList<>(courses.values()); }

    public List<Course> searchByTitle(String q){
        return courses.values().stream().filter(c -> c.matches(q)).collect(Collectors.toList());
    }

    public void addExisting(Course c){ courses.put(c.getCode().getCode(), c); }
}
