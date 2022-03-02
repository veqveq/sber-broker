package ru.veqveq.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.veqveq.core.model.entity.AbstractAccount;
import ru.veqveq.model.strategy.CalculationStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
public class Account extends AbstractAccount {
    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "calculation_strategy")
    @Enumerated(EnumType.STRING)
    private CalculationStrategy calculationStrategy;

    @Column(name = "calculated_day")
    private Integer calculatedDay;

    @Column(name = "capitalized")
    private Boolean capitalized;

    @OneToOne
    @JoinColumn(name = "interest_account_id")
    private Account interestAccount;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operation> operations;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private FreeCash freeCash;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profit> profits;


}
