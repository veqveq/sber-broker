package ru.veqveq.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;
import ru.veqveq.model.Account;
import ru.veqveq.model.strategy.handler.AbstractStrategyHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CalculatedDateUtils {
    public Pair<LocalDate, LocalDate> getCalculatedPeriod(Account account, LocalDate withinDate) {
        return getCalculatedPeriodWithinDate(account, withinDate);
    }

    public Pair<LocalDate, LocalDate> getCurrentCalculatedPeriod(Account account) {
        return getCalculatedPeriodWithinDate(account, LocalDate.now());
    }

    public List<LocalDate> getCalculatedDateList(Account account) {
        LocalDate checkedDate = getStartCalculatedDate(account);
        List<LocalDate> result = new ArrayList<>();
        while (checkedDate.compareTo(LocalDate.now()) < 1) {
            result.add(checkedDate);
            checkedDate = checkedDate.plusMonths(1L);
        }
        return result;
    }

    private LocalDate getStartCalculatedDate(Account account) {
        LocalDate creationDate = account.getCreationDate();
        Integer calculatedDay = account.getCalculatedDay();
        return LocalDate.of(creationDate.getYear(), creationDate.getMonth(), calculatedDay).plusMonths(1L);
    }

    private Pair<LocalDate, LocalDate> getCalculatedPeriodWithinDate(Account account, LocalDate date) {
        AbstractStrategyHandler strategyHandler = account.getCalculationStrategy().getHandler();
        Integer calculatedDay = account.getCalculatedDay();
        if (date.getDayOfMonth() < calculatedDay) {
            date = date.plusMonths(1L);
        }
        LocalDate endDate = date.plusDays(calculatedDay - date.getDayOfMonth());
        LocalDate startDate = endDate.minus(strategyHandler.getCalculatedPeriodLength(), strategyHandler.getUnit()).plusDays(1L);
        if (startDate.isBefore(account.getCreationDate())) {
            startDate = account.getCreationDate();
        }
        return Pair.of(startDate, endDate);
    }
}
