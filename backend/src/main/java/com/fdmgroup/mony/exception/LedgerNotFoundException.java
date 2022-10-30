package com.fdmgroup.mony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Ledger does not exist.")
public class LedgerNotFoundException extends RuntimeException {
}
