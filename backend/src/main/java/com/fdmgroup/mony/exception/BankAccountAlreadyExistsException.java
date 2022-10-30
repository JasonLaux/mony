package com.fdmgroup.mony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Bank account already exists.")
public class BankAccountAlreadyExistsException extends RuntimeException {
}
