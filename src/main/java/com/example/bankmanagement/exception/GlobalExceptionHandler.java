package com.example.bankmanagement.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<Map<String, String>> handleInsufficientBalance(InsufficientBalanceException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
	}

	@ExceptionHandler(InvalidTransferException.class)
	public ResponseEntity<Map<String, String>> handleInvalidTransfer(InvalidTransferException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));

	}

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleAccountNotFound(AccountNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
	}

	@ExceptionHandler(DestinationAccountNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleDestinationAccountNotFound(
			DestinationAccountNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String , String>> handleValidationError(MethodArgumentNotValidException ex){
	 Map<String, String> errors = new HashMap<>(); 
	 	ex.getBindingResult().getFieldErrors().forEach(error -> 
		errors.put(error.getField(),error.getDefaultMessage()));
		 return ResponseEntity.badRequest().body(errors);	}
}
