package ru.veqveq.model.strategy.handler.impl;

import org.apache.commons.lang3.tuple.Pair;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.model.Account;
import ru.veqveq.model.strategy.handler.AbstractStrategyHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;
import java.util.List;

public class EveryMonthStrategyHandler extends AbstractStrategyHandler {
    public EveryMonthStrategyHandler(Integer calculatedPeriodLength, TemporalUnit unit) {
        super(calculatedPeriodLength, unit);
    }

    @Override
    protected BigDecimal getRate(Account account) {
        return account.getRate().setScale(5, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
    }

    @Override
    protected List<FreeCashDto> getCalculatedDays(List<FreeCashDto> freeCashHistory,
                                                  Pair<LocalDate, LocalDate> calculationPeriod) {
        return List.of(freeCashHistory
                .stream()
                .filter(dto -> dateBetween(dto.getChangeDate(), calculationPeriod))
                .min(Comparator.comparing(FreeCashDto::getAmount))
                .orElse(getPreviousHistoryDay(freeCashHistory, calculationPeriod.getLeft())));
    }

    //    @Override
//    protected List<FreeCashDto> getCalculatedDays(List<FreeCashDto> freeCashHistory) {
//        return List.of(Objects.requireNonNull(freeCashHistory.stream()
//                .min(Comparator.comparing(FreeCashDto::getAmount))
//                .orElse(null)));
//    }
}
