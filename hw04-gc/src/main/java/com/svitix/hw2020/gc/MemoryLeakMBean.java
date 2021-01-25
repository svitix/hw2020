package com.svitix.hw2020.gc;

public interface MemoryLeak {
    public void setLimit(int sizeLimit);
    public int getLimit();
    void run();
}
