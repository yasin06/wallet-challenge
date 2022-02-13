package com.playtomic.tests.wallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.playtomic.tests.wallet.model.Wallet;


public interface WalletRepository extends MongoRepository<Wallet, Long> {
	
	Wallet getWalletByUserId(Long userId);

}
