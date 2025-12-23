package ru.otus.l12.homework;

import lombok.Getter;

@Getter
public enum BanknoteAmount {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    HUNDRED(100);

    private final int value;

    BanknoteAmount(int value) {
        this.value = value;
    }
}
