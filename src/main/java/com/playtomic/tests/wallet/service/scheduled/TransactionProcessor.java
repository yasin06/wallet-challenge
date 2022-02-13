package com.playtomic.tests.wallet.service.scheduled;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playtomic.tests.wallet.config.exceptions.InsufficientBalanceException;
import com.playtomic.tests.wallet.model.Transaction;
import com.playtomic.tests.wallet.model.TransactionStatus;
import com.playtomic.tests.wallet.service.TransactionService;
import com.playtomic.tests.wallet.service.WalletService;

import lombok.extern.slf4j.Slf4j;

/****************************************************************
 * This service contains scheduled tasks for transaction processing
 * 
 ****************************************************************/
@Service
@EnableScheduling
@Slf4j
public class TransactionProcessor {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private WalletService walletService;
	
	/***************************************************************
	 * For every 10 seconds (or after the previous execution, if its not yet completed
	 * in 10 seconds) finds and processes transactions with status NEW.
	 * 
	 * If balance can be updated, than transaction status is updated as SUCCESS, else FAILED. 
	 * Transaction result time is set and transaction is updated accordingly. 
	 *  
	 ****************************************************************/
	@Scheduled(fixedDelay = 10000)
	@Transactional
	private void processWaitingTransactions() {
		log.info("Transaction processor begin.");
		List<Transaction> waitList = transactionService.getTransactionListByTransactionStatus(TransactionStatus.NEW);
		TransactionStatus transactionStatus = null;
		
		for(Transaction transaction : waitList) {
			try {
				// try to update wallet balance
				walletService.updateBalance(transaction.getWalletId(), transaction.getAmount());
				transactionStatus = TransactionStatus.SUCCESS;
			} catch (InsufficientBalanceException ibe) {
				log.error("transaction failed due to insufficient balance");
				transactionStatus = TransactionStatus.FAILED;
			}

			// update transaction
			transaction.setResultTime(new Date());
			transaction.setTransactionStatus(transactionStatus);
			transactionService.saveOrUpdate(transaction);
		}
		
		log.info("Transaction processor end. Number of processed transactions: {}", waitList.size());
	}

}
