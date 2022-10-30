package com.fdmgroup.mony.service;
import com.fdmgroup.mony.dto.BillInput;
import com.fdmgroup.mony.exception.BillNotFoundException;
import com.fdmgroup.mony.exception.LedgerAlreadyExistsException;
import com.fdmgroup.mony.exception.LedgerNotFoundException;
import com.fdmgroup.mony.exception.UserNotFoundException;
import com.fdmgroup.mony.model.BankAccount;
import com.fdmgroup.mony.model.Bill;
import com.fdmgroup.mony.model.Ledger;
import com.fdmgroup.mony.model.User;
import com.fdmgroup.mony.repository.BillRepository;
import com.fdmgroup.mony.repository.LedgerRepository;
import com.fdmgroup.mony.repository.UserRepository;
import com.fdmgroup.mony.util.ObjectFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillServiceTest {

    @Mock
    private BillRepository mockBillRepository;

    @Mock
    private LedgerService mockLedgerService;

    @Mock
    private BankAccountService mockBankAccountService;

    @Mock
    private BillInput mockBillInput;

    @Mock
    private Ledger mockLedger;

    @Mock
    private BankAccount mockBankAccount;

    @Mock
    private Bill mockBill;

    @Mock
    private ObjectFactory mockObjectFactory;

    private BillService billService;

    @BeforeEach
    void setup(){
        billService = new BillService(mockBillRepository, mockLedgerService, mockBankAccountService, mockObjectFactory);
    }

    @Test
    void test_AddBill(){
        long ledger_id = 1;
        when(mockLedgerService.findLedgerById(ledger_id)).thenReturn(mockLedger);
        when(mockBankAccountService.getBankAccountByAccountNumber(mockBillInput.getAccountNumber())).thenReturn(mockBankAccount);
        when(mockObjectFactory.createBill()).thenReturn(mockBill);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        billService.addBill(ledger_id, mockBillInput);

        verify(mockBill).setAmount(mockBillInput.getAmount());
        verify(mockBill).setLedger(mockLedger);
        verify(mockBill).setCreatedTime(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        verify(mockBill).setBankAccount(mockBankAccount);
        verify(mockBill).setPayee(mockBillInput.getPayeeName());
        verify(mockBillRepository).save(mockBill);

    }

    @Test
    void test_DeleteBill(){
        long ledger_id = 1;
        long bill_id = 1;
        List<Bill> bills = List.of(mockBill);
        when(mockBillRepository.findBillsByLedgerId(ledger_id)).thenReturn(bills);
        when(mockBill.getId()).thenReturn(bill_id);

        billService.deleteBill(ledger_id, bill_id);

        verify(mockBillRepository).deleteById(bill_id);
    }

    @Test
    void test_DeleteBill_ThrowBillNotFoundException(){
        long ledger_id = 1;
        long bill_id = 1;
        List<Bill> bills = List.of(mockBill);
        when(mockBillRepository.findBillsByLedgerId(ledger_id)).thenReturn(bills);
        when(mockBill.getId()).thenReturn(2L);

        assertThrows(BillNotFoundException.class, ()->  billService.deleteBill(ledger_id, bill_id));
    }

    @Test
    void test_UpdateBill(){
        long ledger_id = 1;
        long bill_id = 1;
        List<Bill> bills = List.of(mockBill);
        when(mockBillRepository.findBillsByLedgerId(ledger_id)).thenReturn(bills);
        when(mockBill.getId()).thenReturn(bill_id);

        billService.updateBill(ledger_id, bill_id, mockBillInput);

        verify(mockBill).setAmount(mockBillInput.getAmount());
        verify(mockBill).setPayee(mockBillInput.getPayeeName());
        verify(mockBill).setBankAccount(mockBankAccountService.getBankAccountByAccountNumber(mockBillInput.getAccountNumber()));
        verify(mockBillRepository).save(mockBill);
    }

    @Test
    void test_UpdateBill_ThrowException(){
        long ledger_id = 1;
        long bill_id = 1;
        List<Bill> bills = List.of(mockBill);
        when(mockBillRepository.findBillsByLedgerId(ledger_id)).thenReturn(bills);
        when(mockBill.getId()).thenReturn(2L);

        assertThrows(BillNotFoundException.class, ()->  billService.updateBill(ledger_id, bill_id, mockBillInput));
    }

}
