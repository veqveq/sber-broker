package ru.veqveq.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.veqveq.core.model.CurrencyType;
import ru.veqveq.core.model.OperationStatus;

import java.time.LocalDate;

@Data
public abstract class AbstractOperationDto {
    @Schema(description = "Идентификатор операции")
    private Long id;

    @Schema(description = "Валюта операции")
    private CurrencyType currency;

    @Schema(description = "Объем ДС по операции/Цена актива по операции")
    private Double cost;

    @Schema(description = "Дата операции")
    private LocalDate operationDate;

    @Schema(description = "Статус операции")
    private OperationStatus status;
}
