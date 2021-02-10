package com.svitix.hw2020;

public class Statistics {
    private static final Statistics INSTANCE = new Statistics();
    private int countRunGc;
    private long totalTime;

    private Statistics() {

    }

    public static Statistics getInstance() {
        return INSTANCE;
    }

    public void addTime(long period) {
        totalTime += period;
    }

    public void incrementCountRunGc() {
        countRunGc++;
    }

    public String getStatistics() {
        return "CountRunGc: " + countRunGc + ". "
                + "TotalTime: " + totalTime + ".";
    }

}
