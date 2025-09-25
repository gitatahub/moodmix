package com.moodmix.moodmix.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private String email;
    private Instant createdAt;
}
