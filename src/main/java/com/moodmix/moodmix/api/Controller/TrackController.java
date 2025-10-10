package com.moodmix.moodmix.api.Controller;

import com.moodmix.moodmix.api.DTO.Track.TrackResponse;
import com.moodmix.moodmix.api.DTO.Track.TrackUploadRequest;
import com.moodmix.moodmix.data.entities.Track;
import com.moodmix.moodmix.logic.services.TrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracks")
@RequiredArgsConstructor
public class TrackController {

    private final TrackService uploadTrackService;

    @PostMapping("/upload")
    public ResponseEntity<TrackResponse> upload(@ModelAttribute @Valid TrackUploadRequest request ) {
        Track saved = uploadTrackService.upload(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(TrackResponse.fromEntity(saved));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TrackResponse> getTrackById(@PathVariable Long id) {
        var track = uploadTrackService.getById(id);
        System.out.println(track);

        if (track.isEmpty()) {
            System.out.println("Track not found - returning 404");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TrackResponse.fromEntity(track.get()));
    }

}

