package ru.veqveq.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.core.model.Money;
import ru.veqveq.dto.ProfitSummaryDto;
import ru.veqveq.mapper.AccountMapper;
import ru.veqveq.mapper.FreeCashMapper;
import ru.veqveq.mapper.MoneyMapper;
import ru.veqveq.model.Account;
import ru.veqveq.model.Profit;
import ru.veqveq.repo.AccountRepository;
import ru.veqveq.service.StatisticService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final AccountRepository accountRepository;
    private final FreeCashMapper freeCashMapper;
    private final AccountMapper accountMapper;
    private final MoneyMapper moneyMapper;

    @Override
    public List<FreeCashDto> getAllFreeCash() {
        return accountRepository.findAll()
                .stream()
                .map(Account::getFreeCash)
                .map(freeCashMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfitSummaryDto> getAllProfit() {
        return accountRepository.findAll()
                .stream()
                .map(this::profitSummaryDto)
                .collect(Collectors.toList());
    }

    private ProfitSummaryDto profitSummaryDto(Account account) {
        ProfitSummaryDto result = new ProfitSummaryDto();
        result.setAccount(accountMapper.toDto(account));
        result.setMoney(getProfit(account.getProfits()));
        result.setProfitability(getProfitability(account.getProfits()));
        return result;
    }

    private Money getProfit(List<Profit> profits) {
        return profits.stream()
                .map(moneyMapper::toMoney)
                .reduce(Money::sum)
                .orElse(null);
    }

    private BigDecimal getProfitability(List<Profit> profits) {
        return BigDecimal.valueOf(profits.stream()
                .mapToDouble(profit -> profit.getAccount().getRate().doubleValue())
                .average()
                .orElse(0));
    }
}
