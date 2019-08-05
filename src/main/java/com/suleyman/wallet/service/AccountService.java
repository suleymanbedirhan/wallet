package com.suleyman.wallet.service;

import java.math.BigDecimal;

import com.suleyman.wallet.model.Account;

/**
 * Account Service
 * @author suleyman.bedirhanoglu
 */
public interface AccountService {

	public Account getAccountByPlayerId(long playerId);

	public void updateAccountBalanceById(BigDecimal newBalance, Long id);
}
