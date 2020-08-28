package org.words.game.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.words.game.dto.DatamuseDTO;
import org.words.game.factory.PlayerFactory;
import org.words.game.repository.entity.PlayerInstance;
import org.words.game.service.DatamuseService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerFactoryImpl implements PlayerFactory {

	List<Character> abc;

	@Autowired
	DatamuseService datamuseService;

	public PlayerFactoryImpl() {
		abc = new ArrayList<>();
		IntStream.range(97, 123).forEach(i -> abc.add((char) i));
	}

	@Override
	public PlayerInstance createHumanPlayer(String word) {
		return PlayerInstance.builder().word(word).opts(abc)
				.guess(StringUtils.repeat("?", word.length())).build();
	}

	@Override
	public PlayerInstance createCPUPlayer(int length) {
		DatamuseDTO randomWord = datamuseService.getRandomWord(length);
		return PlayerInstance.builder().word(randomWord.getWord())
				.hint(randomWord.getHint()).opts(abc)
				.guess(StringUtils.repeat("?", length)).build();
	}
}
