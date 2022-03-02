package ru.veqveq.service;

import org.apache.commons.lang3.tuple.Pair;
import ru.veqveq.model.Account;
import ru.veqveq.model.Profit;

import java.time.LocalDate;

public interface ProfitService {
    Profit calculateByPeriod(Account account, Pair<LocalDate, LocalDate> calculatedPeriod);

    void checkMissedCalculatedDays();
}
