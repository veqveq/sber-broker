package ru.veqveq.dto;

import lombok.Builder;
import lombok.Data;
import ru.veqveq.core.model.Money;
import ru.veqveq.model.Account;

import java.time.LocalDate;

@Data
@Builder
public class TransferMoneyDto {
    private Account fromAccount;
    private Account toAccount;
    private Money money;
    private LocalDate operationDate;
}
