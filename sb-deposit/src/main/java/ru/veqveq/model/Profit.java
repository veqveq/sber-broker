package ru.veqveq.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.veqveq.core.model.CurrencyType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "profit")
@NoArgsConstructor
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "calculated_date")
    private LocalDate calculatedDate;

    @Column(name = "calculated_period_start")
    private LocalDate calculatedPeriodStart;

    @Column(name = "calculated_period_end")
    private LocalDate calculatedPeriodEnd;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}