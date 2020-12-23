package com.svitix.hw2020.testframework.myjunit;

import com.svitix.hw2020.testframework.myjunit.annotation.After;
import com.svitix.hw2020.testframework.myjunit.annotation.Before;
import com.svitix.hw2020.testframework.myjunit.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyJUnit {

    private static Class<?> testClass;

    private static List<Method> beforeMethods;
    private static List<Method> afterMethods;

    private static int countFail;
    private static int countPass;

    public static void test(Class clazz) {
        testClass = clazz;

        init();

        getBeforeAndAfterMethods();

        runTests();
    }

    private static void init() {
        beforeMethods = new ArrayList<>();
        afterMethods = new ArrayList<>();

        countPass = 0;
        countFail = 0;
    }

    private static void runTests() {

        for (Method method : testClass.getDeclaredMethods()) {

            Object testObject = null;
            try {
                testObject = testClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            if (method.isAnnotationPresent(Test.class)) {
                try{
                    for (Method beforeMethod : beforeMethods) {
                        beforeMethod.invoke(testObject);
                    }
                    System.out.println("Running: " + method.getName());
                    method.invoke(testObject);
                    for (Method m : afterMethods) {
                        m.invoke(testObject);
                    }
                    countPass++;
                }
                catch (InvocationTargetException | IllegalAccessException e) {
                    countFail++;
                    e.printStackTrace();
                }
            }
        }
        showStatistics();

    }

    private static void showStatistics() {
        System.out.println("completed tests: " + countPass + " / " + (countFail + countPass));
    }


    private static void getBeforeAndAfterMethods() {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        }
    }
}
