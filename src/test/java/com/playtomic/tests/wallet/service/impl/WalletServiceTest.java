package com.playtomic.tests.wallet.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.test.context.ActiveProfiles;

import com.mongodb.MongoCommandException;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.WalletService;

import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("develop")
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
public class WalletServiceTest {

	@Autowired
	private WalletService walletService;

	private Long walletId = 1L;

	@Test
	@Order(1)
	public void insertWallet() {
		walletService.deleteById(walletId);

		Wallet wallet = Wallet.builder().walletId(walletId).userId(10L).balance(new BigDecimal("100.00")).lock(0).build();
		walletService.saveOrUpdate(wallet);
		assertTrue(true);
	}

	@Test
	@Order(2)
	public void testWalletExist() {
		Wallet wallet = walletService.findById(walletId);
		assertNotNull(wallet);
	}

	@Test
	@Order(3)
	public void test100Withdraw100Deposit() throws Exception {
		List<Thread> withdrawThreads = new ArrayList<>();
		List<Thread> depositThreads = new ArrayList<>();
		Thread t1 = null, t2 = null;

		for (int i = 0; i < 20; i++) {
			t1 = new Thread(this::withdraw);
			t1.start();
			withdrawThreads.add(t1);

			t2 = new Thread(this::deposit);
			t2.start();
			depositThreads.add(t2);
		}

		for (Thread thread : withdrawThreads) {
			thread.join();
		}

		for (Thread thread : depositThreads) {
			thread.join();
		}

		Wallet wallet = walletService.findById(walletId);
		log.info("FINAL BALANCE: {}", wallet.getBalance());

		assertTrue(wallet.getBalance().compareTo(new BigDecimal("100.0")) == 0);
	}

	public void withdraw()  {
		int retryCount = 0;
		
		while(retryCount <10) {
			try {
				walletService.updateBalance(walletId, new BigDecimal("-10.00"));
				break;
			} catch (UncategorizedMongoDbException | MongoCommandException e) {
				++retryCount;
			}
		}
		
		if(retryCount > 10) {
			log.error("\n\nCan not withdraw!\n");
		}
	}

	public void deposit() {
		int retryCount = 0;
		
		while(retryCount <10) {
			try {
				walletService.updateBalance(walletId, new BigDecimal("10.00"));
				break;
			} catch (UncategorizedMongoDbException | MongoCommandException e) {
				++retryCount;
			}
		}
		
		if(retryCount > 10) {
			log.error("\n\nCan not deposit!\n");
		}
	}

}
