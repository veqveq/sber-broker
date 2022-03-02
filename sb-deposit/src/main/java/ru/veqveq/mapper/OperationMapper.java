package ru.veqveq.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.veqveq.dto.OperationDto;
import ru.veqveq.dto.TransferMoneyDto;
import ru.veqveq.model.Account;
import ru.veqveq.model.Operation;
import ru.veqveq.model.Profit;

@Mapper
public interface OperationMapper {
    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "type", source = "source.type")
    @Mapping(target = "status", source = "source.status")
    @Mapping(target = "account", source = "operationAccount")
    Operation toEntity(OperationDto source, Account operationAccount);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cost", source = "amount")
    @Mapping(target = "operationDate", source = "calculatedDate")
    @Mapping(target = "account", source = "account.interestAccount")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "type", constant = "INPUT")
    Operation toEntity(Profit profit);

    @Mapping(target = "accountName", source = "account.name")
    OperationDto toDto(Operation source);

    @Mapping(target = "type", constant = "INPUT")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "account", source = "toAccount")
    @Mapping(target = "cost", source = "money.amount")
    @Mapping(target = "currency", source = "money.currency")
    @Mapping(target = "description", source = "fromAccount", qualifiedByName = "setInputDescription")
    Operation toInputOperation(TransferMoneyDto source);

    @Mapping(target = "type", constant = "OUTPUT")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "account", source = "fromAccount")
    @Mapping(target = "cost", source = "money.amount")
    @Mapping(target = "currency", source = "money.currency")
    @Mapping(target = "description", source = "toAccount", qualifiedByName = "setOutputDescription")
    Operation toOutputOperation(TransferMoneyDto source);

    @Named("setInputDescription")
    default String setInputDescription(Account toAccount) {
        return String.format("Поступление денежных средств со счёта #%d: %s",
                toAccount.getId(),
                toAccount.getName());
    }

    @Named("setOutputDescription")
    default String setOutputDescription(Account fromAccount) {
        return String.format("Перевод денежных средств на счёт #%d: %s",
                fromAccount.getId(),
                fromAccount.getName());
    }
}