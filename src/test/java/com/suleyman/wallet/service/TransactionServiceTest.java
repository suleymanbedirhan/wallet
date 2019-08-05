package com.suleyman.wallet.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.suleyman.wallet.model.Account;
import com.suleyman.wallet.model.Player;
import com.suleyman.wallet.model.Transaction;
import com.suleyman.wallet.repository.TransactionRepository;
import com.suleyman.wallet.service.impl.AccountServiceImpl;
import com.suleyman.wallet.service.impl.PlayerServiceImpl;
import com.suleyman.wallet.service.impl.TransactionServiceImpl;
import com.suleyman.wallet.vo.TransactionVo;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

	@InjectMocks
	private TransactionServiceImpl transactionServiceMock;
	
	@Mock
	private TransactionRepository transactionRepositoryMock;
	
	@Mock
	private AccountServiceImpl accountService;
	
	@Mock
	private PlayerServiceImpl playerService;
	
	String extTransId;
	
	long playerId;
	
	long accountId;
	
	TransactionVo transactionVo;
	
	Account account;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		UUID uuid = UUID.randomUUID();
		extTransId = uuid.toString();
		accountId= 1l;
		playerId= 1l;
		transactionVo = new TransactionVo(extTransId, playerId, BigDecimal.TEN);
		account = new Account(accountId, "test", BigDecimal.TEN, new Player(playerId, "Player Test"));
	}

	@Test
	public void testCreditShouldSuccess() throws Exception {
		
		when(transactionRepositoryMock.save(any(Transaction.class))).thenReturn(new Transaction());
		when(playerService.getPlayerById((any(Long.class)))).thenReturn(new Player());
		when(accountService.getAccountByPlayerId((any(Long.class)))).thenReturn(account);
		
        assertThat(transactionServiceMock.credit(transactionVo), is(notNullValue()));
		verify(transactionRepositoryMock, times(1)).save(any(Transaction.class));
	}

	@Test
	public void testDebitShouldSuccess() throws Exception {
		
		when(transactionRepositoryMock.save(any(Transaction.class))).thenReturn(new Transaction());
		when(playerService.getPlayerById((any(Long.class)))).thenReturn(new Player());
		when(accountService.getAccountByPlayerId((any(Long.class)))).thenReturn(account);
		
        assertThat(transactionServiceMock.debit(transactionVo), is(notNullValue()));
		verify(transactionRepositoryMock, times(1)).save(any(Transaction.class));
	}

	@Test
	public void testFindAllTransactionsByPlayerId() throws Exception {
		
		when(transactionRepositoryMock.findTransactionsByPlayerId(playerId)).thenReturn(new ArrayList<TransactionVo>());
		when(playerService.getPlayerById((any(Long.class)))).thenReturn(new Player());
		when(accountService.getAccountByPlayerId((any(Long.class)))).thenReturn(account);
		
        assertThat(transactionServiceMock.getAllTransactionsByPlayerId(playerId), is(notNullValue()));
		verify(transactionRepositoryMock, times(1)).findTransactionsByPlayerId(playerId);
	}
	
}
