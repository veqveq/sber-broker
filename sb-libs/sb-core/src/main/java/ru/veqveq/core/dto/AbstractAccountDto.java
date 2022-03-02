package ru.veqveq.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class AbstractAccountDto {
    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Дата открытия счёта")
    private LocalDate creationDate;

    @Schema(description = "Имя счёта")
    private String name;
}
