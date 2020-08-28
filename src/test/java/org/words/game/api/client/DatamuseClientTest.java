package org.words.game.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.words.game.dto.DatamuseDTO;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DatamuseClientTest {

	@Autowired
	DatamuseClient classUnderTest;
	
	@Test
	public void testRandomWord(){
		DatamuseDTO datamuseDTO = classUnderTest.getWord("?????").stream().findFirst().get();
		assertEquals(5, datamuseDTO.getWord().length());
		assertTrue(datamuseDTO.getFrequency() > 0);
	}
}
