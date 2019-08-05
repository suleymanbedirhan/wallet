package com.suleyman.wallet.repository;

import org.springframework.data.repository.CrudRepository;

import com.suleyman.wallet.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Long>{

	public Player getPlayerById(long id);

}
