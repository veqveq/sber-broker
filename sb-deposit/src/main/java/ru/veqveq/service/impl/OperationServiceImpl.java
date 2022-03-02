package ru.veqveq.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veqveq.core.model.CashOperationType;
import ru.veqveq.core.model.OperationStatus;
import ru.veqveq.dto.OperationDto;
import ru.veqveq.dto.TransferMoneyDto;
import ru.veqveq.mapper.OperationMapper;
import ru.veqveq.model.Account;
import ru.veqveq.model.FreeCash;
import ru.veqveq.model.Operation;
import ru.veqveq.model.Profit;
import ru.veqveq.repo.OperationRepository;
import ru.veqveq.service.AccountService;
import ru.veqveq.service.FreeCashService;
import ru.veqveq.service.OperationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final AccountService accountService;
    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    private final FreeCashService freeCashService;

    @Override
    public List<Long> create(List<OperationDto> operationList, Long accountId) {
        Account account = accountService.getById(accountId);
        List<Operation> operations = operationList.stream()
                .map(dto -> operationMapper.toEntity(dto, account))
                .collect(Collectors.toList());
        return create(operations);
    }

    @Override
    public List<Long> create(List<Operation> operationList) {
        return operationList.stream()
                .map(this::create)
                .map(Operation::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Long create(Profit profit, Pair<LocalDate, LocalDate> calculatedPeriod) {
        Account account = profit.getAccount();
        Operation operation = operationMapper.toEntity(profit);
        operation.setDescription(String.format("Зачисление процентов от %s", calculatedPeriod.getRight().toString()));
        if (account.getCapitalized()) {
            operation = create(operation);
        }
        return operation.getId();
    }

    @Override
    public void transferMoney(TransferMoneyDto dto) {
        List<Operation> transferOperations = List.of(
                operationMapper.toInputOperation(dto),
                operationMapper.toOutputOperation(dto)
        );
        create(transferOperations);
    }

    @Override
    public List<OperationDto> findAllByAccountId(Long accountId) {
        return accountService.getById(accountId)
                .getOperations()
                .stream()
                .map(operationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OperationDto> findAll() {
        return operationRepository.findAll()
                .stream()
                .map(operationMapper::toDto)
                .collect(Collectors.toList());
    }

    private Operation create(Operation operation) {
        FreeCash freeCash = operation.getAccount().getFreeCash();
        try {
            validateOperation(freeCash, operation);
            operation.setStatus(OperationStatus.ACTIVE);
            freeCashService.update(operation);
            return operationRepository.save(operation);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateOperation(FreeCash freeCash, Operation operation) throws RuntimeException {
        if (operation.getCurrency() != freeCash.getCurrency()) {
            throw new RuntimeException("Валюта операции не соответствует валюте счёта");
        }
        if (operation.getType() == CashOperationType.OUTPUT &&
                operation.getCost().compareTo(freeCash.getAmount()) > 0) {
            throw new RuntimeException("На счёте не хватает средств для операции вывода");
        }
    }
}
