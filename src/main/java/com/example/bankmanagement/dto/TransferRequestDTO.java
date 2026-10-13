package com.example.bankmanagement.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TransferRequestDTO {

	@NotBlank(message="Acocunt cannot be blank")
	@Size(max = 30, message = "Account number is too long")
	private String toAccount;
	
	
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01",
	        message = "Amount must be greater than zero")
	@Digits(integer = 17, fraction = 2,
	        message = "Amount can have at most 2 decimal places")
	private BigDecimal  amount;
	
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public BigDecimal  getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
