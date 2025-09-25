package edu.ccrm.domain;

public abstract class Person {
    protected final String id;
    protected String fullName;
    protected String email;

    public Person(String id, String fullName, String email) {
        assert id != null;
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return id + " - " + fullName + " <" + email + ">";
    }
}
