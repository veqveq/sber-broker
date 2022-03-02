package ru.veqveq.controller.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.veqveq.core.model.Money;
import ru.veqveq.dto.OperationDto;
import ru.veqveq.dto.TransferMoneyDto;
import ru.veqveq.service.AccountService;
import ru.veqveq.service.OperationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/operations/cash")
@RequiredArgsConstructor
public class OperationsController {
    private final OperationService operationService;
    private final AccountService accountService;

    @PostMapping("/{accountId}/create")
    @Operation(summary = "Создание операции ввода/вывод по счёту")
    public List<Long> create(@Parameter(description = "Идентификатор счёта")
                             @PathVariable("accountId") Long accountId,
                             @Parameter(description = "Список операций с остатком ДС")
                             @RequestBody List<OperationDto> operations) {
        return operationService.create(operations, accountId);
    }

    @PostMapping("/{fromAccountId}/create/transfer/{toAccountId}")
    @Operation(summary = "Создание операции перевода между счетами")
    public void transfer(@Parameter(description = "Идентификатор счета-отправителя средств")
                         @PathVariable("fromAccountId") Long fromAccountId,
                         @Parameter(description = "Идентификатор счета-получателя средств")
                         @PathVariable("toAccountId") Long toAccountId,
                         @Parameter(description = "Дата операции") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                         @RequestParam("operationDate") LocalDate operationDate,
                         @RequestBody Money money) {
        operationService.transferMoney(
                TransferMoneyDto.builder()
                        .fromAccount(accountService.getById(fromAccountId))
                        .toAccount(accountService.getById(toAccountId))
                        .operationDate(Objects.isNull(operationDate) ? LocalDate.now() : operationDate)
                        .money(money)
                        .build());
    }

    @GetMapping
    @Operation(summary = "Получение операций по всем счетам")
    public List<OperationDto> findAll() {
        return operationService.findAll();
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Получение операций по счету")
    public List<OperationDto> create(@Parameter(description = "Идентификатор счёта")
                                     @PathVariable("accountId") Long accountId) {
        return operationService.findAllByAccountId(accountId);
    }
}
