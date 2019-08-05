package com.suleyman.wallet.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suleyman.wallet.exception.PlayerNotFoundException;
import com.suleyman.wallet.model.Player;
import com.suleyman.wallet.repository.PlayerRepository;
import com.suleyman.wallet.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	
	public Player getPlayerById(long playerId){
		Optional<Player> player =  playerRepository.findById(playerId);
		if(!player.isPresent()) {
			throw new PlayerNotFoundException("Player Not Found");
		}
		return player.get();
	}
}
