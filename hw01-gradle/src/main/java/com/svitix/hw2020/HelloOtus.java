package com.svitix.hw2020;

import com.google.common.collect.Lists;

import java.util.List;

public class HelloOtus {
    public String runGuava() {
        List<String> numbers = Lists.newArrayList("one", "two", "three",
                "four", "five");
        StringBuilder stringBuilder = new StringBuilder();
        for (String number : numbers) {
            stringBuilder.append(number).append(" ");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(new HelloOtus().runGuava());

    }
}
