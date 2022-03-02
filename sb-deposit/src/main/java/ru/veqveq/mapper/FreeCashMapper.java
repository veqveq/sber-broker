package ru.veqveq.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.core.model.Money;
import ru.veqveq.model.FreeCash;

@Mapper
public interface FreeCashMapper {
    @BeforeMapping
    default void beforeMerge(FreeCash freeCash, Money money) {
        if (freeCash.getCurrency() != money.getCurrency()) {
            throw new RuntimeException("Ошибка маппинга. Разные валюты!");
        }
    }

    @Mapping(target = "currency", ignore = true)
    void merge(@MappingTarget FreeCash freeCash, Money money);

    @Mapping(target = "accountName", source = "account.name")
    FreeCashDto toDto(FreeCash freeCash);
}
