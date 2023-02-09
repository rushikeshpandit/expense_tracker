package com.rushikesh.expense_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TransactionTypeNotFoundException extends RuntimeException {

	public TransactionTypeNotFoundException(String message) {
		super(message);
	}
}