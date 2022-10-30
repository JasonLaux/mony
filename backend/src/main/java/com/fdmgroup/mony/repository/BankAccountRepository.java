package com.fdmgroup.mony.repository;
import com.fdmgroup.mony.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByAccountNumber(String number);

    List<BankAccount> findByUserId(long id);
}
