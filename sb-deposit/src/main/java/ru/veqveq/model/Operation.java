package ru.veqveq.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.veqveq.core.model.CashOperationType;
import ru.veqveq.core.model.entity.AbstractOperation;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cash_operation")
@NoArgsConstructor
public class Operation extends AbstractOperation {
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CashOperationType type;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    protected Account account;
}
