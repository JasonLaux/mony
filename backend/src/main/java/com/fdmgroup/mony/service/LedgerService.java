package com.fdmgroup.mony.service;
import com.fdmgroup.mony.exception.LedgerAlreadyExistsException;
import com.fdmgroup.mony.exception.LedgerNotFoundException;
import com.fdmgroup.mony.model.Ledger;
import com.fdmgroup.mony.model.User;
import com.fdmgroup.mony.repository.LedgerRepository;
import com.fdmgroup.mony.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Ledger service.
 * @author Jason Liu
 * @version 1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository ledgerRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * Find ledger by id. An LedgerNotFoundException will be thrown if there is no matching id.
     * @param id Ledger id.
     * @return   Persisted ledger object.
     */
    public Ledger findLedgerById(long id){
        Optional<Ledger> maybe_ledger = ledgerRepository.findById(id);
        if(maybe_ledger.isEmpty()){
            throw new LedgerNotFoundException();
        }
        return maybe_ledger.get();
    }

    /**
     * Find ledger by its name.
     * @param name Ledger name.
     * @return     Ledger object.
     */
    public Ledger findLedgerByName(String name){
        Optional<Ledger> maybe_ledger = ledgerRepository.findByName(name);
        if(maybe_ledger.isEmpty()){
            throw new LedgerNotFoundException();
        }
        return maybe_ledger.get();
    }

    /**
     * Add ledger to the database using the ledger name.
     * @param ledger Ledger object.
     * @return       Persisted ledger object.
     */
    public Ledger addLedger(Ledger ledger){
        if(ledgerRepository.existsByName(ledger.getName())){
            throw new LedgerAlreadyExistsException();
        }
        return ledgerRepository.save(ledger);
    }

    /**
     * Delete ledger based on its id.
     * @param id Ledger id.
     */
    public void deleteLeger(long id){
        ledgerRepository.deleteById(id);
    }

    /**
     * Update ledger using its name.
     * @param id   Ledger id.
     * @param ledger Ledger object.
     * @return     Persisted ledger object.
     */
    public Ledger updateLeger(long id, Ledger ledger){

        Optional<Ledger> maybeLedger = ledgerRepository.findById(id);

        if(maybeLedger.isEmpty()){
            throw new LedgerNotFoundException();
        }

        Ledger persisted_ledger = maybeLedger.get();

        persisted_ledger.setName(ledger.getName());
        // More features to add.

        return ledgerRepository.save(persisted_ledger);
    }

    /**
     * Add a user to the ledger.
     *
     * @param user_id User id.
     * @param name    Ledger name.
     */
    public void addUserToLedger(long user_id, String name){
        User user = userService.getUserById(user_id);
        Ledger ledger = this.findLedgerByName(name);
        user.addLedger(ledger);
        userRepository.save(user);
    }

    /**
     * Delete the user from the ledger.
     *
     * @param user_id   User id.
     * @param ledger_id Ledger id.
     */
    public void removeUserFromLedger(long user_id, long ledger_id){
        User user = userService.getUserById(user_id);
        Ledger ledger = this.findLedgerById(ledger_id);
        user.removeLedger(ledger);
        userRepository.save(user);
    }

    /**
     * Find ledgers that a user has.
     * @param id User id.
     * @return   List of ledgers.
     */
    public List<Ledger> findLedgersByUsersId(long id){
        return ledgerRepository.findLedgersByUsersId(id);
    }



}
