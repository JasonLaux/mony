package com.fdmgroup.mony.repository;
import com.fdmgroup.mony.model.Ledger;
import com.fdmgroup.mony.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameOrEmail(String username, String email);

//    boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findUsersByLedgersId(long id);

    Optional<User> findUserByUsername(String username);

}
