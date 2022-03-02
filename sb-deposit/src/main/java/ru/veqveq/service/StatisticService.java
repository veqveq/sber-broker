package ru.veqveq.service;

import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.dto.ProfitSummaryDto;

import java.util.List;

public interface StatisticService {
    List<FreeCashDto> getAllFreeCash();

    List<ProfitSummaryDto> getAllProfit();
}
