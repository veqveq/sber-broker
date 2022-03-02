package ru.veqveq.dictionary.lib.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Аккаунт")
public class AccountTypeDto {
    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Доступные операции")
    private List<AccountOperationTypeDto> operationTypes;
}
