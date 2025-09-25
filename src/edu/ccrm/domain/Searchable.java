package edu.ccrm.domain;

public interface Searchable<T> {
    boolean matches(String q);
    default String info(){ return "Searchable info"; }
}
