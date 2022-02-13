package com.playtomic.tests.wallet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.playtomic.tests.wallet.model.Transaction;
import com.playtomic.tests.wallet.model.TransactionStatus;
import com.playtomic.tests.wallet.repository.TransactionRepository;
import com.playtomic.tests.wallet.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Transaction getTransactionByWalletId(Long walletId) {
		return transactionRepository.getTransactionByWalletId(walletId);
	}

	@Override
	public Transaction saveOrUpdate(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	public void deleteById(Long transactionId) {
		transactionRepository.deleteById(transactionId);
	}

	@Override
	public List<Transaction> getTransactionListByTransactionStatus(TransactionStatus transactionStatus) {
		return transactionRepository.getTransactionListByTransactionStatus(transactionStatus);
	}
	
}
