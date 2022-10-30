package com.fdmgroup.mony.model;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Bill {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "payee")
    private String payee;

    @ManyToOne
    @JoinColumn(name = "ledger_id", nullable = false)
    private Ledger ledger;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

}
