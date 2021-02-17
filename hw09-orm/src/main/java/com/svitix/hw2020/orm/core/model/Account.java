package com.svitix.hw2020.orm.core.model;

import com.svitix.hw2020.orm.jdbc.annotation.Id;

public class Account {

    @Id
    private String no;
    private String type;
    private double rest;

    public Account() {

    }

    public Account(String no, String type) {
        this.no = no;
        this.type = type;
    }


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRest() {
        return rest;
    }

    public void setRest(double rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                '}';
    }
}
