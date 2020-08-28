package org.words.game.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DatamuseDTO {

	String word;
	String score;
	List<String> tags;
	List<String> defs;

	public double getFrequency() {
		if (tags != null) {
			return tags.stream().filter(t -> t.startsWith("f:"))
					.map(t -> Double.parseDouble(t.replace("f:", ""))).findFirst()
					.orElse((double) 0);
		}
		return 0;
	}

	public String getHint() {
		if (defs != null && !defs.isEmpty()) {
			return defs.get(0);
		}
		return null;
	}
}
