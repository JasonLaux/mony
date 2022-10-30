package com.fdmgroup.mony.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Formatted bank account object for accepting user input.
 * @author Jason Liu
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class BankAccountInput {

    @NotBlank
    String accountNumber;

    String bankName;

    @NotBlank
    BigDecimal startBalance;

}
