package ru.veqveq.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.core.model.CashOperationType;
import ru.veqveq.core.model.CurrencyType;
import ru.veqveq.core.model.Money;
import ru.veqveq.core.service.impl.FreeCashHistoryReaderImpl;
import ru.veqveq.mapper.FreeCashMapper;
import ru.veqveq.mapper.MoneyMapper;
import ru.veqveq.model.Account;
import ru.veqveq.model.FreeCash;
import ru.veqveq.model.Operation;
import ru.veqveq.repo.FreeCashRepository;
import ru.veqveq.service.AccountService;
import ru.veqveq.service.FreeCashService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class FreeCashServiceImpl implements FreeCashService {
    private final FreeCashRepository freeCashRepository;
    private final AccountService accountService;
    private final MoneyMapper moneyMapper;
    private final FreeCashMapper freeCashMapper;
    private final FreeCashHistoryReaderImpl historyReader;

    @Override
    public FreeCash create(Account account, CurrencyType currencyType) {
        return FreeCash.builder()
                .account(account)
                .currency(currencyType)
                .amount(BigDecimal.ZERO)
                .changeDate(account.getCreationDate())
                .build();
    }

    @Override
    public void update(Operation operation) {
        if (operation.getType() == CashOperationType.OUTPUT) {
            operation.setCost(operation.getCost().negate());
        }
        FreeCash freeCash = operation.getAccount().getFreeCash();
        freeCash.setChangeDate(operation.getOperationDate());
        Money accountMoney = moneyMapper.toMoney(freeCash);
        Money operationMoney = moneyMapper.toMoney(operation);
        freeCashMapper.merge(freeCash, accountMoney.sum(operationMoney));
        freeCashRepository.save(freeCash);
    }

    @Override
    public FreeCashDto getByAccountId(Long accountId) {
        return freeCashMapper.toDto(freeCashRepository.getByAccount_Id(accountId)
                .orElseThrow(() -> new RuntimeException("Не найден остаток по счёту")));
    }

    @Override
    public List<FreeCashDto> getAllHistory(Long accountId) {
        Account account = accountService.getById(accountId);
        Pair<LocalDate, LocalDate> allHistoryPeriod = Pair.of(account.getCreationDate(), LocalDate.now());
        return getHistory(accountId, allHistoryPeriod);
    }

    @Override
    public List<FreeCashDto> getHistory(Long accountId, Pair<LocalDate, LocalDate> calculatedPeriod) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("account_id", accountId);
        paramsMap.put("from_date", calculatedPeriod.getLeft());
        paramsMap.put("to_date", calculatedPeriod.getRight());
        return historyReader.readHistory(paramsMap);
    }


}
