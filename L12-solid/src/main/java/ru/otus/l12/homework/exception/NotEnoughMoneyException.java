package ru.otus.l12.homework.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException(String message) {
            super(message);
    }
}
