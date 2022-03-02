package ru.veqveq.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.veqveq.core.dto.AbstractOperationDto;
import ru.veqveq.core.model.CashOperationType;
import ru.veqveq.core.model.OperationStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Операция с остатком ДС на счёте")
public class OperationDto extends AbstractOperationDto {
    @Schema(description = "Название счёта")
    private String accountName;

    @Schema(description = "Тип операции")
    private CashOperationType type;

    @Schema(description = "Описание операции")
    private String description;
}
