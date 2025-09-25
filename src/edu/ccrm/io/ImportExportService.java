package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class ImportExportService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public ImportExportService(StudentService ss, CourseService cs, EnrollmentService es){
        this.studentService = ss; this.courseService = cs; this.enrollmentService = es;
    }

    public void importAllFromTestData() throws IOException {
        Path sd = Paths.get("test-data","students.csv");
        Path cd = Paths.get("test-data","courses.csv");
        if(Files.exists(sd)){
            List<String> lines = Files.readAllLines(sd, StandardCharsets.UTF_8);
            lines.stream().skip(1).filter(l->!l.isBlank()).forEach(l->{
                String[] p = l.split(",");
                String reg = p[1].trim(); String name = p[2].trim(); String email = p[3].trim();
                Student s = new Student("S-"+reg, reg, name, email);
                studentService.addExisting(s);
            });
        }
        if(Files.exists(cd)){
            List<String> lines = Files.readAllLines(cd, StandardCharsets.UTF_8);
            lines.stream().skip(1).filter(l->!l.isBlank()).forEach(l->{
                String[] p = l.split(",");
                String code = p[0].trim(); String title = p[1].trim(); int credits = Integer.parseInt(p[2].trim());
                String instr = p[3].trim(); String sem = p[4].trim();
                courseService.addCourse(code, title, credits, instr, sem);
            });
        }
    }

    public void exportAll() throws IOException {
        Path exports = Paths.get("exports");
        Files.createDirectories(exports);
        List<String> studs = studentService.listAll().stream().map(s-> s.getRegNo()+","+s.getFullName()+","+s.getEmail()).collect(Collectors.toList());
        Files.write(exports.resolve("students_export.csv"), studs, StandardCharsets.UTF_8);
        List<String> crs = courseService.listAll().stream().map(c-> c.getCode().getCode()+","+c.getTitle()+","+c.getCredits()+","+c.getInstructor()).collect(Collectors.toList());
        Files.write(exports.resolve("courses_export.csv"), crs, StandardCharsets.UTF_8);
    }
}
