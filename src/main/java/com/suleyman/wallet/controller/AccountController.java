package com.suleyman.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suleyman.wallet.model.Account;
import com.suleyman.wallet.service.AccountService;
import com.suleyman.wallet.vo.AccountVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="/account", description="Account Management", produces ="application/json")
@RequestMapping(value = "/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@ApiOperation(value = "Get Current Balance of Player")
	@GetMapping(value = "/player/{playerId}/balance")
	public ResponseEntity<AccountVo> getCurrentBalance(@PathVariable("playerId") long playerId) {
		Account account = accountService.getAccountByPlayerId(playerId);
		AccountVo accountVo = new AccountVo(account.getPlayer().getId(), account.getBalance(), account.getName());
		return new ResponseEntity<>(accountVo, HttpStatus.OK);
	}

}
