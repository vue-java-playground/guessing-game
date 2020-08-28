package org.words.game.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.words.game.api.client.DatamuseClient;
import org.words.game.dto.DatamuseDTO;

@RunWith(MockitoJUnitRunner.class)
public class DatamuseServiceTest {

	@InjectMocks
	DatamuseService classUnderTest;
	
	@Mock
	DatamuseClient datamuseClient;
	
	@Mock
	DatamuseDTO mockDto;
	
	@Test
	public void testGetWord(){
		when(mockDto.getWord()).thenReturn("abcde");
		when(mockDto.getHint()).thenReturn("abcde");
		when(datamuseClient.getWord("?????")).thenReturn(Arrays.asList(mockDto));
		String randomWord = classUnderTest.getRandomWord(5).getWord();
		assertEquals(5, randomWord.length());
	}
}
