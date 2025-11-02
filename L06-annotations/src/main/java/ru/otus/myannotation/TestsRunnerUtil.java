package ru.otus.myannotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestsRunnerUtil {

    public static void runTests( Class<?> clazz) {
        List<Method> befores = new ArrayList<>();
        List<Method> tests = new ArrayList<>();
        List<Method> afters = new ArrayList<>();

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
            Object instance = newInstance(clazz);
            runAllMethods(befores, instance);
            executeMethod(test, instance);
            runAllMethods(afters, instance);
        }
    }

    private static void runAllMethods(List<Method> methods, Object object) {
        for (Method method : methods) {
            executeMethod(method, object);
        }
    }

    @SuppressWarnings("java:S112")
    private static void executeMethod(Method method, Object object) {
        try {
            method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object newInstance(Class<?> clazz) {
        try {
            var creator = clazz.getDeclaredConstructor();
            creator.setAccessible(true);
            return creator.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
