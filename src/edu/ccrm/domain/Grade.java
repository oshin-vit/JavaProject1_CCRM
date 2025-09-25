package edu.ccrm.domain;

public enum Grade {
    A(10), B(8), C(6), D(4), E(2), F(0);

    private final int points;
    Grade(int p) { this.points = p; }
    public int points() { return points; }
}
