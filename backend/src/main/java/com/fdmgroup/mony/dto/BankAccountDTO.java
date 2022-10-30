package com.fdmgroup.mony.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Formatted BankAccount object for responses.
 * @author Jason Liu
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class BankAccountDTO {

    @NotBlank
    Long id;

    @NotBlank
    String accountNumber;

    String bankName;

    BigDecimal balance;

}
