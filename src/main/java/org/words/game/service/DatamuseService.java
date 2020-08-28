package org.words.game.service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.words.game.api.client.DatamuseClient;
import org.words.game.dto.DatamuseDTO;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DatamuseService {

	@Autowired
	DatamuseClient datamuseClient;

	public DatamuseDTO getRandomWord(int length) {
		if (length < 1) {
			throw new IllegalArgumentException("Length cannot be less then 1");
		}

		String regex = StringUtils.repeat("?", length);
		List<DatamuseDTO> words = getAllWords(regex, 100);
		Random rand = new Random();
		DatamuseDTO word = DatamuseDTO.builder().word(" ").build();
		while (word.getWord().contains(" ") || StringUtils.isEmpty(word.getHint())) {
			int index = rand.nextInt(words.size()) - 1;
			word = words.get(index < 0 ? 0 : index);
		}

		return word;
	}

	public DatamuseDTO getFirstWord(String word) {
		return getAllWords(word).stream().findFirst().orElse(null);
	}

	public List<DatamuseDTO> getAllWords(String word) {
		return getAllWords(word, 1000);
	}
	
	public List<DatamuseDTO> getAllWords(String word, int limit) {
		return datamuseClient.getWord(word).stream()
				.filter(w -> w.getWord().indexOf(" ") == -1)
				.sorted(Comparator.comparing(DatamuseDTO::getFrequency).reversed())
				.limit(limit).collect(Collectors.toList());
	}
}
