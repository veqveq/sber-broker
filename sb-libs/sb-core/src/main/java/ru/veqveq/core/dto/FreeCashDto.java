package ru.veqveq.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.veqveq.core.model.CurrencyType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "Остаток ДС по счёту")
public class FreeCashDto {
    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Название счёта")
    private String accountName;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Валюта")
    private CurrencyType currency;

    @Schema(description = "Объем ДС")
    private BigDecimal amount;

    @Schema(description = "Дата изменения остатка на счёте")
    private LocalDate changeDate;
}
