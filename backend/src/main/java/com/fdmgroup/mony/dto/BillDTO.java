package com.fdmgroup.mony.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Formatted bill model for response.
 * @author Jason Liu
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class BillDTO {

    @NotBlank
    Long id;

    @NotBlank
    LocalDateTime createdTime;

    @NotBlank
    BigDecimal amount;

    String payee;

    @NotBlank
    String ledgerName;

    @NotBlank
    String bankAccountNumber;
}
