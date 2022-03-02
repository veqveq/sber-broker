package ru.veqveq.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.lang.NonNull;
import ru.veqveq.core.annotation.Default;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Money implements Comparable<Money> {
    private static final int DEFAULT_SCALE = 2;

    @Getter
    @Enumerated(EnumType.STRING)
    private final CurrencyType currency;

    private BigDecimal amount;

    @Setter
    private int scale = DEFAULT_SCALE;

    @Default
    public Money(@JsonProperty("currency") CurrencyType currency) {
        this.currency = currency;
    }

    public Money(CurrencyType currency, double amount) {
        this(currency);
        setAmount(amount);
    }

    public Money(CurrencyType currency, double number, int scale) {
        this(currency, number);
        this.scale = scale;
    }

    public void setAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public BigDecimal getAmount() {
        return amount.setScale(scale, BigDecimal.ROUND_CEILING);
    }

    public Money sum(Money money) throws RuntimeException {
        consistentCurrency(money);
        this.amount = this.amount.add(money.getAmount());
        return this;
    }

    public Money subtract(Money money) {
        consistentCurrency(money);
        this.amount = this.amount.subtract(money.getAmount());
        return this;
    }

    public Money multiply(double multiplier) {
        this.amount = this.amount.multiply(BigDecimal.valueOf(multiplier));
        return this;
    }

    @Override
    public int compareTo(@NonNull Money money) {
        consistentCurrency(money);
        return amount.compareTo(money.getAmount());
    }

    private void consistentCurrency(@NotNull Money money) {
        if (money.getCurrency() != getCurrency()) {
            throw new RuntimeException("Разные валюты");
        }
    }

    @Override
    public String toString() {
        return getAmount() + currency.getLogo();
    }
}

