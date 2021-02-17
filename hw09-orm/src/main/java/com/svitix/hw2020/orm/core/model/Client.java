package com.svitix.hw2020.orm.core.model;

import com.svitix.hw2020.orm.jdbc.annotation.Id;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class Client {

    @Id
    private long id;
    private String name;
    private Integer age;

    public Client() {

    }

    public Client(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
