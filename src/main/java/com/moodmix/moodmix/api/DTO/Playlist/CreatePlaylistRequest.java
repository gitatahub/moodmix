package com.moodmix.moodmix.api.DTO.Playlist;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public record CreatePlaylistRequest(
        @NotNull(message = "Name is required")
        String name,

        @Size(max = 200, message = "Description can be 200 characters max.")
        String description
) {}
