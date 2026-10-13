package com.example.bankmanagement.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bankmanagement.dto.TransferRequestDTO;
import com.example.bankmanagement.entity.Account;
import com.example.bankmanagement.exception.AccountNotFoundException;
import com.example.bankmanagement.exception.DestinationAccountNotFoundException;
import com.example.bankmanagement.exception.InsufficientBalanceException;
import com.example.bankmanagement.exception.InvalidTransferException;
import com.example.bankmanagement.repository.AccountRepository;

@Service
public class AccountService {
	
	
	private static final Logger log = LoggerFactory.getLogger(AccountService.class); 

	private final AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Account createAccount(Account account) {
		return accountRepository.save(account);
	}

	public Account getAccountByNumber(String accountNumber) {

		return accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountNotFoundException("Source account not found: " +accountNumber));
	}

	public Account getDestinationAccount(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new DestinationAccountNotFoundException("Destination not found "+accountNumber));
	}

	private void validateAmount(BigDecimal amount) {
		if (amount==null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidTransferException("transfer amount must be greater than zero");
		}
	}

	private void validateBalance(Account sourceAccount, BigDecimal amount) {
		if (sourceAccount.getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException("Insufficient balance in source account");
		}
	}

	@Transactional
	public void transferMoney(String fromAccountnumber, TransferRequestDTO request) {

		 log.info("money transfer started");
		 log.debug("Validating transfer amount");

		// 1. Source account check
		Account sourceAccount = getAccountByNumber(fromAccountnumber);

		// 2. Destination account check
		Account destinationAccount = getDestinationAccount(request.getToAccount());

	    BigDecimal amount = request.getAmount();
	    
	    
//	    Self-transfer check
	    if(sourceAccount.equals(destinationAccount)) {
	    	throw new InvalidTransferException(" Source and destination accounts cannot be the same");
	    }
	    
	    
		// 3. Amount validation
		validateAmount(amount);

		// 4.Balance validation
		validateBalance(sourceAccount, request.getAmount());

		// 5.Paise deduct kro
		sourceAccount.setBalance(sourceAccount.getBalance().subtract( amount));
		;

		// 6. Receiver ke account me paise add karo
		destinationAccount.setBalance(destinationAccount.getBalance().add( amount));

		// 7. Dono accounts save karo
		accountRepository.save(sourceAccount);
		accountRepository.save(destinationAccount);
		log.info("Money transfer completed successfully");
	}
}
