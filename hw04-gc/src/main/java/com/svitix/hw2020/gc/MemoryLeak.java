package com.svitix.hw2020.gc;

import com.svitix.hw2020.Statistics;

import java.util.ArrayList;
import java.util.List;

public class MemoryLeak implements MemoryLeakMBean {

    private static final int PAUSE_IN_MS = 10;
    private final int cycle;
    private final List<Integer> array = new ArrayList<>();
    private int sizeLimit;

    public MemoryLeak(int cycle) {
        this.cycle = cycle;
    }

    public void run() {
        for (int x = 0; x < cycle; x++) {
            int sizeOfNewPart = sizeLimit;
            try {
                for (int y = 0; y < sizeOfNewPart; y++) {
                    array.add(y);
                }
            } catch (OutOfMemoryError e) {
                Statistics stat = Statistics.getInstance();
                throw new Error("Final cycle: " + x + ". " + stat.getStatistics());
            }
            for (int y = 0; y < array.size() / 4 * 3; y++) {
                array.set(y, null);
            }
            handPause();
        }
    }

    void handPause() {
        try {
            Thread.sleep(PAUSE_IN_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void setLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }
}
