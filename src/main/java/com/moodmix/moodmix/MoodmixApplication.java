package com.moodmix.moodmix;

import com.moodmix.moodmix.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class MoodmixApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoodmixApplication.class, args);
	}

}
