package com.moodmix.moodmix.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class pingController {
    @GetMapping("/api/ping")
    public Map<String, String> ping() {
        return Map.of("status","ok","service","moodmix","time", Instant.now().toString());
    }
}
