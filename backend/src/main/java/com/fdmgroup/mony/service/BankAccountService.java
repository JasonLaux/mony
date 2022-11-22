package com.fdmgroup.mony.service;
import com.fdmgroup.mony.exception.BankAccountNotFoundException;
import com.fdmgroup.mony.model.BankAccount;
import com.fdmgroup.mony.model.Bill;
import com.fdmgroup.mony.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * Bank account service for handling CRUD operations.
 * @author Jason Liu
 * @version 1.0
 */
@Service
@Transactional
@AllArgsConstructor
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    /**
     * Persist the bank account to the database.
     * @param bankAccount BankAccount.
     * @return            Persisted object.
     */
    public BankAccount save(BankAccount bankAccount){
        return bankAccountRepository.save(bankAccount);
    }

    /**
     * Delete and bank account.
     * @param bankAccount Bank account object.
     */
    public void delete(BankAccount bankAccount){
        bankAccountRepository.delete(bankAccount);
    }

    /**
     * Get bank account object by its account number.
     * @param accountNumber Account number.
     * @return              Bank account object.
     */
    public BankAccount getBankAccountByAccountNumber(String accountNumber){
       Optional<BankAccount> maybe_account = bankAccountRepository.findByAccountNumber(accountNumber);
       if(maybe_account.isEmpty()){
           throw new BankAccountNotFoundException();
       }
       return maybe_account.get();
    }

    /**
     * Get all bank accounts by user id.
     * @param user_id User id.
     * @return        List of BankAccount
     */
    public List<BankAccount> getAllBankAccountsByUserId(long user_id){

        return bankAccountRepository.findByUserId(user_id);
    }

    public BigDecimal calculateBalance(String accountNumber){
        BankAccount bankAccount = getBankAccountByAccountNumber(accountNumber);
        List<Bill> bills = bankAccount.getBills();
        BigDecimal start_balance = bankAccount.getStartBalance();
        if(bills.isEmpty()) {
            return start_balance;
        }
        BigDecimal offset = bills.stream().map(Bill::getAmount).reduce(new BigDecimal("0"), BigDecimal::add);
        return start_balance.add(offset);
    }
}
