package ru.otus.myannotation;

import java.lang.reflect.Method;
import java.util.ArrayList;

@SuppressWarnings("java:S106")
public class Main {

    public static void main(String[] args) {
        TestsRunnerUtil.runTests(MyTests.class);
    }

}
