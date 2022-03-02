package ru.veqveq.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.veqveq.core.config.properties.FreeCashHistoryReaderProperties;

@Configuration
@EnableConfigurationProperties(value = FreeCashHistoryReaderProperties.class)
public class MainConfig {
}
