package com.playtomic.tests.wallet.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.playtomic.tests.wallet.model.Transaction;
import com.playtomic.tests.wallet.model.TransactionStatus;

public interface TransactionRepository extends MongoRepository<Transaction, Long> {
	
	Transaction getTransactionByWalletId(Long walletId);
	
	List<Transaction> getTransactionListByTransactionStatus(TransactionStatus transactionStatus);
	
}
