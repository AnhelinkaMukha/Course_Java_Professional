package ru.otus.l12.homework;

import java.util.*;
import ru.otus.l12.homework.exception.NoBanknotesForGivenAmount;
import ru.otus.l12.homework.exception.NotEnoughMoneyException;
import ru.otus.l12.homework.service.BanknoteCombinationFinderService;

public class ATM implements ATMMachine {

    private final BanknoteCombinationFinderService combinationFinder;

    private final BanknoteStorage storage;

    public ATM(BanknoteCombinationFinderService combinationFinder, BanknoteStorage storage) {
        this.combinationFinder = combinationFinder;
        this.storage = storage;
    }

    public void acceptBanknotes(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            storage.add(banknote.getBanknoteAmount());
        }
    }

    public int giveMoney(int amount) {
        int totalAtmAmount = howMuchMoneyLeft();
        if (amount > totalAtmAmount) {
            throw new NotEnoughMoneyException("Requested " + amount + ", but ATM has only " + totalAtmAmount);
        }

        EnumMap<BanknoteAmount, Integer> dispensed =
                combinationFinder.findSmallestFirstCombination(storage.getBanknotes(), amount);

        if (dispensed == null) {
            throw new NoBanknotesForGivenAmount("ATM cannot give exact amount with available banknotes: " + amount);
        }

        // applying combination that we found to main ATM Map
        for (var e : dispensed.entrySet()) {
            BanknoteAmount denom = e.getKey();
            int used = e.getValue();
            storage.getBanknotes().merge(denom, -used, Integer::sum);
        }

        return amount;
    }

    public int howMuchMoneyLeft() {
        int totalSum = 0;
        for (Map.Entry<BanknoteAmount, Integer> b : storage.getBanknotes().entrySet()) {
            totalSum += b.getKey().getValue() * b.getValue();
        }
        return totalSum;
    }
}
