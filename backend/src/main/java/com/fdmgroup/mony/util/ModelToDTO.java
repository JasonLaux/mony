package com.fdmgroup.mony.util;
import com.fdmgroup.mony.dto.*;
import com.fdmgroup.mony.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Model mapper.
 *
 * @author Jason Liu
 * @version 1.0
 */
@Component
public class ModelToDTO {

    private ModelMapper modelMapper;

    /**
     * Define the mapping mechanism for models and DTOs.
     * @param modelMapper ModelMapper.
     */
    @Autowired
    public ModelToDTO(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        modelMapper.typeMap(Bill.class, BillDTO.class)
                .addMapping(bill -> bill.getBankAccount().getAccountNumber(), BillDTO::setBankAccountNumber)
                .addMapping(bill -> bill.getLedger().getName(), BillDTO::setLedgerName);
    }

    /**
     * User to UserDTO
     * @param user User.
     * @return     DTO.
     */
    public UserDTO userToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Ledger to LedgerDTO
     * @param ledger Ledger
     * @return       LedgerDTO
     */
    public LedgerDTO ledgerToDTO(Ledger ledger) {
        return modelMapper.map(ledger, LedgerDTO.class);
    }

    /**
     * Bill to BillDTO
     * @param bill Bill
     * @return     BillDTO
     */
    public BillDTO billToDTO(Bill bill) {
        return modelMapper.map(bill, BillDTO.class);
    }

    /**
     * BankAccount to BankAccountDTO
     * @param bankAccount BankAccount
     * @return            BankAccountDTO
     */
    public BankAccountDTO bankAccountToDTO(BankAccount bankAccount){
        return modelMapper.map(bankAccount, BankAccountDTO.class);
    }
}
