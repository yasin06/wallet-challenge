package com.playtomic.tests.wallet.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playtomic.tests.wallet.config.exceptions.InsufficientBalanceException;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.WalletService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Wallet getWalletByUserId(Long userId) {
		return walletRepository.getWalletByUserId(userId);
	}

	@Override
	public Wallet findById(Long walletId) {
		Optional<Wallet> result = walletRepository.findById(walletId);
		return result.isPresent() ? result.get() : null;
	}

	@Override
	public boolean existsById(Long walletId) {
		return walletRepository.existsById(walletId);
	}

	@Override
	public Wallet saveOrUpdate(Wallet wallet) {
		return walletRepository.save(wallet);
	}

	@Override
	public void deleteById(Long walletId) {
		walletRepository.deleteById(walletId);
	}

	@Transactional
	@Override
	public void updateBalance(Long walletId, BigDecimal amount) {
		//updateLock(walletId);
		Wallet wallet = findById(walletId);

		if ((wallet.getBalance().add(amount)).compareTo(BigDecimal.ZERO) < 0) {
			throw new InsufficientBalanceException();
		}

		String type = amount.compareTo(BigDecimal.ZERO) >= 0 ? "D" : "W";

		log.info(type + " amount:{}, old Balance: {}, new balance: {}", amount, wallet.getBalance(),
				wallet.getBalance().add((amount)));
		wallet.setBalance(wallet.getBalance().add((amount)));
		saveOrUpdate(wallet);
	}
	
//	@Transactional
//	private Wallet findAndModify(Long walletId) {
//		Query query6 = new Query();
//		query6.addCriteria(Criteria.where("walletId").is(walletId));
//		
//		Update update = new Update();
//		update.set("walletId", walletId);
//		
//		//FindAndModifyOptions().returnNew(true) = newly updated document
//		//FindAndModifyOptions().returnNew(false) = old document (not update yet)
//		Wallet wallet = mongoTemplate.findAndModify(
//				query6, update, 
//				new FindAndModifyOptions().returnNew(true), Wallet.class);
//		
//		return wallet;
//	}
	
	@Transactional
	@Override
	public void updateLock(Long walletId) {
		mongoTemplate.updateFirst(new Query(Criteria.where("walletId").is(walletId)), 
                new Update().inc("lock", 1), Wallet.class);
	}
}
