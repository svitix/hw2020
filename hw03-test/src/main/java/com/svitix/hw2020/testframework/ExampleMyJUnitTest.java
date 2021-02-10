package com.svitix.hw2020.testframework;

import com.svitix.hw2020.testframework.myjunit.annotation.After;
import com.svitix.hw2020.testframework.myjunit.annotation.Before;
import com.svitix.hw2020.testframework.myjunit.annotation.Test;

public class ExampleMyJUnitTest {

    @Before
    public void beforeFirstMethod() {

    }

    @Before
    public void beforeSecondMethod() {

    }

    @Test
    public void exampleTest() {
        throw new ArithmeticException();
    }

    @Test
    public void example2Test() {
    }

    @Test
    public void example3Test() {
    }

    @After
    public void afterFirstMethod() {

    }

    @After
    public void afterSecondMethod() {

    }

}
