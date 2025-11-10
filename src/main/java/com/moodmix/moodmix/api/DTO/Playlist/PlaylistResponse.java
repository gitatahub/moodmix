package com.moodmix.moodmix.api.DTO.Playlist;

import com.moodmix.moodmix.api.DTO.Track.TrackResponse;
import com.moodmix.moodmix.data.entities.Playlist;
import com.moodmix.moodmix.data.entities.Track;

import java.util.List;

public record PlaylistResponse(
        Long id,
        String name,
        String description,
        List<TrackResponse> tracks
) {
    public static PlaylistResponse fromEntity(Playlist playlist) {
        List<TrackResponse> trackDtos = playlist.getTracks().stream()
                .map(TrackResponse::fromEntity)
                .toList();

        return new PlaylistResponse(
                playlist.getId(),
                playlist.getName(),
                playlist.getDescription(),
                trackDtos
        );
    }
}

