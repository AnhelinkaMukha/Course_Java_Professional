package ru.otus.l12.homework;

import java.util.EnumMap;

public class BanknoteStorage {

    private final EnumMap<BanknoteAmount, Integer> banknotes = new EnumMap<>(BanknoteAmount.class);

    public void add(BanknoteAmount amount) {
        banknotes.merge(amount, 1, Integer::sum);
    }

    public boolean remove(BanknoteAmount amount) {
        Integer count = banknotes.get(amount);
        if (count == null || count == 0) return false;
        banknotes.put(amount, count - 1);
        return true;
    }

    public int getCount(BanknoteAmount amount) {
        return banknotes.getOrDefault(amount, 0);
    }

    public EnumMap<BanknoteAmount, Integer> getBanknotes() {
        return banknotes;
    }
}
