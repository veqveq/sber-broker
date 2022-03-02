package ru.veqveq.service;

import org.apache.commons.lang3.tuple.Pair;
import ru.veqveq.dto.OperationDto;
import ru.veqveq.dto.TransferMoneyDto;
import ru.veqveq.model.Operation;
import ru.veqveq.model.Profit;

import java.time.LocalDate;
import java.util.List;

public interface OperationService {
    List<Long> create(List<OperationDto> operationList, Long accountId);

    List<Long> create(List<Operation> operationList);

    Long create(Profit profit, Pair<LocalDate, LocalDate> calculatedPeriod);

    void transferMoney(TransferMoneyDto dto);

    List<OperationDto> findAll();

    List<OperationDto> findAllByAccountId(Long accountId);
}
