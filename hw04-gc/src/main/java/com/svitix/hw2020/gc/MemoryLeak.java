package com.svitix.hw2020.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MemoryLeakMBeanImpl implements MemoryLeakMBean {

    private final int cycle;
    private final List<Integer> array = new ArrayList<>();
    private int sizeLimit;

    public MemoryLeakMBeanImpl(int cycle) {
        this.cycle = cycle;
    }

    public void run() {
        for (int x = 0; x < cycle; x++) {
            int sizeOfNewPart = ThreadLocalRandom.current().nextInt(sizeLimit);
            for (int y = 0; y < sizeOfNewPart; y++) {
                array.add(ThreadLocalRandom.current().nextInt());
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //delete section


        }
    }

    @Override
    public void setLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    @Override
    public int getLimit() {
        return sizeLimit;
    }
}
