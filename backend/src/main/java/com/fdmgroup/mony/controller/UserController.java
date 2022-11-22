package com.fdmgroup.mony.controller;
import com.fdmgroup.mony.dto.BankAccountDTO;
import com.fdmgroup.mony.dto.BankAccountInput;
import com.fdmgroup.mony.dto.LedgerDTO;
import com.fdmgroup.mony.dto.UserDTO;
import com.fdmgroup.mony.exception.BankAccountAlreadyExistsException;
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
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handle requests related to users, ledgers and bank accounts.
 * @author Jason Liu
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final LedgerService ledgerService;

    private final ModelToDTO modelToDTO;

    private final BankAccountService bankAccountService;

    private final BankAccountRepository bankAccountRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Get User object based on its id.
     * @param id User id.
     * @return   User object.
     */
    @Operation(summary = "get user using id")
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable long id){
        return modelToDTO.userToDTO(userService.getUserById(id));
    }

    /**
     * Update the user with user input form.
     * @param id   User id.
     * @param user User update request
     * @return     User DTO.
     */
    @Operation(summary = "update user")
    @PutMapping("/{id}")
    public UserDTO update(@PathVariable long id, @RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return modelToDTO.userToDTO(userService.updateUser(id, user));
    }

    /**
     * Delete user.
     * @param id User id.
     * @return   Message.
     */
    @Operation(summary = "delete user using id")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id){
        userService.deleteUserById(id);
        return "Deleted User with ID " + id;
    }

    /**
     * Get all the ledgers based on user id.
     * @param id User id.
     * @return   List of ledgerDTO.
     */
    @Operation(summary = "get all ledgers related with the user")
    @GetMapping("/{id}/ledgers")
    public List<LedgerDTO> getAllLedgers(@PathVariable long id){
        return ledgerService.findLedgersByUsersId(id)
                .stream()
                .map(ledger -> modelToDTO.ledgerToDTO(ledger))
                .collect(Collectors.toList());
    }

    /**
     * Get ledger DTO based on user id and ledger id.
     * @param user_id   User id.
     * @param ledger_id Ledger id.
     * @return          LedgerDTO.
     */
    @Operation(summary = "get the ledger associated with user")
    @GetMapping("/{user_id}/ledgers/{ledger_id}")
    public LedgerDTO getLedger(@PathVariable long user_id, @PathVariable long ledger_id){
        List<Ledger> ledgers = ledgerService.findLedgersByUsersId(user_id);
        Optional<Ledger> ledger = ledgers.stream().filter(ledger1 -> ledger1.getId() == ledger_id).findFirst();
        if(ledger.isEmpty()){
            throw new LedgerNotFoundException();
        }
        return modelToDTO.ledgerToDTO(ledger.get());
    }

    /**
     * Add ledger based on user id and ledger name.
     * @param user_id     User id.
     * @param ledger_name Ledger name.
     * @return            Response.
     */
    @Operation(summary = "link the ledger with user")
    @PostMapping("/{user_id}/ledgers/{ledger_name}")
    public String addLedger(@PathVariable long user_id, @PathVariable String ledger_name){
        ledgerService.addUserToLedger(user_id, ledger_name);
        return "Successfully add ledger with name " + ledger_name;
    }

    /**
     * Remove ledger based on user id and ledger id.
     * @param user_id   User id.
     * @param ledger_id Ledger id.
     * @return          Response.
     */
    @Operation(summary = "unlink the ledger with user")
    @DeleteMapping("/{user_id}/ledgers/{ledger_id}")
    public String removeLedger(@PathVariable long user_id, @PathVariable long ledger_id){
        ledgerService.removeUserFromLedger(user_id, ledger_id);
        return "Successfully delete ledger with id " + ledger_id;
    }

    /**
     * Get all bank accounts from user.
     * @param user_id User id.
     * @return        List of BankAccountDTO.
     */
    @Operation(summary = "get all bank accounts associated with user")
    @GetMapping("/{user_id}/bankAccounts")
    public List<BankAccountDTO> getAllBankAccounts(@PathVariable long user_id){
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccountsByUserId(user_id);

        return bankAccounts.stream()
                .map(modelToDTO::bankAccountToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get BankAccountDTO using user id and bank account id.
     * @param user_id        User id.
     * @param bankAccount_id Bank account id.
     * @return               BankAccountDTO.
     */
    @Operation(summary = "get specific bank account from user")
    @GetMapping("/{user_id}/bankAccounts/{bankAccount_id}")
    public BankAccountDTO getBankAccount(@PathVariable long user_id, @PathVariable long bankAccount_id){
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccountsByUserId(user_id);
        BankAccount bankAccount = bankAccounts.stream().filter(account -> account.getId() == bankAccount_id)
                                    .findFirst().orElseThrow(BankAccountNotFoundException::new);
        return modelToDTO.bankAccountToDTO(bankAccount);
    }

    /**
     * Create a new bank account.
     * @param user_id User id.
     * @param input   User input.
     * @return        BankAccountDTO.
     */
    @Operation(summary = "create a new bank account and link it with user")
    @PostMapping("/{user_id}/bankAccounts")
    public BankAccountDTO createBankAccount(@PathVariable long user_id, @RequestBody BankAccountInput input){
        User user = userService.getUserById(user_id);
        Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountNumber(input.getAccountNumber());
        if(bankAccount.isPresent()){
            throw new BankAccountAlreadyExistsException();
        }

        BankAccount newBankAccount = bankAccountService.save(BankAccount.builder().accountNumber(input.getAccountNumber())
                .bankName(input.getBankName()).balance(input.getStartBalance()).startBalance(input.getStartBalance()).user(user).build());

        return modelToDTO.bankAccountToDTO(newBankAccount);
    }

    /**
     * Delete the bank account.
     * @param user_id    User id.
     * @param account_id Bank account id.
     * @return           Message.
     */
    @Operation(summary = "unlink and delete the bank account from user")
    @DeleteMapping("/{user_id}/bankAccounts/{account_id}")
    public String deleteBankAccount(@PathVariable long user_id, @PathVariable long account_id){

        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccountsByUserId(user_id);

        for(BankAccount bankAccount : bankAccounts){
            if(bankAccount.getId() == account_id) {
                bankAccountService.delete(bankAccount);
                return "Successfully delete the bank account with id " + account_id + " of user " + user_id;
            }
        }

        throw new BankAccountNotFoundException();
    }

    /**
     * Update the bank account.
     * @param user_id    User id.
     * @param account_id Bank account id.
     * @param input      Updated information.
     * @return           BankAccountDTO.
     */
    @Operation(summary = "update bank account info")
    @PutMapping("/{user_id}/bankAccounts/{account_id}")
    public BankAccountDTO updateBankAccount(@PathVariable long user_id, @PathVariable long account_id, @RequestBody BankAccountInput input){
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccountsByUserId(user_id);
        for(BankAccount bankAccount : bankAccounts){
            if(bankAccount.getId() == account_id) {
                if(input.getAccountNumber().length() != 0){
                    bankAccount.setAccountNumber(input.getAccountNumber());
                }
                if(input.getBankName().length() != 0) {
                    bankAccount.setBankName(input.getBankName());
                }
                if(input.getStartBalance() != null) {
                    bankAccount.setStartBalance(input.getStartBalance());
                }
                BigDecimal balance = bankAccountService.calculateBalance(bankAccount.getAccountNumber());
                bankAccount.setBalance(balance);
                log.info(input.toString());
                return modelToDTO.bankAccountToDTO(bankAccountService.save(bankAccount));
            }
        }
        throw new BankAccountNotFoundException();
    }
}
