package com.fdmgroup.mony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Bank account does not exist.")
public class BankAccountNotFoundException extends RuntimeException{
}
