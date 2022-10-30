package com.fdmgroup.mony.service;

import com.fdmgroup.mony.exception.BankAccountNotFoundException;
import com.fdmgroup.mony.model.BankAccount;
import com.fdmgroup.mony.repository.BankAccountRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    private BankAccountService bankAccountService;

    @Mock
    BankAccountRepository mockBankAccountRepository;

    @Mock
    BankAccount mockBankAccount;

    @BeforeEach
    void setup(){
        bankAccountService = new BankAccountService(mockBankAccountRepository);
    }

    @Test
    void test_saveBankAccount(){
        bankAccountService.save(mockBankAccount);

        verify(mockBankAccountRepository).save(mockBankAccount);
    }

    @Test
    void test_Delete(){
        bankAccountService.delete(mockBankAccount);

        verify(mockBankAccountRepository).delete(mockBankAccount);
    }

    @Test
    void test_GetBankAccount_ByAccountNumber(){
        String accountNumber = "123";
        when(mockBankAccountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(mockBankAccount));

        assertEquals(mockBankAccount, bankAccountService.getBankAccountByAccountNumber(accountNumber));
    }

    @Test
    void test_GetBankAccount_ByAccountNumber_ThrowBankAccountNotFoundException(){
        String accountNumber = "123";
        when(mockBankAccountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class, ()->bankAccountService.getBankAccountByAccountNumber(accountNumber));
    }

    @Test
    void test_GetBankAccount_ByUserId(){
        long user_id = 1;
        List<BankAccount> bankAccounts = List.of(mockBankAccount);
        when(mockBankAccountRepository.findByUserId(user_id)).thenReturn(bankAccounts);

        assertEquals(bankAccounts, bankAccountService.getAllBankAccountsByUserId(user_id));
    }

}
