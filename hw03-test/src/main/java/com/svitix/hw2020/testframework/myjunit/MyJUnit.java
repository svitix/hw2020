package com.svitix.hw2020.testframework.myjunit;

import com.svitix.hw2020.testframework.myjunit.annotation.After;
import com.svitix.hw2020.testframework.myjunit.annotation.Before;
import com.svitix.hw2020.testframework.myjunit.annotation.Test;
import com.svitix.hw2020.testframework.myjunit.exception.MyJUnitException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyJUnit {

    private final List<StatListener> statListeners = new ArrayList<>();

    public static void test(Class<?> clazz) {

        Statistics stat = new Statistics();
        MyJUnit myJUnit = new MyJUnit();

        myJUnit.addStatListener(stat);

        myJUnit.run(clazz);

        System.out.println(stat);

    }

    private void run(Class<?> clazz) {
        List<Method> beforeMethods = getServiceMethodsFromTestClass(clazz, Before.class);
        List<Method> afterMethods = getServiceMethodsFromTestClass(clazz, After.class);

        try {
            runTestMethods(clazz, beforeMethods, afterMethods);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void runTestMethods(Class<?> testClass, List<Method> beforeMethods, List<Method> afterMethods) throws NoSuchMethodException {
        for (Method method : testClass.getDeclaredMethods()) {
            try {
                if (method.isAnnotationPresent(Test.class)) {
                    try {
                        Object testObject = testClass.getDeclaredConstructor().newInstance();
                        runMethodsOnObject(testObject, beforeMethods, method, afterMethods);
                    } catch (MyJUnitException exception) {
                        System.out.println(exception.getMessage());
                    }
                    System.out.println("----");
                }
            } catch (ReflectiveOperationException e) {
                System.out.println("Не удалось создать объект для тестирования.");
                e.printStackTrace();
            }

        }
    }

    private void runMethodsOnObject(Object testObject, List<Method> beforeMethods, Method method, List<Method> afterMethods) {

        submitTestStatus(false);

        try {
            runMethods(testObject, beforeMethods);
            try {
                runMethod(testObject, method);
            } catch (ReflectiveOperationException ex) {
                submitTestStatus(true);
                throw new MyJUnitException("Тест не пройден. Problem with test method.");
            }
        } catch (ReflectiveOperationException e) {
            submitTestStatus(true);
            throw new MyJUnitException("Тест не пройден. Problem with before");
        } finally {
            try {
                runMethods(testObject, afterMethods);
            } catch (ReflectiveOperationException e) {
                submitTestStatus(true);
                throw new MyJUnitException("Тест не пройден. Problem with after");
            }
        }
    }

    private void runMethods(Object testObject, List<Method> methods) throws ReflectiveOperationException {
        for (Method method : methods) {
            runMethod(testObject, method);
        }
    }

    private void runMethod(Object testObject, Method method) throws ReflectiveOperationException {
        System.out.println(method.getName());
        method.invoke(testObject);
    }

    private List<Method> getServiceMethodsFromTestClass(Class<?> clazz, Class<? extends Annotation> serviceClass) {
        List<Method> result = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(serviceClass)) {
                result.add(method);
            }
        }
        return result;
    }

    void addStatListener(StatListener listener) {
        statListeners.add(listener);
    }

    void submitTestStatus(boolean isFailed) {
        statListeners.forEach(statListener -> {
            try {
                if (isFailed) {
                    statListener.onFailedTest();
                } else {
                    statListener.onStartTest();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
