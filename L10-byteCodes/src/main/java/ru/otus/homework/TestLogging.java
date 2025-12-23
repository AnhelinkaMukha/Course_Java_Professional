package ru.otus.homework;

public class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {}
    ;

    public void calculation(int param1, int param2) {}
    ;
}
