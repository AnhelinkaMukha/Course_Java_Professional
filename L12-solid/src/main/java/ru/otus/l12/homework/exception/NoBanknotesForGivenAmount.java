package ru.otus.l12.homework.exception;

public class NoBanknotesForGivenAmount extends RuntimeException{
    public NoBanknotesForGivenAmount(String message) {
        super(message);
    }

}
