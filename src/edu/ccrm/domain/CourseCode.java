package edu.ccrm.domain;

public final class CourseCode {
    private final String code;
    public CourseCode(String code) {
        if(code==null) throw new IllegalArgumentException("code null");
        this.code = code;
    }
    public String getCode() { return code; }
    public CourseCode copy() { return new CourseCode(new String(code)); }
    @Override public String toString(){ return code; }
}
