package com.fdmgroup.mony.dto;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Formatted bill object for accepting user input.
 * @author Jason Liu
 * @version 1.0
 */
@Data
public class BillInput {

    @NotBlank
    BigDecimal amount;

    @NotBlank
    String accountNumber;

    String payeeName;
}
