package ru.otus.l12.homework;

import ru.otus.l12.homework.exception.NoBanknotesForGivenAmount;
import ru.otus.l12.homework.exception.NotEnoughMoneyException;
import ru.otus.l12.homework.service.BanknoteCombinationFinderService;

import java.util.*;

public class ATM {
    private final EnumMap<BanknoteAmount, Integer> allBanknotes =
            new EnumMap<>(BanknoteAmount.class);

    private final BanknoteCombinationFinderService combinationFinder = new BanknoteCombinationFinderService();

    public ATM(){
        for (BanknoteAmount d : BanknoteAmount.values()) {
            allBanknotes.put(d, 0);
        }
    }

    public void acceptBanknotes(List<Banknote> banknotes) {
        for(Banknote banknote: banknotes){
            allBanknotes.merge(banknote.getBanknoteAmount(), 1, Integer::sum);
        }
    }

    public int giveMeMoney(int amount) {
        int totalAtmAmount = howMuchMoneyLeft();
        if (amount > totalAtmAmount) {
            throw new NotEnoughMoneyException(
                    "Requested " + amount + ", but ATM has only " + totalAtmAmount
            );
        }

        EnumMap<BanknoteAmount, Integer> dispensed =
                combinationFinder.findSmallestFirstCombination(allBanknotes, amount);

        if (dispensed == null) {
            throw new NoBanknotesForGivenAmount(
                    "ATM cannot give exact amount with available banknotes: " + amount
            );
        }

        // applying combination that we found to main ATM Map
        for (var e : dispensed.entrySet()) {
            BanknoteAmount denom = e.getKey();
            int used = e.getValue();
            allBanknotes.merge(denom, -used, Integer::sum);
        }

        return amount;
    }

    public int howMuchMoneyLeft() {
        int totalSum = 0;
        for (Map.Entry<BanknoteAmount,Integer> b : allBanknotes.entrySet()) {
            totalSum += b.getKey().getValue() * b.getValue();
        }
        return totalSum;
    }


}
