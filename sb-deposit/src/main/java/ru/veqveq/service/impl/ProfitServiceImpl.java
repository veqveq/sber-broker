package ru.veqveq.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.core.model.Money;
import ru.veqveq.mapper.MoneyMapper;
import ru.veqveq.model.Account;
import ru.veqveq.model.Profit;
import ru.veqveq.repo.AccountRepository;
import ru.veqveq.repo.ProfitRepository;
import ru.veqveq.service.ProfitService;
import ru.veqveq.service.FreeCashService;
import ru.veqveq.service.OperationService;
import ru.veqveq.utils.CalculatedDateUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfitServiceImpl implements ProfitService {
    private final ProfitRepository profitRepository;
    private final AccountRepository accountRepository;
    private final FreeCashService freeCashService;
    private final OperationService operationService;
    private final MoneyMapper moneyMapper;

    @PostConstruct
    void start() {
        this.checkMissedCalculatedDays();
    }

    @Override
    public Profit calculateByPeriod(Account account, Pair<LocalDate, LocalDate> calculatedPeriod) {
        List<FreeCashDto> freeCashHistory = freeCashService.getAllHistory(account.getId());
        Money profitMoney = account.getCalculationStrategy()
                .getHandler()
                .calculateProfit(account, freeCashHistory, calculatedPeriod);
        Profit profit = moneyMapper.toProfit(profitMoney, account, calculatedPeriod);
        if (account.getCapitalized()) {
            operationService.create(profit, calculatedPeriod);
        }
        return profitRepository.save(profit);
    }

    @Override
    public void checkMissedCalculatedDays() {
        accountRepository.findAll()
                .forEach(account -> {
                    List<LocalDate> profitCalendar = CalculatedDateUtils.getCalculatedDateList(account);
                    List<LocalDate> calculationCalendar = account.getProfits()
                            .stream()
                            .map(Profit::getCalculatedDate)
                            .collect(Collectors.toList());
                    profitCalendar.removeAll(calculationCalendar);
                    profitCalendar.forEach(calcDate -> {
                        Pair<LocalDate, LocalDate> calcPeriod = CalculatedDateUtils.getCalculatedPeriod(account, calcDate);
                        calculateByPeriod(account, calcPeriod);
                    });
                });
    }
}
