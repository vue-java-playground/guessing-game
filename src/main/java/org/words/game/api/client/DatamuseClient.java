package org.words.game.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.words.game.config.FeignConfig;
import org.words.game.dto.DatamuseDTO;

@FeignClient(name = "datamuse", url = "${datamuse.url}", configuration = FeignConfig.class)
public interface DatamuseClient {

	@GetMapping("?max=1000&md=fd")
	List<DatamuseDTO> getWord(@RequestParam("sp") String regex);
}
