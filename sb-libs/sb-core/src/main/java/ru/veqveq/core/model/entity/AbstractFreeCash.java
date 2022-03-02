package ru.veqveq.core.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import ru.veqveq.core.model.CurrencyType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Audited
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractFreeCash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name="change_date")
    private LocalDate changeDate;
}
