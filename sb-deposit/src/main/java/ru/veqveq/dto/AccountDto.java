package ru.veqveq.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.veqveq.core.dto.AbstractAccountDto;
import ru.veqveq.model.strategy.CalculationStrategy;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Депозитный счёт")
public class AccountDto extends AbstractAccountDto {
    @Schema(description = "Процентная ставка")
    private BigDecimal rate;

    @Schema(description = "Стратегия начисления процентов")
    private CalculationStrategy calculationStrategy;

    @Schema(description = "День выплаты процентов")
    private Integer calculatedDay;

    @Schema(description = "Капитализация процентов")
    private Boolean capitalized;

    @Schema(description = "Аккаунт для начисления процентов")
    private Long interestAccountId;
}
