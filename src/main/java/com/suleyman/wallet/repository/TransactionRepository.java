package com.suleyman.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.suleyman.wallet.model.Transaction;
import com.suleyman.wallet.vo.TransactionVo;

public interface TransactionRepository extends CrudRepository<Transaction, Long>{

	public Optional<Transaction> getTransactionByExtTransId(String transId);
	
	@Query("SELECT new com.suleyman.wallet.vo.TransactionVo(t.extTransId, t.playerId, t.transAmt) FROM Transaction t WHERE t.playerId = :playerId")
	public List<TransactionVo> findTransactionsByPlayerId(@Param("playerId") long playerId);
}
