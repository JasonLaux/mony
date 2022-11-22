package com.fdmgroup.mony.model;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BankAccount {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "account_number", length = 30, nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "start_balance")
    private BigDecimal startBalance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bankAccount")
    private List<Bill> bills;
}
