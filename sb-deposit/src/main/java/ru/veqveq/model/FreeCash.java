package ru.veqveq.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import ru.veqveq.core.model.entity.AbstractFreeCash;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Audited
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "free_cash")
public class FreeCash extends AbstractFreeCash {
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
