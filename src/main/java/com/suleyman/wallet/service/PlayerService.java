package com.suleyman.wallet.service;

import com.suleyman.wallet.model.Player;

/**
 * Player Service
 * @author suleyman.bedirhanoglu
 */
public interface PlayerService {

	/**
	 * Get player by given id
	 * @param playerId
	 * @return
	 */
	public Player getPlayerById(long playerId);
}
