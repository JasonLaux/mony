package com.fdmgroup.mony.service;
import com.fdmgroup.mony.dto.BillInput;
import com.fdmgroup.mony.exception.BillNotFoundException;
import com.fdmgroup.mony.model.BankAccount;
import com.fdmgroup.mony.model.Bill;
import com.fdmgroup.mony.model.Ledger;
import com.fdmgroup.mony.repository.BillRepository;
import com.fdmgroup.mony.util.ObjectFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Bill service.
 * @author Jason Liu
 * @version 1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    private final LedgerService ledgerService;

    private final BankAccountService bankAccountService;

    private final ObjectFactory objectFactory;

    /**
     * Add a bill.
     * @param ledger_id Ledger id.
     * @param input     Bill input.
     * @return          Persisted Bill object.
     */
    public Bill addBill(long ledger_id, BillInput input){

        BigDecimal amount = input.getAmount();
        String accountNumber = input.getAccountNumber();
        String payeeName = input.getPayeeName();

        Ledger ledger = ledgerService.findLedgerById(ledger_id);
        BankAccount bankAccount = bankAccountService.getBankAccountByAccountNumber(accountNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Bill bill = objectFactory.createBill();

        bill.setAmount(amount);
        bill.setLedger(ledger);
        bill.setCreatedTime(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        bill.setBankAccount(bankAccount);
        bill.setPayee(payeeName);

        return billRepository.save(bill);

    }

    /**
     * Delete bill.
     * @param ledger_id Ledger id.
     * @param bill_id   Bill id.
     */
    public void deleteBill(long ledger_id, long bill_id){
        List<Bill> bills = getBillsByLedgerId(ledger_id);
        for(Bill bill : bills){
            if(bill.getId() == bill_id){
                billRepository.deleteById(bill_id);
                return;
            }
        }
        throw new BillNotFoundException();
    }

    /**
     * Update bill.
     * @param ledger_id Ledger id.
     * @param bill_id   Bill id.
     * @param input     BillInput
     * @return          Bill.
     */
    public Bill updateBill(long ledger_id, long bill_id, BillInput input){
        List<Bill> bills = getBillsByLedgerId(ledger_id);
        for(Bill bill : bills){
            if(bill.getId() == bill_id){
                bill.setAmount(input.getAmount());
                bill.setPayee(input.getPayeeName());
                bill.setBankAccount(bankAccountService.getBankAccountByAccountNumber(input.getAccountNumber()));
                return billRepository.save(bill);
            }
        }
        throw new BillNotFoundException();
    }

    /**
     * Get a list of bills.
     * @param id Ledger id.
     * @return   List of bills.
     */
    public List<Bill> getBillsByLedgerId(long id){

        return billRepository.findBillsByLedgerId(id);
    }
}
