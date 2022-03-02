package ru.veqveq.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.veqveq.core.config.properties.FreeCashHistoryReaderProperties;
import ru.veqveq.core.dto.FreeCashDto;
import ru.veqveq.core.model.CurrencyType;
import ru.veqveq.core.service.FreeCashHistoryReader;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FreeCashHistoryReaderImpl implements FreeCashHistoryReader {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final FreeCashHistoryReaderProperties properties;
    private RowMapper<FreeCashDto> rowMapper;

    @PostConstruct
    private void init() {
        rowMapper = (result, i) -> FreeCashDto.builder()
                .id(result.getLong("id"))
                .amount(result.getBigDecimal("amount"))
                .currency(CurrencyType.valueOf(result.getString("currency")))
                .changeDate(LocalDate.parse(result.getString("change_date")))
                .build();
    }

    @Override
    public List<FreeCashDto> readHistory(Map<String, Object> params) {
        try {
            String query = Files.readString(loadSqlScript(properties.getScriptFile()));
            List<FreeCashDto> result = jdbcTemplate.query(query, params, rowMapper);
            log.debug("Find {} records if free cash history by params {}", result.size(), params.toString());
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения sql-скрипта!", e);
        }
    }

    private Path loadSqlScript(String path) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext();
        File scriptFile = context.getResource(path).getFile();
        return scriptFile.toPath();
    }
}
