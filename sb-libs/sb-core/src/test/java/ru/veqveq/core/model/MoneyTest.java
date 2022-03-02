package ru.veqveq.core.model;

import org.junit.Test;

public class MoneyTest {
    @Test
    void createMoney() {
        Money rub = new Money(CurrencyType.RUB, 123);
        Money euro = new Money(CurrencyType.EURO, 12.96544, 5);
        Money usd = new Money(CurrencyType.USD, 10);
        System.out.println(rub.toString());
        System.out.println(euro.toString());
        System.out.println(usd.toString());
        rub.sum(rub);
        System.out.println(rub);
        System.out.println(usd.multiply(2));
        System.out.println(usd.multiply(1.5d));
    }
}
