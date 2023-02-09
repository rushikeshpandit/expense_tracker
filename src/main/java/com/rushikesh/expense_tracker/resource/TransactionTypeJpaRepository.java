package com.rushikesh.expense_tracker.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rushikesh.expense_tracker.constants.ConstantUtil;
import com.rushikesh.expense_tracker.exception.TransactionTypeNotFoundException;
import com.rushikesh.expense_tracker.model.TransactionType;
import com.rushikesh.expense_tracker.payload.response.ServiceResponse;
import com.rushikesh.expense_tracker.repository.TransactionTypeRepository;

@RestController
@RequestMapping(path = "/api")
public class TransactionTypeJpaRepository {

	@Autowired
	private TransactionTypeRepository transactionTypeRepository;

	@GetMapping("/users/transactiontype")
	public ResponseEntity<?> retrieveTransactionTypes() {
		ServiceResponse response = new ServiceResponse();
		List<TransactionType> transactionTypes = transactionTypeRepository.findAll();

		if(transactionTypes.isEmpty())
			throw new TransactionTypeNotFoundException("Transaction Types not found");

		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		response.setReturnObject(transactionTypes);
		return ResponseEntity
				.ok()
				.body(response);
	}
}
