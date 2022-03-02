package ru.veqveq.service;


import ru.veqveq.core.model.CurrencyType;
import ru.veqveq.core.model.Money;
import ru.veqveq.dto.AccountDto;
import ru.veqveq.dto.TransferMoneyDto;
import ru.veqveq.model.Account;

import java.util.List;

public interface AccountService {
    List<AccountDto> getList();

    AccountDto findById(Long accountId);

    Account getById(Long accountId);

    Long create(AccountDto accountDto, CurrencyType currencyType);

    void update(AccountDto accountDto);

    void delete(Long accountId);
}
