package edu.ccrm.domain;

public class Course implements Searchable<Course>, Persistable, Readable {
    private final CourseCode code;
    private final String title;
    private final int credits;
    private final String instructor;
    private final Semester semester;

    private Course(Builder b){
        assert b.code!=null;
        this.code = b.code;
        this.title = b.title;
        this.credits = b.credits;
        this.instructor = b.instructor;
        this.semester = b.semester;
    }

    public CourseCode getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }

    @Override public String toString(){
        return code + " | " + title + " ("+credits+"cr) - "+instructor+" - "+semester;
    }

    @Override
    public boolean matches(String q) {
        return code.getCode().equalsIgnoreCase(q) || title.toLowerCase().contains(q.toLowerCase());
    }

    @Override
    public String serialize() {
        return code.getCode()+","+title+","+credits+","+instructor+","+semester;
    }

    // static nested helper class
    public static class Utils {
        public static CourseCode parseCode(String s){ return new CourseCode(s); }
    }

    // inner class example (non-static)
    public class Meta {
        private final String createdBy;
        public Meta(String createdBy){ this.createdBy = createdBy; }
        public String info(){ return code + " created by " + createdBy; }
    }

    // Builder
    public static class Builder {
        private CourseCode code;
        private String title;
        private int credits;
        private String instructor;
        private Semester semester = Semester.FALL;

        public Builder code(String c){ this.code = new CourseCode(c); return this; }
        public Builder title(String t){ this.title = t; return this; }
        public Builder credits(int c){ this.credits = c; return this; }
        public Builder instructor(String i){ this.instructor = i; return this; }
        public Builder semester(Semester s){ this.semester = s; return this; }
        public Course build(){ return new Course(this); }
    }
    @Override
    public String info() {
        // resolve diamond problem explicitly
        return "Course info: " + code.getCode() + " - " + title;
    }
}
