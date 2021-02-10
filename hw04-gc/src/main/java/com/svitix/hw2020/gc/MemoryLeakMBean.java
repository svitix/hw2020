package com.svitix.hw2020.gc;

public interface MemoryLeakMBean {

    void setLimit(int sizeLimit);

    void run();

}
