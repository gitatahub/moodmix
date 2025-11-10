package com.moodmix.moodmix.api.Controller;

import com.moodmix.moodmix.api.DTO.Playlist.CreatePlaylistRequest;
import com.moodmix.moodmix.api.DTO.Playlist.PlaylistResponse;
import com.moodmix.moodmix.api.DTO.Track.TrackUploadRequest;
import com.moodmix.moodmix.data.entities.Playlist;
import com.moodmix.moodmix.logic.interfaces.IPlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/playlist")
@Validated
@RequiredArgsConstructor
public class PlaylistController {
    private final IPlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistResponse> createPlaylist(@RequestBody @Validated CreatePlaylistRequest request) {
        Playlist created = playlistService.createPlaylist(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlaylistResponse.fromEntity(created));
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistResponse> getPlaylist(@PathVariable Long playlistId) {
        Playlist playlist = playlistService.getByIdOrThrow(playlistId);
        return ResponseEntity.ok(PlaylistResponse.fromEntity(playlist));
    }

    @PostMapping("/{playlistId}/tracks/{trackId}")
    public ResponseEntity<PlaylistResponse> addTrack(
            @PathVariable Long playlistId,
            @PathVariable Long trackId) {

        Playlist playlist = playlistService.addTrack(playlistId, trackId);
        return ResponseEntity.ok(PlaylistResponse.fromEntity(playlist));
    }

    @PostMapping("/{playlistId}/upload-track")
    public ResponseEntity<PlaylistResponse> uploadTrackToPlaylist(
            @PathVariable Long playlistId,
            @ModelAttribute TrackUploadRequest request) { // @ModelAttribute parses multipart form data

        Playlist playlist = playlistService.uploadTrack(playlistId, request);
        return ResponseEntity.ok(PlaylistResponse.fromEntity(playlist));
    }

    @DeleteMapping("/{playlistId}/tracks/{trackId}")
    public ResponseEntity<PlaylistResponse> removeTrack(
            @PathVariable Long playlistId,
            @PathVariable Long trackId) {

        Playlist updated = playlistService.removeTrack(playlistId, trackId);
        return ResponseEntity.ok(PlaylistResponse.fromEntity(updated));
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.noContent().build(); // returns 204
    }




}
