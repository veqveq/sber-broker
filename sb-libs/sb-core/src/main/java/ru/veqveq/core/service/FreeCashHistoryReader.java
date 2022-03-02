package ru.veqveq.core.service;

import ru.veqveq.core.dto.FreeCashDto;

import java.util.List;
import java.util.Map;

public interface FreeCashHistoryReader {
    List<FreeCashDto> readHistory(Map<String, Object> params);
}
