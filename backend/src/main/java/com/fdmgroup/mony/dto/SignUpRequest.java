package com.fdmgroup.mony.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Sign up request.
 * @author Jason Liu
 * @version 1.0
 */
@Data
public class SignUpRequest {

    @Schema( example = "user1")
    @NotBlank
    private String username;

    @Schema(example = "user1")
    @NotBlank
    private String password;

    @Schema(example = "user1@gmail.com")
    @Email
    private String email;
}
