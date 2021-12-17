package com.suleyman.wallet.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suleyman.wallet.constant.WalletConstant;
import com.suleyman.wallet.exception.InSufficientBalanceException;
import com.suleyman.wallet.exception.TransactionConflictException;
import com.suleyman.wallet.model.Account;
import com.suleyman.wallet.model.Player;
import com.suleyman.wallet.model.Transaction;
import com.suleyman.wallet.repository.TransactionRepository;
import com.suleyman.wallet.service.AccountService;
import com.suleyman.wallet.service.PlayerService;
import com.suleyman.wallet.service.TransactionService;
import com.suleyman.wallet.vo.ResponseVo;
import com.suleyman.wallet.vo.TransactionVo;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PlayerService playerService;

	/**
	 * Make debit to account if the account balance is enough
	 * 
	 * @param transactionVo
	 */
	@Override
	@Transactional
	public ResponseVo debit(TransactionVo transactionVo) {

		checkTransaction(transactionVo.getExtTransId());

		Transaction transaction = generateTransaction(transactionVo);
		transaction.setTransType(WalletConstant.DEBIT);

		checkBalance(transaction);

		BigDecimal newBalance = transaction.getAccountBalance().subtract(transaction.getTransAmt());
		return updateBalance(transaction, newBalance);
	}

	/**
	 * Make credit to account
	 * 
	 * @param transactionVo
	 */
	@Override
	@Transactional
	public ResponseVo credit(TransactionVo transactionVo) {

		checkTransaction(transactionVo.getExtTransId());

		Transaction transaction = generateTransaction(transactionVo);
		transaction.setTransType(WalletConstant.CREDIT);

		BigDecimal newBalance = transaction.getAccountBalance().add(transaction.getTransAmt());

		return updateBalance(transaction, newBalance);
	}

	@Override
	public List<TransactionVo> getAllTransactionsByPlayerId(long playerId) {
		return transactionRepository.findTransactionsByPlayerId(playerId);
	}

	/**
	 * Check Balance is enough or not
	 * 
	 * @param transaction
	 * @return
	 */
	private boolean checkBalance(Transaction transaction) {
		if (transaction.getAccountBalance().subtract(transaction.getTransAmt()).intValue() < 0) {
			throw new InSufficientBalanceException("Balance is not sufficient");
		}
		return true;
	}

	/**
	 * Update account balance by given new balance and also insert transaction
	 * record to transaction table
	 * 
	 * @param transaction
	 * @param newBalance
	 * @return
	 */
	private ResponseVo updateBalance(Transaction transaction, BigDecimal newBalance) {
		ResponseVo responseVo = new ResponseVo();

		BigDecimal oldBalance = transaction.getAccountBalance();
		accountService.updateAccountBalanceById(newBalance, transaction.getAccountId());
		transactionRepository.save(transaction);
		responseVo.setOldBalance(oldBalance);
		responseVo.setNewBalance(newBalance);
		responseVo.setAccountId(transaction.getAccountId());

		return responseVo;
	}

	/**
	 * Validation for external transaction Id
	 * 
	 * @param transId
	 * @return
	 */
	private boolean checkTransaction(String transId) {

		Optional<Transaction> transaction = transactionRepository.getTransactionByExtTransId(transId);

		if (transaction.isPresent()) {
			throw new TransactionConflictException("Transaction Id Exist! You can not use same transaction Id");
		}

		return true;
	}

	private Transaction generateTransaction(TransactionVo transactionVo) {

		Player player = playerService.getPlayerById(transactionVo.getPlayerId());
		Account account = accountService.getAccountByPlayerId(player.getId());

		Transaction transaction = new Transaction();
		transaction.setExtTransId(transactionVo.getExtTransId());
		transaction.setTransAmt(transactionVo.getTransAmt());
		transaction.setAccountBalance(account.getBalance());
		transaction.setAccountId(account.getId());
		transaction.setPlayerId(player.getId());

		return transaction;
	}

}
