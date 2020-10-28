package com.svitix.hw2020;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HelloOtusTest {
    @Test void runGuavaAnswer() {
        HelloOtus classUnderTest = new HelloOtus();
        assertEquals(classUnderTest.runGuava(), "one two three four five ");
    }
}
