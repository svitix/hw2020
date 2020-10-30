package com.svitix.hw2020;

import com.svitix.hw2020.model.Car;
import com.svitix.hw2020.model.Vehicle;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class DIYArrayListExample {
    public static void main(String... args) {

        DIYArrayList<Vehicle> myList = new DIYArrayList<>();
        for (int i = 0; i < 20; i++) {
            myList.add(new Vehicle(String.valueOf(ThreadLocalRandom.current().nextInt(100, 199))));
        }

        /*
            Test 1
            Collections.addAll(Collection<? super T> c, T... elements)
         */
        Car[] elements = {
                new Car("145"),
                new Car("123"),
                new Car("153")
        };
        Collections.addAll(myList, elements);
        System.out.println("Added: " + myList.size());
        for (int i = 0; i < myList.size(); i++) {
            System.out.println(myList.get(i));
        }
        System.out.println();


        /*
            Test2
            Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
         */
        DIYArrayList<Vehicle> myListForCopy = new DIYArrayList<>();
        for (int j = 0; j < myList.size(); j++) {
            myListForCopy.add(new Vehicle(String.valueOf(j)));
        }
        Collections.copy(myListForCopy, myList);
        System.out.println("Copy: " + myListForCopy.size());
        for (int i = 0; i < myListForCopy.size(); i++) {
            System.out.println(myListForCopy.get(i));
        }
        System.out.println();

        /*
            Test3
            Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
         */
        Comparator <Vehicle> comparator = Comparator.comparing(Vehicle::getName);
        Collections.sort(myListForCopy, comparator);
        System.out.println("Sorting: " + myListForCopy.size());
        for (int i = 0; i < myListForCopy.size(); i++) {
            System.out.println(myListForCopy.get(i));
        }
    }
}
