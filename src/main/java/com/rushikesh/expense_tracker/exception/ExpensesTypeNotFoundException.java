package com.rushikesh.expense_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ExpensesTypeNotFoundException extends RuntimeException {
	
	public ExpensesTypeNotFoundException(String message) {
		super(message);
	}
}