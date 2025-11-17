package ru.otus.homework;

import ru.otus.aop.proxy.MyClassImpl;
import ru.otus.aop.proxy.MyClassInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {};

    public void calculation(int param1, int param2) {};
}
