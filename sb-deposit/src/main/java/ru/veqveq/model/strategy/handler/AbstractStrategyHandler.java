package ru.veqveq.model.strategy.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.core.model.Money;
import ru.veqveq.model.Account;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractStrategyHandler {
    private final Integer calculatedPeriodLength;
    private final TemporalUnit unit;

    protected abstract BigDecimal getRate(Account account);

    protected abstract List<FreeCashDto> getCalculatedDays(List<FreeCashDto> freeCashHistory, Pair<LocalDate, LocalDate> calculationPeriod);

    public Money calculateProfit(Account account, List<FreeCashDto> allHistory, Pair<LocalDate, LocalDate> calculationPeriod) {
        AbstractStrategyHandler strategyHandler = account.getCalculationStrategy().getHandler();
        BigDecimal realMonthRate = strategyHandler.getRate(account).divide(BigDecimal.valueOf(100L), RoundingMode.HALF_UP);
        List<FreeCashDto> calculatedDays = strategyHandler.getCalculatedDays(allHistory, calculationPeriod);
        return calculatedDays.stream()
                .map(freeCashDto -> {
                    Money money = new Money(freeCashDto.getCurrency());
                    money.setAmount(freeCashDto.getAmount().multiply(realMonthRate).doubleValue());
                    return money;
                }).reduce(Money::sum).orElse(null);
    }

    protected FreeCashDto getPreviousHistoryDay(List<FreeCashDto> freeCashHistory, LocalDate beforeDay) {
        Map<LocalDate, List<FreeCashDto>> historyMap = freeCashHistory
                .stream()
                .sorted(Comparator.comparing(FreeCashDto::getChangeDate).reversed())
                .collect(Collectors.groupingBy(FreeCashDto::getChangeDate));
        LocalDate startHistoryDate = (LocalDate) historyMap.keySet().toArray()[0];
        FreeCashDto result = getMax(historyMap.get(beforeDay));
        while (Objects.isNull(result) && beforeDay.isAfter(startHistoryDate)) {
            beforeDay = beforeDay.minusDays(1);
            result = getMax(historyMap.get(beforeDay));
        }
        if (Objects.isNull(result)) {
            throw new RuntimeException("Не найдена дата для расчёта процентов");
        }
        return result;
    }

    protected boolean dateBetween(LocalDate date, Pair<LocalDate, LocalDate> period) {
        return date.compareTo(period.getLeft()) > -1 && date.compareTo(period.getRight()) < 1;
    }

    protected FreeCashDto getMin(List<FreeCashDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream()
                .min(Comparator.comparing(FreeCashDto::getAmount))
                .orElse(null);
    }

    protected FreeCashDto getMax(List<FreeCashDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream()
                .max(Comparator.comparing(FreeCashDto::getAmount))
                .orElse(null);
    }
}
