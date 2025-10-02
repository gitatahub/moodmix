package com.moodmix.moodmix.api.Controller;

import com.moodmix.moodmix.api.DTO.TrackResponse;
import com.moodmix.moodmix.data.entities.Track;
import com.moodmix.moodmix.logic.services.UploadTrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final UploadTrackService uploadTrackService;

    @PostMapping("/upload")
    public ResponseEntity<TrackResponse> upload(@RequestParam("file") MultipartFile file) {
        Track saved = uploadTrackService.upload(file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TrackResponse.fromEntity(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackResponse> getTrack(@PathVariable Long id) {
        return uploadTrackService.getById(id)
                .map(track -> ResponseEntity.ok(TrackResponse.fromEntity(track)))
                .orElse(ResponseEntity.notFound().build());
    }
}

