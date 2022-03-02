package ru.veqveq.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.veqveq.core.model.Money;

import java.math.BigDecimal;

@Data
public class ProfitSummaryDto {
    @Schema(description = "Название счёта")
    private AccountDto account;

    @Schema(description = "Доходность, руб")
    private Money money;

    @Schema(description = "Доходность, %")
    private BigDecimal profitability;
}
