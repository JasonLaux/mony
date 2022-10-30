package com.fdmgroup.mony.dto;
import lombok.*;
import javax.validation.constraints.NotBlank;

/**
 * Formatted User for response.
 * @author Jason Liu
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    Long id;

    @NotBlank
    String username;

    @NotBlank
    String password;

    @NotBlank
    String email;

}
