package ru.veqveq.dictionary.lib.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Операция со счётом")
public class AccountOperationTypeDto {
    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Название операции")
    private String name;
}
