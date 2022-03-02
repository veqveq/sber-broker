package ru.veqveq.core.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.free-cash-history")
public class FreeCashHistoryReaderProperties {
    private String scriptFile;
}
