package com.moodmix.moodmix.api.DTO.Track;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record TrackUploadRequest(
        @NotNull(message = "File is required")
        MultipartFile file,

        @Size(max = 100, message = "Artist must be at most 100 characters")
        String artist,

        @Size(max = 100, message = "Title must be at most 100 characters")
        String title
) {}
