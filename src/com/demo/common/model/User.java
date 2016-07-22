package com.demo.common.model;

/**
 * User class.
 */
public class User {

    private long id;
    private String name;

    /**
     * Constructor.
     *
     * @param id   the id
     * @param name the name
     */
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
