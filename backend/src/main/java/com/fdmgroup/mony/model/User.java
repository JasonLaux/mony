package com.fdmgroup.mony.model;
import com.fdmgroup.mony.exception.LedgerAlreadyExistsException;
import com.fdmgroup.mony.exception.LedgerNotFoundException;
import lombok.*;
import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "username", length = 45, nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", length = 45, nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_ledger", joinColumns =
    @JoinColumn(name = "user_id"), inverseJoinColumns =
    @JoinColumn(name = "ledger_id"))
    private Set<Ledger> ledgers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<BankAccount> bankAccounts;

    public void addLedger(Ledger ledger){
        Optional<Ledger> maybe_ledger = this.ledgers.stream().filter(item ->(long) item.getId() == ledger.getId()).findFirst();
        if(maybe_ledger.isEmpty()) {
            this.ledgers.add(ledger);
            ledger.getUsers().add(this);
        }
        else {
            throw new LedgerAlreadyExistsException();
        }
    }

    public void removeLedger(Ledger ledger){
        Optional<Ledger> maybe_ledger = this.ledgers.stream().filter(item ->(long) item.getId() == ledger.getId()).findFirst();
        if(maybe_ledger.isEmpty()){
            throw new LedgerNotFoundException();
        }
        this.ledgers.remove(ledger);
        ledger.getUsers().remove(this);
    }

}
