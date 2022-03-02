package ru.veqveq.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.veqveq.dto.AccountDto;
import ru.veqveq.model.Account;

@Mapper
public interface AccountMapper {
    @Mapping(target = "interestAccount", ignore = true)
    Account toEntity(AccountDto source);

    @Mapping(target = "interestAccountId", source = "interestAccount.id")
    AccountDto toDto(Account account);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "interestAccount", ignore = true)
    void merge(AccountDto source, @MappingTarget Account account);
}
