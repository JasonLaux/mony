package com.fdmgroup.mony.controller;
import com.fdmgroup.mony.dto.BankAccountDTO;
import com.fdmgroup.mony.dto.BankAccountInput;
import com.fdmgroup.mony.dto.LedgerDTO;
import com.fdmgroup.mony.dto.UserDTO;
import com.fdmgroup.mony.exception.BankAccountNotFoundException;
import com.fdmgroup.mony.exception.LedgerNotFoundException;
import com.fdmgroup.mony.model.BankAccount;
import com.fdmgroup.mony.model.Ledger;
import com.fdmgroup.mony.model.User;
import com.fdmgroup.mony.repository.BankAccountRepository;
import com.fdmgroup.mony.service.BankAccountService;
import com.fdmgroup.mony.service.LedgerService;
import com.fdmgroup.mony.service.UserService;
import com.fdmgroup.mony.util.ModelToDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private UserController userController;

    @Mock
    private UserService mockUserService;

    @Mock
    private LedgerService mockLedgerService;

    @Mock
    private ModelToDTO mockModelToDTO;

    @Mock
    private BankAccountService mockBankAccountService;

    @Mock
    private BankAccountRepository mockBankAccountRepository;

    @Mock
    private User mockUser;

    @Mock
    private User mockModifiedUser;

    @Mock
    private UserDTO mockUserDTO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup(){
        userController = new UserController(mockUserService, mockLedgerService, mockModelToDTO, mockBankAccountService, mockBankAccountRepository, passwordEncoder);
    }

    @Test
    void test_GetUser(){
        long user_id = 1;
        when(mockUserService.getUserById(user_id)).thenReturn(mockUser);
        when(mockModelToDTO.userToDTO(mockUser)).thenReturn(mockUserDTO);

        UserDTO result = userController.getUser(user_id);

        assertEquals(mockUserDTO, result);
    }

    @Test
    void test_UpdateUser(){
        long user_id = 1;
        when(mockUserService.updateUser(user_id, mockModifiedUser)).thenReturn(mockUser);
        when(mockModelToDTO.userToDTO(mockUser)).thenReturn(mockUserDTO);

        UserDTO result = userController.update(user_id, mockModifiedUser);

        assertEquals(mockUserDTO, result);
    }

    @Test
    void test_DeleteUser(){
        long user_id = 1;

        String result = userController.delete(user_id);

        verify(mockUserService).deleteUserById(user_id);
        assertEquals("Deleted User with ID " + user_id, result);
    }

    @Test
    void test_GetAllLedgers(){
        long user_id = 1;
        Ledger ledger1 = mock(Ledger.class);
        Ledger ledger2 = mock(Ledger.class);
        LedgerDTO ledgerDTO1 = mock(LedgerDTO.class);
        LedgerDTO ledgerDTO2 = mock(LedgerDTO.class);
        List<Ledger> ledgerList = List.of(ledger1, ledger2);
        List<LedgerDTO> ledgerDTOS = List.of(ledgerDTO1, ledgerDTO2);
        when(mockLedgerService.findLedgersByUsersId(user_id)).thenReturn(ledgerList);
        when(mockModelToDTO.ledgerToDTO(ledger1)).thenReturn(ledgerDTO1);
        when(mockModelToDTO.ledgerToDTO(ledger2)).thenReturn(ledgerDTO2);

        assertEquals(ledgerDTOS, userController.getAllLedgers(user_id));
    }

    @Test
    void test_GetLedger(){
        long user_id = 1;
        long ledger_id = 1;
        Ledger ledger = mock(Ledger.class);
        LedgerDTO ledgerDTO = mock(LedgerDTO.class);
        List<Ledger> ledgerList = List.of(ledger);
        when(ledger.getId()).thenReturn(ledger_id);
        when(mockLedgerService.findLedgersByUsersId(user_id)).thenReturn(ledgerList);
        when(mockModelToDTO.ledgerToDTO(ledger)).thenReturn(ledgerDTO);

        assertEquals(ledgerDTO, userController.getLedger(user_id, ledger_id));
    }

    @Test
    void test_GetLedger_ThrowLedgerNotFoundException(){
        long user_id = 1;
        long ledger_id = 1;
        List<Ledger> ledgerList = List.of();
        when(mockLedgerService.findLedgersByUsersId(user_id)).thenReturn(ledgerList);

        assertThrows(LedgerNotFoundException.class, ()-> userController.getLedger(user_id, ledger_id));
    }

    @Test
    void test_AddLedger(){
        long user_id = 1;
        String ledger_name = "test";

        assertEquals("Successfully add ledger with name " + ledger_name, userController.addLedger(user_id, ledger_name));
        verify(mockLedgerService).addUserToLedger(user_id, ledger_name);
    }

    @Test
    void test_RemoveLedger(){
        long user_id = 1;
        long ledger_id = 1;

        assertEquals("Successfully delete ledger with id " + ledger_id, userController.removeLedger(user_id, ledger_id));
        verify(mockLedgerService).removeUserFromLedger(user_id, ledger_id);
    }

    @Test
    void test_GetALLBankAccounts(){
        long user_id = 1;

        BankAccount bankAccount1 = mock(BankAccount.class);
        BankAccount bankAccount2 = mock(BankAccount.class);
        BankAccountDTO bankAccountDTO1 = mock(BankAccountDTO.class);
        BankAccountDTO bankAccountDTO2 = mock(BankAccountDTO.class);
        List<BankAccount> bankAccounts = List.of(bankAccount1, bankAccount2);
        List<BankAccountDTO> bankAccountDTOS = List.of(bankAccountDTO1, bankAccountDTO2);
        when(mockModelToDTO.bankAccountToDTO(bankAccount1)).thenReturn(bankAccountDTO1);
        when(mockModelToDTO.bankAccountToDTO(bankAccount2)).thenReturn(bankAccountDTO2);
        when(mockBankAccountService.getAllBankAccountsByUserId(user_id)).thenReturn(bankAccounts);

        assertEquals(bankAccountDTOS, userController.getAllBankAccounts(user_id));
    }

    @Test
    void test_GetBankAccount(){
        long user_id = 1;
        long bankAccount_id = 1;
        BankAccount bankAccount = mock(BankAccount.class);
        BankAccountDTO bankAccountDTO = mock(BankAccountDTO.class);
        List<BankAccount> bankAccounts = List.of(bankAccount);
        when(bankAccount.getId()).thenReturn(bankAccount_id);
        when(mockBankAccountService.getAllBankAccountsByUserId(user_id)).thenReturn(bankAccounts);
        when(mockModelToDTO.bankAccountToDTO(bankAccount)).thenReturn(bankAccountDTO);

        assertEquals(bankAccountDTO, userController.getBankAccount(user_id, bankAccount_id));

    }

    @Test
    void test_CreateBankAccount(){
        long user_id = 1;
        BankAccountInput bankAccountInput = mock(BankAccountInput.class);

        userController.createBankAccount(user_id, bankAccountInput);

        verify(mockBankAccountRepository).findByAccountNumber(bankAccountInput.getAccountNumber());
    }

    @Test
    void test_DeleteBankAccount(){
        long user_id = 1;
        long account_id = 2;
        BankAccount bankAccount = mock(BankAccount.class);
        List<BankAccount> bankAccounts = List.of(bankAccount);
        when(bankAccount.getId()).thenReturn(account_id);
        when(mockBankAccountService.getAllBankAccountsByUserId(user_id)).thenReturn(bankAccounts);

        assertEquals("Successfully delete the bank account with id " + account_id + " of user " + user_id, userController.deleteBankAccount(user_id, account_id));
        verify(mockBankAccountService).delete(bankAccount);
    }

    @Test
    void test_DeleteBankAccount_ThrowBankAccountNotFoundException(){
        long user_id = 1;
        long account_id = 2;
        List<BankAccount> bankAccounts = List.of();
        when(mockBankAccountService.getAllBankAccountsByUserId(user_id)).thenReturn(bankAccounts);

        assertThrows(BankAccountNotFoundException.class, ()-> userController.deleteBankAccount(user_id, account_id));
    }

//    @Test
//    void test_UpdateBankAccount(){
//        long user_id = 1;
//        long account_id = 2;
//        String accountNumber = "123";
//        String bankName = "test";
//        BigDecimal start_balance = BigDecimal.valueOf(100);
//        BankAccountInput bankAccountInput = mock(BankAccountInput.class);
//        BankAccount bankAccount = mock(BankAccount.class);
//        BankAccountDTO bankAccountDTO = mock(BankAccountDTO.class);
//        List<BankAccount> bankAccounts = List.of(bankAccount);
//        when(mockBankAccountService.getAllBankAccountsByUserId(user_id)).thenReturn(bankAccounts);
//        when(bankAccount.getId()).thenReturn(account_id);
//        when(bankAccountInput.getAccountNumber()).thenReturn(accountNumber);
//        when(bankAccountInput.getBankName()).thenReturn(bankName);
//        when(bankAccountInput.getStartBalance()).thenReturn(start_balance);
//        when(mockBankAccountService.save(bankAccount)).thenReturn(bankAccount);
//        when(mockModelToDTO.bankAccountToDTO(bankAccount)).thenReturn(bankAccountDTO);
//
//        assertEquals(bankAccountDTO, userController.updateBankAccount(user_id, account_id, bankAccountInput));
//        verify(bankAccount).setAccountNumber(accountNumber);
//        verify(bankAccount).setBankName(bankName);
//        verify(bankAccount).setBalance(start_balance);
//    }

    @Test
    void test_UpdateBankAccount_ThrowBankAccountNotFoundException(){
        long user_id = 1;
        long account_id = 2;
        BankAccountInput bankAccountInput = mock(BankAccountInput.class);
        List<BankAccount> bankAccounts = List.of();
        when(mockBankAccountService.getAllBankAccountsByUserId(user_id)).thenReturn(bankAccounts);

        assertThrows(BankAccountNotFoundException.class, ()-> userController.updateBankAccount(user_id, account_id, bankAccountInput));
    }
}
