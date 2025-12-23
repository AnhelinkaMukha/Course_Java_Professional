package ru.otus.l12.homework.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import ru.otus.l12.homework.BanknoteAmount;

public class BanknoteCombinationFinderService {
    public EnumMap<BanknoteAmount, Integer> findSmallestFirstCombination(
            Map<BanknoteAmount, Integer> storage, int amount) {
        EnumMap<BanknoteAmount, Integer> dispensed = new EnumMap<>(BanknoteAmount.class);

        BanknoteAmount[] denoms = storage.keySet().toArray(new BanknoteAmount[0]);
        Arrays.sort(denoms, Comparator.comparingInt(BanknoteAmount::getValue)); // smallest first

        if (!findCombination(denoms, 0, amount, storage, dispensed)) {
            return null;
        }

        return dispensed;
    }

    private boolean findCombination(
            BanknoteAmount[] denoms,
            int index,
            int remaining,
            Map<BanknoteAmount, Integer> storage,
            EnumMap<BanknoteAmount, Integer> dispensed) {

        if (remaining == 0) return true;
        if (index == denoms.length) return false;

        BanknoteAmount denom = denoms[index];
        int value = denom.getValue();
        int available = storage.getOrDefault(denom, 0);

        int maxUse = Math.min(remaining / value, available);

        for (int use = maxUse; use >= 0; use--) {
            if (use > 0) {
                dispensed.merge(denom, use, Integer::sum);
            }

            if (findCombination(denoms, index + 1, remaining - use * value, storage, dispensed)) {
                return true;
            }

            if (use > 0) {
                int after = dispensed.get(denom) - use;
                if (after == 0) dispensed.remove(denom);
                else dispensed.put(denom, after);
            }
        }
        return false;
    }
}
