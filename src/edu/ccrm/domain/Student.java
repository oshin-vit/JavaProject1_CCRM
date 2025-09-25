package edu.ccrm.domain;

public class Student extends Person implements Persistable {
    private final String regNo;

    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = regNo;
    }

    public String getRegNo() { return regNo; }

    @Override
    public String toString() {
        return regNo + " | " + getFullName() + " (" + getEmail() + ")";
    }

    @Override
    public String serialize() {
        return id + "," + regNo + "," + fullName + "," + email;
    }
}
