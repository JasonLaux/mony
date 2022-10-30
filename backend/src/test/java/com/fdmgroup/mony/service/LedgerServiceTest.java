package com.fdmgroup.mony.service;
import com.fdmgroup.mony.exception.LedgerAlreadyExistsException;
import com.fdmgroup.mony.exception.LedgerNotFoundException;
import com.fdmgroup.mony.exception.UserNotFoundException;
import com.fdmgroup.mony.model.Ledger;
import com.fdmgroup.mony.model.User;
import com.fdmgroup.mony.repository.LedgerRepository;
import com.fdmgroup.mony.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LedgerServiceTest {

    @Mock
    private LedgerRepository mockLedgerRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserService mockUserService;

    @Mock
    private Ledger mockLedger;

    @Mock
    private Ledger mockUpdatedLedger;

    @Mock
    private User mockUser;

    private LedgerService ledgerService;

    @BeforeEach
    void setup(){
        ledgerService = new LedgerService(mockLedgerRepository, mockUserRepository, mockUserService);
    }

    @Test
    void test_findLedger_ById(){
        long id = 1;
        when(mockLedgerRepository.findById(id)).thenReturn(Optional.of(mockLedger));

        assertEquals(mockLedger, ledgerService.findLedgerById(id));

    }

    @Test
    void test_findLedger_ById_ThrowLedgerNotFound(){
        long id = 1;
        when(mockLedgerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(LedgerNotFoundException.class, ()-> ledgerService.findLedgerById(id));

    }

    @Test
    void test_findLedger_ByName(){
        String name = "test";
        when(mockLedgerRepository.findByName(name)).thenReturn(Optional.of(mockLedger));

        assertEquals(mockLedger, ledgerService.findLedgerByName(name));
    }

    @Test
    void test_findLedger_ByName_ThrowLedgerNotFound(){
        String name = "test";
        when(mockLedgerRepository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(LedgerNotFoundException.class, ()-> ledgerService.findLedgerByName(name));

    }

    @Test
    void test_AddLedger_ThrowAlreadyExistsException(){
        when(mockLedgerRepository.existsByName(mockLedger.getName())).thenReturn(true);

        assertThrows(LedgerAlreadyExistsException.class, ()-> ledgerService.addLedger(mockLedger));
    }

    @Test
    void test_AddLedger(){
        when(mockLedgerRepository.existsByName(mockLedger.getName())).thenReturn(false);

        ledgerService.addLedger(mockLedger);

        verify(mockLedgerRepository).save(mockLedger);
    }

    @Test
    void test_deleteLedger_ById(){
        long id = 1;

        ledgerService.deleteLeger(id);

        verify(mockLedgerRepository).deleteById(id);
    }

    @Test
    void test_UpdateLedger(){
        long id = 1;
        when(mockLedgerRepository.findById(id)).thenReturn(Optional.of(mockLedger));

        ledgerService.updateLeger(id, mockUpdatedLedger);

        verify(mockLedger).setName(mockUpdatedLedger.getName());
        verify(mockLedgerRepository).save(mockLedger);
    }

    @Test
    void test_UpdateLedger_ThrowLedgerNotFound(){
        long id = 1;
        when(mockLedgerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(LedgerNotFoundException.class, ()-> ledgerService.updateLeger(id, mockLedger));
    }


    @Test
    void test_AddUserToLedger(){
        long id = 1;
        String name = "test";
        when(mockUserService.getUserById(id)).thenReturn(mockUser);
        when(mockLedgerRepository.findByName(name)).thenReturn(Optional.of(mockLedger));

        ledgerService.addUserToLedger(id, name);

        verify(mockUser).addLedger(mockLedger);
        verify(mockUserRepository).save(mockUser);
    }

    @Test
    void test_RemoveUser_FromLedger(){
        long user_id = 1;
        long ledger_id = 2;
        when(mockUserService.getUserById(user_id)).thenReturn(mockUser);
        when(mockLedgerRepository.findById(ledger_id)).thenReturn(Optional.of(mockLedger));

        ledgerService.removeUserFromLedger(user_id, ledger_id);

        verify(mockUser).removeLedger(mockLedger);
        verify(mockUserRepository).save(mockUser);
    }

    @Test
    void test_FindLedgers_ByUsersId(){
        long id = 1;

        ledgerService.findLedgersByUsersId(id);

        verify(mockLedgerRepository).findLedgersByUsersId(id);
    }


}
