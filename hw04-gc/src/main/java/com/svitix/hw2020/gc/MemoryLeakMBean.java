package com.svitix.hw2020.gc;

public interface MemoryLeakMBean {
    public void setLimit(int sizeLimit);
    public int getLimit();
    void run();
}
