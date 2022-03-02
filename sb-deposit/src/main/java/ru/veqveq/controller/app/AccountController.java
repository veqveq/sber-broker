package ru.veqveq.controller.app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.core.model.CurrencyType;
import ru.veqveq.dto.AccountDto;
import ru.veqveq.service.AccountService;
import ru.veqveq.service.FreeCashService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts/")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final FreeCashService freeCashService;

    @PostMapping
    @Operation(summary = "Создание счёта")
    public Long create(@Parameter(description = "Валюта счёта", required = true)
                       @RequestParam CurrencyType currencyType,
                       @Parameter(description = "Новый счёт", required = true)
                       @RequestBody AccountDto accountDto) {
        return accountService.create(accountDto, currencyType);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение списка счетов")
    public List<AccountDto> findAll() {
        return accountService.getList();
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Получение счёта по идентификатору")
    public AccountDto findById(@Parameter(description = "Идентификатор счёта", required = true)
                               @PathVariable("accountId") Long accountId) {
        return accountService.findById(accountId);
    }

    @PutMapping
    @Operation(summary = "Обновление счёта")
    public void update(@Parameter(description = "Обновленный счёт", required = true)
                       @RequestBody AccountDto accountDto) {
        accountService.update(accountDto);
    }

    @DeleteMapping("/{accountId}")
    @Operation(summary = "Удаление счёта")
    public void delete(@Parameter(description = "Идентификатор счёта")
                       @PathVariable("accountId") Long accountId) {
        accountService.delete(accountId);
    }

    @GetMapping("/{accountId}/free-cash")
    @Operation(summary = "Получение остатков по счёту")
    public FreeCashDto getFreeCash(@Parameter(description = "Идентификатор счёта")
                                   @PathVariable("accountId") Long accountId) {
        return freeCashService.getByAccountId(accountId);
    }

    @GetMapping("/{accountId}/free-cash/history")
    @Operation(summary = "Получение истории изменений остатков по счёту")
    public List<FreeCashDto> getFreeCashHistory(
            @Parameter(description = "Идентификатор счёта")
            @PathVariable("accountId") Long accountId,
            @Parameter(description = "Начало периода")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("fromDate") LocalDate fromDate,
            @Parameter(description = "Конец периода")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("toDate") LocalDate toDate) {
        return freeCashService.getHistory(accountId, Pair.of(fromDate, toDate));
    }
}
