package ru.otus.l12.homework;

import java.util.List;

public interface ATMMachine {
    public void acceptBanknotes(List<Banknote> banknotes);
    public int giveMoney(int amount);
    public int howMuchMoneyLeft();
}
