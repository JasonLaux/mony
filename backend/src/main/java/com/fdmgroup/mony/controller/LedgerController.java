package com.fdmgroup.mony.controller;
import com.fdmgroup.mony.dto.BillDTO;
import com.fdmgroup.mony.dto.BillInput;
import com.fdmgroup.mony.dto.LedgerDTO;
import com.fdmgroup.mony.model.Bill;
import com.fdmgroup.mony.model.Ledger;
import com.fdmgroup.mony.service.BillService;
import com.fdmgroup.mony.service.LedgerService;
import com.fdmgroup.mony.util.ModelToDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handle CRUD requests for ledgers and bills.
 *
 * @author Jason Liu
 * @version 1.0
 */
@RestController
@RequestMapping("/api/ledgers")
@AllArgsConstructor
public class LedgerController {

    private LedgerService ledgerService;

    private BillService billService;

    private ModelToDTO modelToDTO;

    /**
     * Get ledger DTO by its id.
     * @param id Ledger id.
     * @return   Ledger DTO.
     */
    @Operation(summary = "use id to get ledger")
    @GetMapping("/{id}")
    public LedgerDTO getLedgerById(@PathVariable long id){
        return modelToDTO.ledgerToDTO(ledgerService.findLedgerById(id));
    }

    /**
     * Create a new ledger based on its name.
     * @param ledger User input object.
     * @return       LedgerDTO containing id and name.
     */
    @Operation(summary = "create a ledger book")
    @PostMapping
    public LedgerDTO create(@RequestBody Ledger ledger){
        return modelToDTO.ledgerToDTO(ledgerService.addLedger(ledger));
    }

    /**
     * Update the ledger with the new name.
     * @param id     Ledger id.
     * @param ledger Ledger DTO.
     * @return
     */
    @Operation(summary = "update information of the ledger based on id")
    @PutMapping("/{id}")
    public LedgerDTO update(@PathVariable long id, @RequestBody Ledger ledger){
        return modelToDTO.ledgerToDTO(ledgerService.updateLeger(id, ledger));
    }

    /**
     * Delete the ledger according to the id.
     * @param id Ledger id.
     * @return   Message about deleting.
     */
    @Operation(summary = "delete the ledger book")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        ledgerService.deleteLeger(id);
        return "Deleted ledger with ID " + id;
    }

    /**
     * Get all bills from the specific ledger.
     * @param id Ledger id.
     * @return   List of BillDTO.
     */
    @Operation(summary = "get all bills from a ledger book")
    @GetMapping("/{id}/bills")
    public List<BillDTO> getAllBills(@PathVariable long id){
        return billService.getBillsByLedgerId(id).stream()
                .map(bill -> modelToDTO.billToDTO(bill))
                .collect(Collectors.toList());
    }

    /**
     * Add a new bill to the ledger.
     * @param id    Ledger id.
     * @param input BillInput object.
     * @return      BillDTO.
     */
    @Operation(summary = "add bill to ledger")
    @PostMapping("/{id}/bills")
    public BillDTO addBill(@PathVariable long id, @RequestBody BillInput input){
        Bill bill = billService.addBill(id, input);
        return modelToDTO.billToDTO(bill);
    }

    /**
     * Delete the bill.
     * @param ledger_id Ledger id.
     * @param bill_id   Bill id.
     * @return          Message for deleting successfully.
     */
    @Operation(summary = "delete bill of ledger using id")
    @DeleteMapping("/{ledger_id}/bills/{bill_id}")
    public String deleteBill(@PathVariable long ledger_id, @PathVariable long bill_id){
        billService.deleteBill(ledger_id, bill_id);
        return "Successfully delete bill with id " + bill_id;
    }

    /**
     * Update the bill.
     * @param ledger_id Ledger id.
     * @param bill_id   Bill id.
     * @param input     User input for bill information.
     * @return          BIllDTO.
     */
    @Operation(summary = "update a bill")
    @PutMapping("/{ledger_id}/bills/{bill_id}")
    public BillDTO updateBill(@PathVariable long ledger_id, @PathVariable long bill_id, @RequestBody BillInput input){
        return modelToDTO.billToDTO(billService.updateBill(ledger_id, bill_id, input));
    }

}
