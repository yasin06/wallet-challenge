package com.playtomic.tests.wallet.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
@Document(collection = "wallet")
public class Wallet {
	
	@Id
	private Long walletId;
	
	private Long userId;
	
	private BigDecimal balance;
	
	private int lock;
}
