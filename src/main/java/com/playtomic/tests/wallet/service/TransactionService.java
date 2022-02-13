package com.playtomic.tests.wallet.service;

import java.util.List;

import com.playtomic.tests.wallet.model.Transaction;
import com.playtomic.tests.wallet.model.TransactionStatus;
import com.playtomic.tests.wallet.service.impl.TransactionServiceImpl;

/**
 * 
 * @see TransactionServiceImpl
 * 
 */
public interface TransactionService {

	Transaction getTransactionByWalletId(Long walletId);
	
	Transaction saveOrUpdate(Transaction transaction);

	void deleteById(Long transactionId);

	List<Transaction> getTransactionListByTransactionStatus(TransactionStatus transactionStatus);

}
