package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    static TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface)
                Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLoggingInterface;

        DemoInvocationHandler(TestLoggingInterface testLoggingInterface) {
            this.testLoggingInterface = testLoggingInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method implMethod =
                    testLoggingInterface.getClass()
                            .getMethod(method.getName(), method.getParameterTypes());

            if (implMethod.isAnnotationPresent(Log.class)) {
                logger.info("executed method:{}, param:{}", method, args);
            }
            return method.invoke(testLoggingInterface, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "TestLoggingClass=" + testLoggingInterface + '}';
        }
    }
}
