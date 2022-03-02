package ru.veqveq.model.strategy.handler.impl;

import org.apache.commons.lang3.tuple.Pair;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.model.Account;
import ru.veqveq.model.strategy.handler.AbstractStrategyHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;

public class EveryDayStrategyHandler extends AbstractStrategyHandler {
    public EveryDayStrategyHandler(Integer calculatedPeriodLength, TemporalUnit unit) {
        super(calculatedPeriodLength, unit);
    }

    @Override
    protected BigDecimal getRate(Account account) {
        return account.getRate().setScale(5, RoundingMode.HALF_UP).divide(BigDecimal.valueOf(365L), RoundingMode.HALF_UP);
    }

    @Override
    protected List<FreeCashDto> getCalculatedDays(List<FreeCashDto> freeCashHistory,
                                                  Pair<LocalDate, LocalDate> calculationPeriod) {
        Map<LocalDate, List<FreeCashDto>> historyMap = freeCashHistory.stream()
                .filter(dto -> dateBetween(dto.getChangeDate(), calculationPeriod))
                .collect(Collectors.groupingBy(FreeCashDto::getChangeDate));
        List<FreeCashDto> result = new ArrayList<>();
        LocalDate currentDate = calculationPeriod.getLeft();
        while (currentDate.compareTo(calculationPeriod.getRight()) < 0) {
            result.add(getDay(historyMap, currentDate, freeCashHistory));
            currentDate = currentDate.plusDays(1);
        }
        return result;
    }

    private FreeCashDto getDay(Map<LocalDate, List<FreeCashDto>> historyMap,
                               LocalDate date,
                               List<FreeCashDto> freeCashHistory) {
        FreeCashDto firstDay = getMin(historyMap.get(date));
        return Objects.nonNull(firstDay)
                ? firstDay
                : getPreviousHistoryDay(freeCashHistory, date);
    }
}
