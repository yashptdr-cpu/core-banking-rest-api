package com.example.bankmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankmanagement.dto.TransferRequestDTO;
import com.example.bankmanagement.entity.Account;
import com.example.bankmanagement.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		Account accountsaved = accountService.createAccount(account);
		return ResponseEntity.ok(accountsaved);
	}

	@PostMapping("{accountNumber}/transfers")
	public ResponseEntity<String> transferMoney(@PathVariable String accountNumber,
			@Valid @RequestBody TransferRequestDTO request) {

		accountService.transferMoney(accountNumber, request);

		return ResponseEntity.ok("Transfer Successfull");

	}

}
