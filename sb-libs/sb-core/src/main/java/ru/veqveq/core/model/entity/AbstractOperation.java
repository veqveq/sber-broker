package ru.veqveq.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.veqveq.core.model.CurrencyType;
import ru.veqveq.core.model.OperationStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class AbstractOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "operation_date")
    private LocalDate operationDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OperationStatus status;
}
