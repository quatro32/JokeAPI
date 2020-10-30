package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JokeController {
	@GetMapping("/joke")
	public Joke joke(@RequestParam(value = "lang", defaultValue = "en") String lang) throws JsonProcessingException {
            var jokes = getJokes(lang);
            return(jokes.get(new Random().nextInt(jokes.size())));
	}
        
        public List<Joke> getJokes(String lang) throws JsonProcessingException{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            
            var jokes = objectMapper.readValue(Data.JsonData, new TypeReference<List<Joke>>(){});
            return jokes.stream().filter(i -> i.lang.equals(lang)).collect(Collectors.toList());
        }
}