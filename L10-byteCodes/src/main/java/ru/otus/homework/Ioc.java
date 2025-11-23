package ru.otus.homework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {}

    static TestLoggingInterface createTestLogging(TestLoggingInterface testLoggingInterface) {
        InvocationHandler handler = new DemoInvocationHandler(testLoggingInterface);
        return (TestLoggingInterface) Proxy.newProxyInstance(
                Ioc.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLoggingInterface;
        private final Set<Method> methodsToLog = new HashSet<>();

        DemoInvocationHandler(TestLoggingInterface testLoggingInterface) {
            this.testLoggingInterface = testLoggingInterface;

            for (Method implMethod : testLoggingInterface.getClass().getMethods()) {
                if (implMethod.isAnnotationPresent(Log.class)) {
                    try {
                        Method interfaceMethod = TestLoggingInterface.class.getMethod(
                                implMethod.getName(), implMethod.getParameterTypes());
                        methodsToLog.add(interfaceMethod);
                    } catch (NoSuchMethodException ignored) {

                    }
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methodsToLog.contains(method)) {
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
