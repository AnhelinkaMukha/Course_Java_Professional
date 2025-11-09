package ru.otus.myannotation;

@SuppressWarnings("java:S106")
public class MyTests {

    @Before
    void setUp1() {
        System.out.println("Hello From Set Up 1:)");
    }

    @Before
    void setUp2() {
        System.out.println("Hello From Set Up 2:)");
    }

    @Test
    void test1() {
        System.out.println("hello from test1!");
    }

    @Test
    void test2() {
        System.out.println("hello from test2!");
    }

    @After
    void cleanUp1() {
        System.out.println("hello from cleanUp[After] 1");
    }

    @After
    void cleanUp2() {
        System.out.println("hello from cleanUp[After] 2");
    }
}
