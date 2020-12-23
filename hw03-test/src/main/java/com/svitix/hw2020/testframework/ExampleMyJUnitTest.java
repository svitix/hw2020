package com.svitix.hw2020.testframework;

import com.svitix.hw2020.testframework.myjunit.annotation.After;
import com.svitix.hw2020.testframework.myjunit.annotation.Before;
import com.svitix.hw2020.testframework.myjunit.annotation.Test;

public class ExampleMyJUnitTest {

    @Before
    public void beforeExample(){
        System.out.println("before1");
    }

    @Before
    public void before2Example(){
        System.out.println("before2");
    }

    @Test
    public void exampleTest() {
        throw new ArithmeticException();
    }

    @Test
    public void example2Test() {
    }

    @After
    public void afterTest() {
        System.out.println("after1");
    }

}
