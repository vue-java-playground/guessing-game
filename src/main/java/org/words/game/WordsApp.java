package org.words.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WordsApp {

	public static void main(String[] args) {
		SpringApplication.run(WordsApp.class);
	}
}