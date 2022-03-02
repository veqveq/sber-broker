package ru.veqveq.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CurrencyType {
    RUB("\u20bd", "Рубли"),
    EURO("\u20ac", "Евро"),
    USD("\u0024", "Доллары");

    private final String logo;

    private final String name;
}
