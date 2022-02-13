package com.playtomic.tests.wallet.service;

import java.math.BigDecimal;

import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.impl.WalletServiceImpl;

/**
 * 
 * @see WalletServiceImpl
 * 
 */
public interface WalletService {

	Wallet getWalletByUserId(Long userId);

	Wallet findById(Long walletId);

	boolean existsById(Long walletId);

	Wallet saveOrUpdate(Wallet wallet);

	void deleteById(Long walletId);

	void updateBalance(Long walletId, BigDecimal amount);
	
	void updateLock(Long walletId);

}
