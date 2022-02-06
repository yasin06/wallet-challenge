package com.playtomic.tests.wallet.service.impl;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.playtomic.tests.wallet.service.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.StripeServiceException;

@ActiveProfiles("develop")
@SpringBootTest
public class StripeServiceTest {

	@Autowired
	private StripeService s;

	@Test
	public void test_exception() {
		Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
			s.charge("4242 4242 4242 4242", new BigDecimal(5));
		});
	}

	@Test
	public void test_ok() throws StripeServiceException {
		s.charge("4242 4242 4242 4242", new BigDecimal(15));
	}

}
