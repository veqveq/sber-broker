package ru.veqveq.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.veqveq.core.model.AccountStatus;
import ru.veqveq.core.model.CurrencyType;
import ru.veqveq.dto.AccountDto;
import ru.veqveq.mapper.AccountMapper;
import ru.veqveq.model.Account;
import ru.veqveq.repo.AccountRepository;
import ru.veqveq.service.AccountService;
import ru.veqveq.service.FreeCashService;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    @Autowired
    private FreeCashService freeCashService;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountDto> getList() {
        return accountRepository.findAll()
                .stream()
                .filter(acc -> acc.getStatus() == AccountStatus.ACTIVE)
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findById(Long accountId) {
        return accountMapper.toDto(getById(accountId));
    }

    @Override
    public Account getById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Не найден аккаунт"));
    }

    @Override
    public Long create(AccountDto accountDto, CurrencyType currencyType) {
        Account account = accountMapper.toEntity(accountDto);
        account.setFreeCash(freeCashService.create(account, currencyType));
        account.setStatus(AccountStatus.ACTIVE);
        account.setStatusTime(Instant.now());
        setInterestAccount(account, accountDto);
        return accountRepository.save(account).getId();
    }

    @Override
    public void update(AccountDto accountDto) {
        Account account = getById(accountDto.getId());
        accountMapper.merge(accountDto, account);
        setInterestAccount(account, accountDto);
        accountRepository.save(account);
    }

    @Override
    public void delete(Long accountId) {
        Account account = getById(accountId);
        if (account.getStatus() == AccountStatus.ACTIVE) {
            account.setStatus(AccountStatus.CLOSE);
            account.setStatusTime(Instant.now());
            accountRepository.save(account);
        }
    }

    private void setInterestAccount(Account account, AccountDto dto) {
        if (BooleanUtils.isFalse(account.getCapitalized())) {
            account.setInterestAccount(null);
        } else {
            if (ObjectUtils.isNotEmpty(dto.getInterestAccountId())) {
                Account interestAccount = getById(dto.getInterestAccountId());
                account.setInterestAccount(interestAccount);
            } else {
                accountRepository.flush();
                account.setInterestAccount(account);
            }
        }
    }
}
