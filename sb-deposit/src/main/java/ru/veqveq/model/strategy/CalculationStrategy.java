package ru.veqveq.model.strategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.veqveq.model.strategy.handler.AbstractStrategyHandler;
import ru.veqveq.model.strategy.handler.impl.EveryDayStrategyHandler;
import ru.veqveq.model.strategy.handler.impl.EveryMonthStrategyHandler;

import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
public enum CalculationStrategy {
    EVERY_MONTH(new EveryMonthStrategyHandler(1, ChronoUnit.MONTHS)),
    EVERY_DAY(new EveryDayStrategyHandler(1, ChronoUnit.MONTHS));

    @Getter
    private final AbstractStrategyHandler handler;
}
