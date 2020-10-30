package com.svitix.hw2020.model;

public class Vehicle {

    private String name;

    public Vehicle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + ":" + name;
    }
}
