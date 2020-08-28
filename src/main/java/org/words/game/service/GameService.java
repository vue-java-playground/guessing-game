package org.words.game.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.words.game.dto.DatamuseDTO;
import org.words.game.factory.PlayerFactory;
import org.words.game.repository.GamesRepo;
import org.words.game.repository.entity.GameInstace;
import org.words.game.repository.entity.PlayerInstance;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameService {

	@Autowired
	PlayerFactory playerFactory;

	@Autowired
	DatamuseService datamuseService;

	@Autowired
	GamesRepo gamesRepo;

	public GameInstace createGame(String word) {
		validateWord(word);
		return gamesRepo.saveAndFlush(GameInstace.builder()
				.cpu(playerFactory.createCPUPlayer(word.length()))
				.human(playerFactory.createHumanPlayer(word.toLowerCase())).build());
	}

	private void validateWord(String word) {
		DatamuseDTO datamuseDTO = datamuseService.getAllWords(word.toLowerCase()).stream()
				.filter(d -> d.getWord().equalsIgnoreCase(word.toLowerCase())).findFirst()
				.orElse(datamuseService.getFirstWord(word.toLowerCase()));
		if (datamuseDTO == null) {
			throw new IllegalStateException("Failed to create game for word: " + word);
		}
		else {
			if (!datamuseDTO.getWord().equalsIgnoreCase(word)) {
				throw new IllegalArgumentException(
						"Word: [" + word + "] is illegal, maybe you mistyped ["
								+ datamuseDTO.getWord() + "] ?");
			}
		}
	}

	public GameInstace sendMove(Long gameId, Character move) {
		Optional<GameInstace> gameOptional = gamesRepo.findById(gameId);
		if (!gameOptional.isPresent()) {
			throw new IllegalArgumentException("Can't find game: " + gameId);
		}

		GameInstace game = gameOptional.get();

		if (StringUtils.isNotBlank(game.getWinner())) {
			throw new IllegalStateException(
					"Game is ended already, the winner is: " + game.getWinner());
		}

		PlayerInstance human = game.getHuman();
		PlayerInstance cpu = game.getCpu();
		List<Character> opts = human.getOpts();
		if (!opts.stream().anyMatch(move::equals)) {
			throw new IllegalArgumentException("Illegal move: " + move);
		}
		opts.remove(Character.valueOf(move));
		human.setOpts(opts);
		game.setPlayerMove(move);
		String cpuWord = cpu.getWord();
		if (updateGuess(human, move, cpuWord)) {
			game.setHuman(human);
			game.setWinner("Human");
			return gamesRepo.saveAndFlush(game);
		}

		Character cpuMove = getCpuMove(cpu);
		game.setCpuMove(cpuMove);

		if (updateGuess(cpu, cpuMove, human.getWord())) {
			game.setCpu(cpu);
			game.setWinner("CPU");
			return gamesRepo.saveAndFlush(game);
		}

		game.setHuman(human);
		game.setCpu(cpu);
		return gamesRepo.saveAndFlush(game);
	}

	private Character getCpuMove(PlayerInstance cpu) {
		List<Character> opts = cpu.getOpts();
		List<DatamuseDTO> wordDtos = datamuseService.getAllWords(cpu.getGuess());
		Character cpuMove = null;
		int index = 0;
		while (cpuMove == null) {
			String firstWord = wordDtos.get(index++).getWord();
			Optional<Character> anyChar = opts.parallelStream()
					.filter(c -> firstWord.indexOf(c) > -1).findAny();
			if (anyChar.isPresent()) {
				System.err.println(firstWord);
				cpuMove = anyChar.get();
			}
		}
		opts.remove(cpuMove);
		cpu.setOpts(opts);
		return cpuMove;
	}

	private boolean updateGuess(PlayerInstance player, Character move, String word) {
		String guess = player.getGuess();
		StringBuilder tmp = new StringBuilder(guess);
		int index = word.indexOf(move, -1);
		while (index > -1) {
			tmp.setCharAt(index, move);
			index = word.indexOf(move, index + 1);
		}
		player.setGuess(tmp.toString());
		return !player.getGuess().contains("?");
	}

	public List<GameInstace> getAll() {
		return gamesRepo.findAll();
	}
}
