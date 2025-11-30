package ru.otus.l12.homework;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Banknote {
    @Setter
    private BanknoteAmount banknoteAmount;

    Banknote(BanknoteAmount banknoteAmount){
        this.banknoteAmount = banknoteAmount;
    }
}
