package com.moodmix.moodmix.api.DTO.Playlist;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record TrackUploadPlaylistRequest(
        @NotNull(message = "Audio file is required")
        MultipartFile file,
        String title,
        String artist
) {}

