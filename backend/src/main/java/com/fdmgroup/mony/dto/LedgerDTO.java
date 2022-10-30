package com.fdmgroup.mony.dto;
import lombok.*;
import javax.validation.constraints.NotBlank;

/**
 * Formatted Ledger object.
 * @author Jason Liu
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class LedgerDTO {

    @NotBlank
    Long id;

    @NotBlank
    String name;

}
