package com.fdmgroup.mony.controller;
import com.fdmgroup.mony.dto.*;
import com.fdmgroup.mony.exception.BankAccountNotFoundException;
import com.fdmgroup.mony.exception.LedgerNotFoundException;
import com.fdmgroup.mony.model.BankAccount;
import com.fdmgroup.mony.model.Bill;
import com.fdmgroup.mony.model.Ledger;
import com.fdmgroup.mony.model.User;
import com.fdmgroup.mony.repository.BankAccountRepository;
import com.fdmgroup.mony.service.BankAccountService;
import com.fdmgroup.mony.service.BillService;
import com.fdmgroup.mony.service.LedgerService;
import com.fdmgroup.mony.service.UserService;
import com.fdmgroup.mony.util.ModelToDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class LedgerControllerTest {

    private LedgerController ledgerController;

    @Mock
    private LedgerService mockLedgerService;

    @Mock
    private BillService mockBillService;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private ModelToDTO mockModelToDTO;

    @BeforeEach
    void setup(){
        ledgerController = new LedgerController(mockLedgerService, mockBillService, mockModelToDTO, bankAccountService);
    }

    @Test
    void test_GetLedger_ById(){
        long ledger_id = 1;
        Ledger ledger = mock(Ledger.class);
        LedgerDTO ledgerDTO = mock(LedgerDTO.class);
        when(mockLedgerService.findLedgerById(ledger_id)).thenReturn(ledger);
        when(mockModelToDTO.ledgerToDTO(ledger)).thenReturn(ledgerDTO);

        LedgerDTO result = ledgerController.getLedgerById(ledger_id);
        assertEquals(ledgerDTO, result);

    }

    @Test
    void test_CreateLedger(){
        Ledger ledger = mock(Ledger.class);
        LedgerDTO ledgerDTO = mock(LedgerDTO.class);
        when(mockLedgerService.addLedger(ledger)).thenReturn(ledger);
        when(mockModelToDTO.ledgerToDTO(ledger)).thenReturn(ledgerDTO);

        LedgerDTO result = ledgerController.create(ledger);
        assertEquals(ledgerDTO, result);
    }

    @Test
    void test_UpdateLedger(){
        long id = 1;
        Ledger ledger = mock(Ledger.class);
        Ledger updated_ledger = mock(Ledger.class);
        LedgerDTO ledgerDTO = mock(LedgerDTO.class);
        when(mockLedgerService.updateLeger(id, ledger)).thenReturn(updated_ledger);
        when(mockModelToDTO.ledgerToDTO(updated_ledger)).thenReturn(ledgerDTO);

        assertEquals(ledgerDTO, ledgerController.update(id, ledger));
    }

    @Test
    void test_DeleteLedger(){
        long ledger_id = 1;

        assertEquals("Deleted ledger with ID " + ledger_id, ledgerController.delete(ledger_id));
        verify(mockLedgerService).deleteLeger(ledger_id);
    }

    @Test
    void test_GetAllBills(){
        long ledger_id = 1;
        Bill bill = mock(Bill.class);
        List<Bill> bills = List.of(bill);
        BillDTO billDTO = mock(BillDTO.class);
        List<BillDTO> billDTOS = List.of(billDTO);
        when(mockBillService.getBillsByLedgerId(ledger_id)).thenReturn(bills);
        when(mockModelToDTO.billToDTO(bill)).thenReturn(billDTO);

        assertEquals(billDTOS, ledgerController.getAllBills(ledger_id));
    }

//    @Test
//    void test_AddBill(){
//        long ledger_id = 1;
//        BillInput billInput = mock(BillInput.class);
//        Bill bill = mock(Bill.class);
//        BillDTO billDTO = mock(BillDTO.class);
//        when(mockBillService.addBill(ledger_id, billInput)).thenReturn(bill);
//        when(mockModelToDTO.billToDTO(bill)).thenReturn(billDTO);
//
//        assertEquals(billDTO, ledgerController.addBill(ledger_id, billInput));
//    }

//    @Test
//    void test_DeleteBill(){
//        long ledger_id = 1;
//        long bill_id = 1;
//
//        assertEquals("Successfully delete bill with id " + bill_id, ledgerController.deleteBill(ledger_id, bill_id));
//        verify(mockBillService).deleteBill(ledger_id, bill_id);
//    }
//
//    @Test
//    void test_UpdateBill(){
//        long ledger_id = 1;
//        long bill_id = 1;
//        BillInput input = mock(BillInput.class);
//        Bill bill = mock(Bill.class);
//        BillDTO billDTO = mock(BillDTO.class);
//        when(mockBillService.updateBill(ledger_id, bill_id, input)).thenReturn(bill);
//        when(mockModelToDTO.billToDTO(bill)).thenReturn(billDTO);
//
//        assertEquals(billDTO, ledgerController.updateBill(ledger_id, bill_id, input));
//    }
}
