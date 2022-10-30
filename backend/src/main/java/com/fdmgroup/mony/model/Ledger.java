package com.fdmgroup.mony.model;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "ledger")
@ToString
@Builder
public class Ledger {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "ledgers", fetch = FetchType.LAZY)
    private Set<User> users;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "ledger", fetch = FetchType.LAZY)
    private Set<Bill> bills;

}
