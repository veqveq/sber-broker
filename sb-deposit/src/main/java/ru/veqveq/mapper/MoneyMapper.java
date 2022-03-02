package ru.veqveq.mapper;

import org.apache.commons.lang3.tuple.Pair;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.veqveq.core.model.Money;
import ru.veqveq.model.Account;
import ru.veqveq.model.FreeCash;
import ru.veqveq.model.Operation;
import ru.veqveq.model.Profit;

import java.time.LocalDate;

@Mapper
public interface MoneyMapper {
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "account", source = "operationAccount")
    @Mapping(target = "cost", source = "source.amount")
    Operation toOperation(Money source, Account operationAccount);

    @Mapping(target = "account", source = "cashAccount")
    FreeCash toFreeCash(Money source, Account cashAccount);

    @Mapping(target = "amount", source = "source.amount")
    @Mapping(target = "currency", source = "source.currency")
    @Mapping(target = "account", source = "profitAccount")
    @Mapping(target = "calculatedPeriodStart", source = "calcPeriod.left")
    @Mapping(target = "calculatedPeriodEnd", source = "calcPeriod.right")
    @Mapping(target = "calculatedDate", source = "calcPeriod.right")
    Profit toProfit(Money source, Account profitAccount, Pair<LocalDate, LocalDate> calcPeriod);

    Money toMoney(FreeCash freeCash);

    @Mapping(target = "amount", source = "cost")
    Money toMoney(Operation operation);

    Money toMoney(Profit profit);
}
