package com.playtomic.tests.wallet.model;

import java.math.BigDecimal;
import java.util.Date;

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
@Document(collection = "transaction")
public class Transaction {

	@Id
	private Long transactionId;

	private Long walletId;

	private BigDecimal amount;

	private String source; // transaction request source info, like: USER-MOBILE, REFUND, GIFT-CARD v.s.

	private Date entryTime; // transaction's first arrival time

	private Date resultTime; // transaction's process time

	private Date readTime; // the time transaction owner notified

	private TransactionStatus transactionStatus; // status

	private String comment; // a column to hold additional information

}
