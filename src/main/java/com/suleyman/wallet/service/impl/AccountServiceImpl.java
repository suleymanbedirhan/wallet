package com.suleyman.wallet.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suleyman.wallet.exception.AccountNotFoundException;
import com.suleyman.wallet.model.Account;
import com.suleyman.wallet.repository.AccountRepository;
import com.suleyman.wallet.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	@Transactional(readOnly = true)
	public Account getAccountByPlayerId(long playerId) {
		Optional<Account> account = accountRepository.findAccountByPlayerId(playerId);
		checkAccount(account);
		return account.get();
	}

	@Override
	public void updateAccountBalanceById(BigDecimal balance, Long acctId) {
		Optional<Account> account = accountRepository.findById(acctId);
		checkAccount(account);
		account.get().setBalance(balance);
		accountRepository.save(account.get());
	}

	private void checkAccount(Optional<Account> account) {
		if (!account.isPresent()) {
			throw new AccountNotFoundException("Account Not Found");
		}
	}
}
