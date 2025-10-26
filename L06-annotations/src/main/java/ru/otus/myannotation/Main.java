package ru.otus.myannotation;

import java.lang.reflect.Method;
import java.util.ArrayList;

@SuppressWarnings("java:S106")
public class Main {
    static Class<MyTests> clazz = MyTests.class;

    public static void main(String[] args) {
        ArrayList<Method> befores = new ArrayList<>();
        ArrayList<Method> tests = new ArrayList<>();
        ArrayList<Method> afters = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                befores.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                tests.add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                afters.add(method);
            }
        }

        for (Method test : tests) {
            runAllMethods(befores);
            executeMethod(test);
            runAllMethods(afters);
        }
    }

    static void runAllMethods(ArrayList<Method> methods) {
        for (Method method : methods) {
            executeMethod(method);
        }
    }

    @SuppressWarnings("java:S112")
    private static void executeMethod(Method method) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            method.invoke(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
