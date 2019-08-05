package com.suleyman.wallet.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.suleyman.wallet.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

	public Optional<Account> findAccountByPlayerId(long playerId);
	
}
