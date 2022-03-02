package ru.veqveq.controller.internal;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.dto.ProfitSummaryDto;
import ru.veqveq.service.StatisticService;

import java.util.List;

@RestController
@RequestMapping("/internal/v1/statistic/")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/all-free-cash")
    @Operation(summary = "Создание операции ввода/вывод по счёту")
    public List<FreeCashDto> getAllFreeCash() {
        return statisticService.getAllFreeCash();
    }

    @GetMapping("/all-profit")
    @Operation(summary = "Получение процентов по всем счетам")
    public List<ProfitSummaryDto> getAllProfit() {
        return statisticService.getAllProfit();
    }

}
