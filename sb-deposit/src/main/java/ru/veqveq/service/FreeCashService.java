package ru.veqveq.service;

import org.apache.commons.lang3.tuple.Pair;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.core.model.CurrencyType;
import ru.veqveq.model.Account;
import ru.veqveq.model.FreeCash;
import ru.veqveq.model.Operation;

import java.time.LocalDate;
import java.util.List;

public interface FreeCashService {
    FreeCash create(Account account, CurrencyType currencyType);

    void update(Operation operation);

    FreeCashDto getByAccountId(Long accountId);

    List<FreeCashDto> getAllHistory(Long accountId);

    List<FreeCashDto> getHistory(Long accountId, Pair<LocalDate,LocalDate> calculatedPeriod);
}
