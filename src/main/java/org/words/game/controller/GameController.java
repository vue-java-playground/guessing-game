package org.words.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.words.game.repository.entity.GameInstace;
import org.words.game.service.GameService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameController {

	@Autowired
	GameService gameService;

	@GetMapping("/{word}")
	public GameInstace createGame(@PathVariable String word) {
		return gameService.createGame(word);
	}

	@GetMapping("/{id}/{move}")
	public GameInstace sendMove(@PathVariable Long id, @PathVariable Character move) {
		return gameService.sendMove(id, move);
	}

	@GetMapping
	public List<GameInstace> getAllGames() {
		return gameService.getAll();
	}
}
