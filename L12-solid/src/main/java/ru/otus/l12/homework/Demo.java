package ru.otus.l12.homework;

import ru.otus.l12.homework.service.BanknoteCombinationFinderService;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        BanknoteStorage storage =new BanknoteStorage();
        ATM atm = new ATM(new BanknoteCombinationFinderService(), storage);
        Banknote banknote100 = new Banknote(BanknoteAmount.HUNDRED);
        Banknote banknote50 = new Banknote(BanknoteAmount.FIFTY);
        Banknote banknote1 = new Banknote(BanknoteAmount.ONE);
        Banknote banknote20 = new Banknote(BanknoteAmount.TWENTY);
        Banknote banknote20second = new Banknote(BanknoteAmount.TWENTY);
        Banknote banknote10 = new Banknote(BanknoteAmount.TEN);
        List<Banknote> banknotes = new ArrayList<>(List.of(banknote1, banknote50, banknote100, banknote20, banknote20second, banknote10));
        atm.acceptBanknotes(banknotes);
        System.out.println(storage.getBanknotes());
        System.out.println(atm.howMuchMoneyLeft());

        System.out.println(atm.giveMoney(50));

        System.out.println(atm.howMuchMoneyLeft());

        //to check exception work
//        System.out.println(atm.giveMoney(20));
    }
}
