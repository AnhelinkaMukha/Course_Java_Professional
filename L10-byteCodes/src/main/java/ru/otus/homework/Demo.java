package ru.otus.homework;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createTestLogging(new TestLogging());
        testLogging.calculation(6);
        testLogging.calculation(6, 2);
    }
}
