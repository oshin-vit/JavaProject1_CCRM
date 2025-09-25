package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.TranscriptService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class MainApp {

    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);
    private final ImportExportService ioService = new ImportExportService(studentService, courseService, enrollmentService);
    private final TranscriptService transcriptService = new TranscriptService(enrollmentService);

    public static void main(String[] args) throws IOException {
        System.out.println("CCRM - Complete version starting...");
        AppConfig cfg = AppConfig.getInstance(); // Singleton loads defaults
        System.out.println("Loaded AppConfig: dataFolder=" + cfg.getDataFolder());
        MainApp app = new MainApp();
        app.bootstrap();
        app.runCLI();
    }

    private void bootstrap() {
        try {
            Files.createDirectories(Path.of("exports"));
            Files.createDirectories(Path.of("backups"));
        } catch (IOException e) {
            System.err.println("Failed to create folders: " + e.getMessage());
        }
    }

    private void runCLI() {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            printMenu();
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> manageStudents(sc);
                    case "2" -> manageCourses(sc);
                    case "3" -> manageEnrollment(sc);
                    case "4" -> manageGrades(sc);
                    case "5" -> importData();
                    case "6" -> exportData();
                    case "7" -> backupNow();
                    case "8" -> reports();
                    case "0" -> exit = true;
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
        System.out.println("Bye");
    }

    private void printMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Enrollment");
        System.out.println("4. Grades");
        System.out.println("5. Import Data (test-data/)");
        System.out.println("6. Export Data (exports/)");
        System.out.println("7. Backup & show size");
        System.out.println("8. Reports (GPA distribution, top students)");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private void manageStudents(Scanner sc) {
        System.out.println("a) Add student  b) List students  c) Search by regNo");
        String c = sc.nextLine().trim();
        if (c.equals("a")) {
            System.out.print("RegNo: ");
            String reg = sc.nextLine().trim();
            System.out.print("Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Email: ");
            String email = sc.nextLine().trim();
            var s = studentService.addStudent(reg, name, email);
            System.out.println("Added: " + s);
        } else if (c.equals("b")) {
            studentService.listAll().forEach(System.out::println);
        } else if (c.equals("c")) {
            System.out.print("RegNo: ");
            String reg = sc.nextLine().trim();
            studentService.findByReg(reg).ifPresentOrElse(System.out::println, () -> System.out.println("Not found"));
        }
    }

    private void manageCourses(Scanner sc) {
        System.out.println("a) Add course  b) List courses");
        String c = sc.nextLine().trim();
        if (c.equals("a")) {
            System.out.print("Code: ");
            String code = sc.nextLine().trim();
            System.out.print("Title: ");
            String title = sc.nextLine().trim();
            System.out.print("Credits: ");
            int credits = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Instructor: ");
            String instr = sc.nextLine().trim();
            System.out.print("Semester (SPRING/SUMMER/FALL): ");
            String sem = sc.nextLine().trim();
            var course = courseService.addCourse(code, title, credits, instr, sem);
            System.out.println("Added: " + course);
        } else if (c.equals("b")) {
            courseService.listAll().forEach(System.out::println);
        }
    }

    private void manageEnrollment(Scanner sc) {
        System.out.print("Student regNo: ");
        String reg = sc.nextLine().trim();
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        try {
            var e = enrollmentService.enroll(reg, code);
            System.out.println("Enrolled: " + e);
        } catch (Exception ex) {
            System.out.println("Enrollment failed: " + ex.getMessage());
        }
    }

    private void manageGrades(Scanner sc) {
        System.out.print("RegNo: ");
        String reg = sc.nextLine().trim();
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        System.out.print("Grade (A-F): ");
        String g = sc.nextLine().trim();
        boolean ok = enrollmentService.assignGrade(reg, code, g);
        System.out.println(ok ? "Grade assigned" : "Failed to assign grade");
        System.out.println("Transcript:");
        System.out.println(transcriptService.buildTranscriptFor(reg));
    }

    private void importData() {
        try {
            ioService.importAllFromTestData();
            System.out.println("Import OK");
        } catch (IOException e) {
            System.out.println("Import error: " + e.getMessage());
        }
    }

    private void exportData() {
        try {
            ioService.exportAll();
            System.out.println("Export OK");
        } catch (IOException e) {
            System.out.println("Export error: " + e.getMessage());
        }
    }

    private void backupNow() {
        try {
            var path = BackupService.createTimestampedBackup(Path.of("."));
            long size = BackupService.computeDirectorySize(path);
            System.out.println("Backup at: " + path + " size=" + size + " bytes");
        } catch (IOException e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }

    private void reports() {
        System.out.println("GPA distribution:");
        transcriptService.printGpaDistribution();
        System.out.println("Top students:");
        transcriptService.printTopStudents(3);
    }
}
