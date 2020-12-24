package com.svitix.hw2020.testframework.myjunit;

import com.svitix.hw2020.testframework.StatListener;

public class Statistics implements StatListener {
    private int countTest = 0;
    private int failedTest = 0;

    @Override
    public void onStartTest() {
        this.countTest++;
    }

    @Override
    public void onFailedTest() {
        this.failedTest++;
    }

    @Override
    public String toString() {
        return "Result of execution tests: " + (countTest - failedTest) + "/" + countTest + ".";
    }
}
