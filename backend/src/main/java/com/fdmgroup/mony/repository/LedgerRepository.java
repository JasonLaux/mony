package com.fdmgroup.mony.repository;
import com.fdmgroup.mony.model.Ledger;
import com.fdmgroup.mony.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LedgerRepository extends JpaRepository<Ledger, Long> {
    boolean existsByName(String name);

    List<Ledger> findLedgersByUsersId(long id);

    Optional<Ledger> findByName(String name);

}
