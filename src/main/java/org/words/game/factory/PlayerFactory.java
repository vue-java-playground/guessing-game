package org.words.game.factory;

import org.words.game.repository.entity.PlayerInstance;

public interface PlayerFactory {

	public PlayerInstance createHumanPlayer(String word);

	public PlayerInstance createCPUPlayer(int length);
}
